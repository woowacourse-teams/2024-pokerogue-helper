package com.pokerogue.helper.pokemon.service;

import java.util.Queue;

public class BufferedQueue<T> {
    private final Queue<T> value;
    private int bufferSize;
    private int bufferCount;

    public BufferedQueue(Queue<T> waitingQueue) {
        this.value = waitingQueue;
    }

    public boolean hasNext() {
        return !value.isEmpty();
    }

    public T poll() {
        T removedValue = value.remove();
        bufferSize = Math.max(bufferSize - 1, 0);
        if (bufferSize == 0) {
            bufferCount = 0;
        }
        return removedValue;
    }

    public void add(T s) {
        value.add(s);
    }

    public boolean hasBufferedNext() {
        return bufferSize > 0;
    }

    public void buffer() {
        bufferSize = value.size();
        bufferCount++;
    }

    public int getBufferCount() {
        return bufferCount;
    }

    public int size() {
        return value.size();
    }
}
