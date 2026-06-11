package org.example.warehouseservice.asyncService;

import org.example.warehouseservice.domain.BMaterialStockTask;
import org.example.warehouseservice.domain.StockTaskBuffer;
import org.example.warehouseservice.mapper.BMaterialStockTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
public class StockTaskExecutor {

    /**
     * 推荐：
     * 500~2000
     */
    private static final int BATCH_SIZE = 800;

    /**
     * flush间隔
     */
    private static final long FLUSH_INTERVAL_MS = 200;
    private static final Logger log = LoggerFactory.getLogger(StockTaskExecutor.class);

    private final StockTaskBuffer buffer;

    private final BMaterialStockTaskMapper mapper;

    /**
     * 单线程消费者
     */
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    public StockTaskExecutor(StockTaskBuffer buffer, BMaterialStockTaskMapper mapper) {
        this.buffer = buffer;
        this.mapper = mapper;
    }

    @PostConstruct
    public void start() {

        executor.submit(this::consumeLoop);
    }

    private void consumeLoop() {

        List<BMaterialStockTask> batch =
                new ArrayList<>(BATCH_SIZE);

        long lastFlushTime = System.currentTimeMillis();

        while (true) {

            try {

                BMaterialStockTask first =
                        buffer.poll(100, TimeUnit.MILLISECONDS);

                if (first != null) {
                    batch.add(first);
                }

                buffer.drainTo(
                        batch,
                        BATCH_SIZE - batch.size());

                boolean shouldFlush =
                        batch.size() >= BATCH_SIZE
                                || (
                                !batch.isEmpty()
                                        && System.currentTimeMillis()
                                        - lastFlushTime
                                        >= FLUSH_INTERVAL_MS
                        );

                if (shouldFlush) {

                    flush(batch);

                    batch.clear();

                    lastFlushTime =
                            System.currentTimeMillis();
                }

            } catch (Exception e) {
                log.warn("batch insert error", e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void flush(List<BMaterialStockTask> batch) {

        if (batch.isEmpty()) {
            return;
        }

        long start = System.currentTimeMillis();

        mapper.batchBMaterialStockTaskInsert(batch);

        long cost = System.currentTimeMillis() - start;

        log.info(
                "batch insert success, size={}, cost={}ms",
                batch.size(),
                cost
        );
    }
}