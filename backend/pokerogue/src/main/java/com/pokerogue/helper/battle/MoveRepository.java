package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MoveRepository {

    private final Map<String, Move> moves = new HashMap<>();

    public void save(Move move) {
        moves.put(move.id(), move);
    }

    public List<Move> findAll() {
        return moves.values()
                .stream()
                .toList();
    }
}
