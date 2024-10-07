package com.pokerogue.helper.pokemon.service;

import java.util.Queue;

public class BufferedQueue<T> {
    private final Queue<T> value;
    private int bufferCapacity;
    private int bufferCount;

    public BufferedQueue(Queue<T> queue) {
        this.value = queue;
    }

    public boolean hasNext() {
        return !value.isEmpty();
    }

    public T poll() {
        T removedValue = value.remove();
        bufferCapacity = Math.max(bufferCapacity - 1, 0);
        return removedValue;
    }

    public void add(T s) {
        value.add(s);
    }

    public boolean hasBufferedNext() {
        return bufferCapacity > 0;
    }

    public void buffer() {
        bufferCapacity = value.size();
        bufferCount++;
    }

    public int getBufferCount() {
        return bufferCount;
    }

    public int getSize() {
        return value.size();
    }
}
