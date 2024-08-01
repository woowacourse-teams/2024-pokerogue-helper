package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.domain.PokemonTypeMatching;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JDBCPokemonTypeMatchingRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertPokemonTypeMatching(List<PokemonTypeMatching> pokemonTypeMatchings) {
        String sql = "INSERT INTO pokemon_type_matching "
                + "(from_type_id, to_type_id, result) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PokemonTypeMatching pokemonTypeMatching = pokemonTypeMatchings.get(i);
                ps.setLong(1, pokemonTypeMatching.getFromType().getId());
                ps.setLong(2, pokemonTypeMatching.getToType().getId());
                ps.setDouble(3, pokemonTypeMatching.getResult());
            }

            @Override
            public int getBatchSize() {
                return pokemonTypeMatchings.size();
            }
        });
    }
}
