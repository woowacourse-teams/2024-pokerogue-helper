package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JDBCPokemonAbilityMappingRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertPokemonAbilityMapping(List<PokemonAbilityMapping> pokemonAbilityMappings) {
        String sql = "INSERT INTO pokemon_ability_mapping "
                + "(pokemon_id, pokemon_ability_id) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PokemonAbilityMapping pokemonAbilityMapping = pokemonAbilityMappings.get(i);
                ps.setLong(1, pokemonAbilityMapping.getPokemon().getId());
                ps.setLong(2, pokemonAbilityMapping.getPokemonAbility().getId());
            }

            @Override
            public int getBatchSize() {
                return pokemonAbilityMappings.size();
            }
        });
    }
}
