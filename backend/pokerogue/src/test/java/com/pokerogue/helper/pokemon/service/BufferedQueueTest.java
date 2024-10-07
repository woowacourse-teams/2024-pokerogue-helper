package com.pokerogue.helper.pokemon.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BufferedQueueTest {

    @DisplayName("버퍼에 삽입 전에는 버퍼 카운팅이 작동하지 않는다.")
    @Test
    void bufferCounting() {
        BufferedQueue<Integer> bufferedQueue = new BufferedQueue<>(new LinkedList<>());
        bufferedQueue.add(1);
        bufferedQueue.add(2);
        bufferedQueue.add(3);

        Assertions.assertThat(bufferedQueue.hasBufferedNext()).isFalse();
        Assertions.assertThat(bufferedQueue.getBufferCount()).isZero();
    }

    @DisplayName("버퍼에 삽입 후 버퍼 카운팅이 증가한다.")
    @Test
    void bufferCounting2() {
        BufferedQueue<Integer> bufferedQueue = new BufferedQueue<>(new LinkedList<>());
        bufferedQueue.add(1);
        bufferedQueue.add(2);
        bufferedQueue.add(3);

        bufferedQueue.buffer();

        Assertions.assertThat(bufferedQueue.hasBufferedNext()).isTrue();
        Assertions.assertThat(bufferedQueue.getBufferCount()).isOne();
    }

    @DisplayName("버퍼 호출시 그 전에 저장된 값만 꺼낼 수 있다.")
    @Test
    void beforeBufferValues() {
        BufferedQueue<Integer> bufferedQueue = new BufferedQueue<>(new LinkedList<>());
        List<Integer> bufferedValues = new ArrayList<>();
        bufferedQueue.add(1);
        bufferedQueue.add(10000);
        bufferedQueue.buffer();
        bufferedQueue.add(3);
        bufferedQueue.add(4);
        bufferedQueue.add(5);

        while (bufferedQueue.hasBufferedNext()) {
            Integer poll = bufferedQueue.poll();
            bufferedValues.add(poll);
        }

        Assertions.assertThat(bufferedValues).hasSameElementsAs(List.of(1, 10000));
        Assertions.assertThat(bufferedQueue.size()).isEqualTo(3);
    }
}
