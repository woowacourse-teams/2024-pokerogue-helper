package com.pokerogue.helper.ability.repository;

import com.pokerogue.helper.ability.domain.PokemonAbility;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JDBCPokemonAbilityRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertPokemonAbility(List<PokemonAbility> pokemonAbilities) {
        String sql = "INSERT INTO pokemon_ability "
                + "(name, ko_name, description, detail_description) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PokemonAbility pokemonAbility = pokemonAbilities.get(i);
                ps.setString(1, pokemonAbility.getName());
                ps.setString(2, pokemonAbility.getKoName());
                ps.setString(3, pokemonAbility.getDescription());
                ps.setString(4, pokemonAbility.getDetailDescription());
            }

            @Override
            public int getBatchSize() {
                return pokemonAbilities.size();
            }
        });
    }
}
