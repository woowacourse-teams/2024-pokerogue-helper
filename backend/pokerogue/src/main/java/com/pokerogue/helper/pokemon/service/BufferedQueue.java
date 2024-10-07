package com.pokerogue.helper.pokemon.service;

import java.util.Queue;

public class BufferedQueue<T> {
    private final Queue<T> waitingQueue;
    private int bufferSize;
    private int captureCount;

    public BufferedQueue(Queue<T> waitingQueue) {
        this.waitingQueue = waitingQueue;
    }

    public boolean hasNext() {
        return !waitingQueue.isEmpty();
    }

    public T poll() {
        bufferSize = waitingQueue.size() - 1;
        return waitingQueue.poll();
    }

    public void add(T s) {
        waitingQueue.add(s);
    }

    public boolean hasBufferedNext() {
        return bufferSize > 0;
    }

    public void capture() {
        this.bufferSize = waitingQueue.size();
        captureCount++;
    }

    public int getCaptureCount() {
        return captureCount;
    }
}
