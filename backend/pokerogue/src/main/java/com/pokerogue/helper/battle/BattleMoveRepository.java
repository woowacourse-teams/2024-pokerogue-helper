package com.pokerogue.helper.battle;

import com.pokerogue.helper.battle.data.BattleMove;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BattleMoveRepository {

    private final Map<String, BattleMove> moves = new HashMap<>();

    public void save(BattleMove battleMove) {
        moves.put(battleMove.id(), battleMove);
    }

    public List<BattleMove> findAll() {
        return moves.values()
                .stream()
                .toList();
    }

    public Optional<BattleMove> findById(String id) {
        return Optional.ofNullable(moves.get(id));
    }
}
