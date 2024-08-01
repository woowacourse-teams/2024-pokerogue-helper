package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.domain.PokemonTypeMapping;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JDBCPokemonTypeMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertPokemonTypeMapping(List<PokemonTypeMapping> pokemonTypeMappings) {
        String sql = "INSERT INTO pokemon_type_mapping "
                + "(pokemon_id, pokemon_type_id) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PokemonTypeMapping pokemonTypeMapping = pokemonTypeMappings.get(i);
                ps.setLong(1, pokemonTypeMapping.getPokemon().getId());
                ps.setLong(2, pokemonTypeMapping.getPokemonType().getId());
            }

            @Override
            public int getBatchSize() {
                return pokemonTypeMappings.size();
            }
        });
    }
}
