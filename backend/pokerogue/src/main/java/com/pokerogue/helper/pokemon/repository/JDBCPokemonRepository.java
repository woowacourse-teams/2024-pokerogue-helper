package com.pokerogue.helper.pokemon.repository;

import com.pokerogue.helper.pokemon.domain.Pokemon;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JDBCPokemonRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertPokemon(List<Pokemon> pokemons) {
        String sql = "INSERT INTO pokemon "
                + "(pokedex_number, name, ko_name, weight, height, hp, speed, attack, defense, special_attack, special_defense, total_stats, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Pokemon pokemon = pokemons.get(i);
                ps.setLong(1, pokemon.getPokedexNumber());
                ps.setString(2, pokemon.getName());
                ps.setString(3, pokemon.getKoName());
                ps.setDouble(4, pokemon.getWeight());
                ps.setDouble(5, pokemon.getHeight());
                ps.setInt(6, pokemon.getHp());
                ps.setInt(7, pokemon.getSpeed());
                ps.setInt(8, pokemon.getAttack());
                ps.setInt(9, pokemon.getDefense());
                ps.setInt(10, pokemon.getSpecialAttack());
                ps.setInt(11, pokemon.getSpecialDefense());
                ps.setInt(12, pokemon.getTotalStats());
                ps.setString(13, pokemon.getImage());
            }

            @Override
            public int getBatchSize() {
                return pokemons.size();
            }
        });
    }
}
