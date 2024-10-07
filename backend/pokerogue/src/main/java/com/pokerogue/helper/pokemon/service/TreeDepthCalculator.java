package com.pokerogue.helper.pokemon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeDepthCalculator {
    private final Map<String, List<String>> adjacentNodes;
    private final Map<String, Integer> indegree;

    public TreeDepthCalculator(Map<String, List<String>> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
        this.indegree = createIndegree();
    }

    private Map<String, Integer> createIndegree() {
        return adjacentNodes.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(node -> node, Collectors.summingInt(cnt -> 1)));
    }

    public Map<String, Integer> calculateDepths() {
        Map<String, Integer> depths = new HashMap<>();
        BufferedQueue<String> bufferedQueue = createBufferedQueue(depths);

        calculateDepthByTopologicalSort(bufferedQueue, depths);

        return depths;
    }

    private BufferedQueue<String> createBufferedQueue(Map<String, Integer> depths) {
        List<String> allNodes = getAllNodes();
        allNodes.forEach(node -> indegree.putIfAbsent(node, 0));
        allNodes.forEach(node -> adjacentNodes.putIfAbsent(node, new ArrayList<>()));
        allNodes.forEach(node -> depths.putIfAbsent(node, 0));

        return new BufferedQueue<>(allNodes.stream()
                .filter(this::isIndegreeZero)
                .collect(Collectors.toCollection(LinkedList::new)));
    }

    private List<String> getAllNodes() {
        return Stream.concat(adjacentNodes.keySet().stream(), adjacentNodes.values().stream().flatMap(List::stream))
                .distinct()
                .toList();
    }

    private void calculateDepthByTopologicalSort(BufferedQueue<String> bufferedQueue, Map<String, Integer> depths) {
        while (bufferedQueue.hasNext()) {
            bufferedQueue.buffer();
            flushNodes(bufferedQueue, depths);
        }
    }

    private void flushNodes(BufferedQueue<String> bufferedQueue, Map<String, Integer> depths) {
        while (bufferedQueue.hasBufferedNext()) {
            String currentNode = bufferedQueue.poll();
            List<String> nextNodes = adjacentNodes.get(currentNode);

            nextNodes.stream()
                    .peek(nextNode -> updateDepthCount(nextNode, depths, bufferedQueue))
                    .peek(this::decreaseIndegreeCount)
                    .filter(this::isIndegreeZero)
                    .filter(this::isAdjacentNodeExist)
                    .forEach(bufferedQueue::add);
        }
    }

    private void updateDepthCount(String nextNode, Map<String, Integer> depths, BufferedQueue<String> bufferedQueue) {
        depths.put(nextNode, bufferedQueue.getBufferCount());
    }

    private void decreaseIndegreeCount(String nextNode) {
        indegree.merge(nextNode, -1, Integer::sum);
    }


    private boolean isIndegreeZero(String node) {
        return indegree.get(node) == 0;
    }

    private boolean isAdjacentNodeExist(String node) {
        return !adjacentNodes.get(node).isEmpty();
    }
}
