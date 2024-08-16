package com.pokerogue.helper.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<Move> findById(String id) {
        return Optional.ofNullable(moves.get(id));
    }
}
