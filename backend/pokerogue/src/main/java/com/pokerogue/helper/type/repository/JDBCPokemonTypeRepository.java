package com.pokerogue.helper.type.repository;

import com.pokerogue.helper.type.domain.PokemonType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JDBCPokemonTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertPokemonType(List<PokemonType> pokemonTypes) {
        String sql = "INSERT INTO pokemon_type "
                + "(name, ko_name, image) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PokemonType pokemonType = pokemonTypes.get(i);
                ps.setString(1, pokemonType.getName());
                ps.setString(2, pokemonType.getKoName());
                ps.setString(3, pokemonType.getImage());
            }

            @Override
            public int getBatchSize() {
                return pokemonTypes.size();
            }
        });
    }
}
