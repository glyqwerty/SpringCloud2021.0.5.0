package org.example.testmodule;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class AsyncMillionStressTest {

    /**
     * 总请求数
     */
    private static final int TOTAL = 1_000_000;

    /**
     * 并发窗口（非常关键）
     * 控制同时飞行中的请求数
     */
    private static final int MAX_IN_FLIGHT = 5000;

    /**
     * URL
     */
    private static final String URL =
            "http://172.20.10.5:9300/bill";

    /**
     * JSON
     */
    private static final ObjectMapper MAPPER =
            new ObjectMapper();

    /**
     * 成功数
     */
    private static final LongAdder SUCCESS =
            new LongAdder();

    /**
     * 失败数
     */
    private static final LongAdder FAIL =
            new LongAdder();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    private static volatile String CURRENT_MINUTE =
            LocalDateTime.now().format(FORMATTER);

    private static final AtomicInteger SEQ =
            new AtomicInteger();
    /**
     * 限流
     */
    private static final Semaphore SEMAPHORE =
            new Semaphore(MAX_IN_FLIGHT);

    /**
     * HTTP CLIENT
     */
    private static final HttpClient CLIENT =
            HttpClient.newBuilder()

                    // HTTP1.1 KeepAlive
                    .version(HttpClient.Version.HTTP_1_1)

                    // 超时
                    .connectTimeout(Duration.ofSeconds(10))

                    // 自定义线程池
                    .executor(
                            Executors.newFixedThreadPool(
                                    200
                            )
                    )

                    .build();
    private static String createJson() {

        String billCode = generateBillCode();

        return "{"
                + "\"billCode\":\"" + billCode + "\","
                + "\"billType\":\"人工入库\","
                + "\"status\":\"NEW\","
                + "\"totalQty\":100,"
                + "\"detailInfoList\":["
                + "{"
                + "\"billCode\":\"" + billCode + "\","
                + "\"materialCode\":\"M001\","
                + "\"materialName\":\"螺丝\","
                + "\"warehouseCode\":\"W01\","
                + "\"qty\":10"
                + "}"
                + "]"
                + "}";
    }
    public static void main(String[] args)
            throws Exception {

        long start = System.currentTimeMillis();

        CountDownLatch latch =
                new CountDownLatch(TOTAL);

        /**
         * TPS监控
         */
        ScheduledExecutorService monitor =
                Executors.newSingleThreadScheduledExecutor();

        monitor.scheduleAtFixedRate(() -> {

            long success = SUCCESS.sum();
            long fail = FAIL.sum();

            long seconds =
                    Math.max(
                            1,
                            (System.currentTimeMillis() - start) / 1000
                    );

            long tps = success / seconds;

            System.out.println(
                    "成功=" + success +
                            " 失败=" + fail +
                            " TPS=" + tps +
                            " 飞行中=" +
                            (MAX_IN_FLIGHT
                                    - SEMAPHORE.availablePermits())
            );

        }, 1, 1, TimeUnit.SECONDS);

        /**
         * 百万异步请求
         */
        for (int i = 0; i < TOTAL; i++) {

            // 控制飞行请求数量
            SEMAPHORE.acquire();

//            Map<String, Object> body =
//                    createRandomOrder();

//            String json =
//                    MAPPER.writeValueAsString(body);
            String json = createJson();
            HttpRequest request =
                    HttpRequest.newBuilder()

                            .uri(URI.create(URL))

                            .header(
                                    "Content-Type",
                                    "application/json"
                            )

                            .timeout(Duration.ofSeconds(30))

                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(json)
                            )

                            .build();

            CLIENT.sendAsync(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    )

                    .thenAccept(response -> {

                        if (response.statusCode() == 200) {
                            SUCCESS.increment();
                        } else {
                            FAIL.increment();
                        }

                    })

                    .exceptionally(ex -> {

                        FAIL.increment();

                        return null;
                    })

                    .whenComplete((r, ex) -> {

                        SEMAPHORE.release();

                        latch.countDown();
                    });
        }

        latch.await();

        long end = System.currentTimeMillis();

        long seconds =
                (end - start) / 1000;

        System.out.println("\n=======================");
        System.out.println("压测结束");
        System.out.println("总请求: " + TOTAL);
        System.out.println("成功: " + SUCCESS.sum());
        System.out.println("失败: " + FAIL.sum());
        System.out.println("耗时: " + seconds + " 秒");
        System.out.println(
                "平均TPS: " +
                        (TOTAL / Math.max(seconds, 1))
        );
        System.out.println("=======================");

        monitor.shutdown();
    }

    /**
     * 创建随机订单
     */
    private static Map<String, Object> createRandomOrder() {

        Map<String, Object> order =
                new HashMap<>();

        String billCode =
                generateBillCode();

        List<Map<String, Object>> details =
                new ArrayList<>();

        int totalQty = 0;

        for (int i = 0; i < 5; i++) {

            int qty =
                    ThreadLocalRandom.current()
                            .nextInt(1, 20);

            totalQty += qty;

            Map<String, Object> detail =
                    new HashMap<>();

            detail.put("billCode", billCode);

            detail.put(
                    "materialCode",
                    "M00" + random(1, 5)
            );

            detail.put(
                    "materialName",
                    randomMaterial()
            );

            detail.put(
                    "warehouseCode",
                    "W0" + random(1, 3)
            );

            detail.put(
                    "warehouseName",
                    "仓库" + random(1, 3)
            );

            detail.put(
                    "positionCode",
                    "P0" + random(1, 5)
            );

            detail.put(
                    "positionName",
                    "A区-" + random(1, 5)
            );

            detail.put("qty", qty);

            details.add(detail);
        }

        order.put("billCode", billCode);
        order.put("billType", "人工入库");
        order.put("status", "NEW");
        order.put("totalQty", totalQty);
        order.put("detailInfoList", details);

        return order;
    }

    /**
     * 单号
     */
    private static String generateBillCode() {

        String minute =
                LocalDateTime.now().format(FORMATTER);

        if (!minute.equals(CURRENT_MINUTE)) {

            synchronized (AsyncMillionStressTest.class) {

                if (!minute.equals(CURRENT_MINUTE)) {

                    CURRENT_MINUTE = minute;

                    SEQ.set(0);
                }
            }
        }

        int seq = SEQ.incrementAndGet();

        return "I"
                + minute
                + String.format("%06d", seq);
    }

    private static int random(int min, int max) {

        return ThreadLocalRandom.current()
                .nextInt(min, max + 1);
    }

    private static String randomMaterial() {

        String[] arr = {
                "螺丝",
                "螺母",
                "轴承",
                "电机",
                "齿轮"
        };

        return arr[
                ThreadLocalRandom.current()
                        .nextInt(arr.length)
                ];
    }
}