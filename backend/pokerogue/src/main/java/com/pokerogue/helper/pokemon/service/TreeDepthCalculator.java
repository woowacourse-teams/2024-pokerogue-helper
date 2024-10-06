package com.pokerogue.helper.pokemon.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

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
                .collect(Collectors.groupingBy(k -> k, Collectors.summingInt(v -> 1)));
    }

    public Map<String, Integer> calculateDepths() {
        Set<String> allNodes = indegree.keySet();
        Queue<String> waitingQueue = allNodes
                .stream()
                .filter(this::isWaitingNode)
                .collect(Collectors.toCollection(LinkedList::new));
        Map<String, Integer> depths = new HashMap<>();
        allNodes.forEach(node -> depths.put(node, 0));

        processAllNodes(waitingQueue, depths);

        return depths;
    }

    private void processAllNodes(Queue<String> waitingQueue, Map<String, Integer> depths) {
        while (!waitingQueue.isEmpty()) {
            processCurrentNode(waitingQueue, depths);
        }
    }

    private void processCurrentNode(Queue<String> waitingQueue, Map<String, Integer> depths) {
        String currentNode = waitingQueue.poll();
        List<String> nextNodes = adjacentNodes.get(currentNode);

        nextNodes.stream()
                .peek(nextNode -> increaseDepthCount(depths, currentNode, nextNode))
                .peek(this::decreaseIndegreeCount)
                .filter(this::isWaitingNode)
                .filter(this::isAdjacentNodeExist)
                .forEach(waitingQueue::add);
    }

    private void decreaseIndegreeCount(String nextNode) {
        indegree.put(nextNode, indegree.get(nextNode) - 1);
    }

    private void increaseDepthCount(Map<String, Integer> depths, String currentNode, String nextNode) {
        depths.put(nextNode, depths.get(currentNode) + 1);
    }

    private boolean isWaitingNode(String node) {
        return indegree.getOrDefault(node, 0) == 0;
    }

    private boolean isAdjacentNodeExist(String node) {
        List<String> nextNodes = adjacentNodes.get(node);
        return Objects.nonNull(nextNodes) && !nextNodes.isEmpty();
    }
}
