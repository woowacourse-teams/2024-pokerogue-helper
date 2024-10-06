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
    private final Map<String, Integer> inDegrees;
    private final Map<String, List<String>> adjacentNodes;

    public TreeDepthCalculator(Map<String, Integer> inDegrees, Map<String, List<String>> adjacentNodes) {
        this.inDegrees = inDegrees;
        this.adjacentNodes = adjacentNodes;
    }

    public Map<String, Integer> calculateDepths() {
        Map<String, Integer> depths = new HashMap<>();
        Set<String> allNodes = inDegrees.keySet();
        allNodes.forEach(node -> depths.put(node, 0));

        Queue<String> waitingQueue = allNodes
                .stream()
                .filter(this::isWaitingNode)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!waitingQueue.isEmpty()) {
            processCurrentDepth(waitingQueue, depths);
        }

        return depths;
    }

    private void processCurrentDepth(Queue<String> waitingQueue, Map<String, Integer> depths) {
        int queueSize = waitingQueue.size();

        while (queueSize-- > 0) {
            String currentNode = waitingQueue.poll();
            List<String> nextNodes = adjacentNodes.get(currentNode);
            if (isNextNodeNull(nextNodes)) {
                continue;
            }

            nextNodes.stream()
                    .peek(nextNode -> updateDepthCount(depths, currentNode, nextNode))
                    .peek(this::updateInDegreeCount)
                    .filter(this::isWaitingNode)
                    .forEach(waitingQueue::add);
        }
    }

    private void updateInDegreeCount(String nextNode) {
        inDegrees.put(nextNode, inDegrees.get(nextNode) - 1);
    }

    private void updateDepthCount(Map<String, Integer> depths, String currentNode, String nextNode) {
        depths.put(nextNode, depths.get(currentNode) + 1);
    }

    private boolean isWaitingNode(String node) {
        return inDegrees.get(node) == 0;
    }

    private boolean isNextNodeNull(List<String> edges) {
        return Objects.isNull(edges) || edges.isEmpty();
    }
}
