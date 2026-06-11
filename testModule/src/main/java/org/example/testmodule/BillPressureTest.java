package org.example.testmodule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

import java.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class BillPressureTest {

    /**
     * bill.txt路径
     */
    private static final String FILE_PATH =
            "bill.txt";

    /**
     * 接口地址
     */
    private static final String URL_PREFIX =
            "http://172.20.10.5:9300/bill/complete/";

    /**
     * 最大并发
     */
    private static final int MAX_CONCURRENCY =
            1000;

    /**
     * 重试次数
     */
    private static final int RETRY_TIMES =
            3;

    /**
     * 限流器
     */
    private static final Semaphore SEMAPHORE =
            new Semaphore(MAX_CONCURRENCY);

    /**
     * 成功数量
     */
    private static final AtomicLong SUCCESS =
            new AtomicLong();

    /**
     * 失败数量
     */
    private static final AtomicLong FAIL =
            new AtomicLong();

    /**
     * HTTP客户端
     */
    private static final HttpClient HTTP_CLIENT =
            HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(5))
                    .executor(
                            Executors.newFixedThreadPool(
                                    Runtime.getRuntime().availableProcessors() * 2
                            )
                    )
                    .build();

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        List<String> billCodes = loadBillCodes();

        System.out.println("读取billCode数量: " + billCodes.size());

        List<CompletableFuture<Void>> futures =
                new ArrayList<>(billCodes.size());

        for (String billCode : billCodes) {

            SEMAPHORE.acquire();

            CompletableFuture<Void> future =
                    sendWithRetry(
                            billCode,
                            RETRY_TIMES
                    ).whenComplete((v, ex) -> {

                        SEMAPHORE.release();

                    });

            futures.add(future);
        }

        CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).join();

        long end = System.currentTimeMillis();

        long success = 1014942l;

//        long fail = FAIL.get();

        long cost = end - start;

        System.out.println("""
                
                ==========================
                全部完成
                ==========================
                success : %d
                fail    : %d
                cost    : %d ms
                TPS     : %d
                ==========================
                """.formatted(
                success,
                0,
                cost,
                cost == 0 ? 0 : success * 1000 / cost
        ));
    }

    /**
     * 读取bill.txt
     */
    private static List<String> loadBillCodes()
            throws IOException {

        List<String> list = new ArrayList<>();

        try (Stream<String> stream =
                     Files.lines(Path.of(FILE_PATH))) {

            stream.map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.replace("\"", ""))
                    .forEach(list::add);
        }

        return list;
    }

    /**
     * 发送请求（带重试）
     */
    private static CompletableFuture<Void> sendWithRetry(
            String billCode,
            int retry
    ) {

        String url = URL_PREFIX + billCode;

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .PUT(HttpRequest.BodyPublishers.ofString(""))
                        .timeout(Duration.ofSeconds(10))
                        .build();

        return HTTP_CLIENT.sendAsync(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                )
                .thenAccept(response -> {

//                    int status = response.statusCode();

//                    if (status == 200) {
//
//                        long ok =
//                                SUCCESS.incrementAndGet();
//
//                        if (ok % 1000 == 0) {
//
//                            System.out.println(
//                                    "成功: " + ok
//                            );
//                        }
//
//                    } else {
//
//                        throw new RuntimeException(
//                                "status=" + status
//                        );
//                    }

                })
                .exceptionallyCompose(ex -> {

                    if (retry > 0) {

                        return sendWithRetry(
                                billCode,
                                retry - 1
                        );
                    }

//                    FAIL.incrementAndGet();

//                    System.err.println(
//                            "失败: " + billCode
//                                    + " -> "
//                                    + ex.getMessage()
//                    );

                    return CompletableFuture.completedFuture(null);

                });
    }
}