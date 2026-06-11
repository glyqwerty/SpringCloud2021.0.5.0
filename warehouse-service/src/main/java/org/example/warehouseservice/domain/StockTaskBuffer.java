package org.example.warehouseservice.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class StockTaskBuffer {

    /**
     * 有界队列
     */
    private final BlockingQueue<BMaterialStockTask> queue =
            new LinkedBlockingQueue<>(200_000);

    public boolean offer(BMaterialStockTask task) {
        return queue.offer(task);
    }

    public int drainTo(List<BMaterialStockTask> list, int maxElements) {
        return queue.drainTo(list, maxElements);
    }

    public BMaterialStockTask poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        return queue.poll(timeout, unit);
    }

    public int size() {
        return queue.size();
    }
}