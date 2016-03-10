package com.king.hawkeye.demo;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by King on 16/3/10.
 */
public class BlockingQueueTest {

    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    @Test
    public void testAdd() {

        queue.add(1);

        queue.add(2);

        System.out.println("finish.");

    }

    @Test
    public void testTake() throws InterruptedException {

        int i = queue.take();
        System.out.println("finish." + i);
    }

}
