package com.pokerogue.helper.pokemon;

import static io.restassured.RestAssured.given;

import com.pokerogue.environment.repository.RepositoryTest;
import com.pokerogue.helper.pokemon.data.Pokemon;
import com.pokerogue.helper.pokemon.repository.PokemonRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Disabled("디버깅용 API 테스트")
public class PokeomonControllerTest extends RepositoryTest {

    @Autowired
    PokemonRepository pokemonRepository;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v1";
    }

    @Test
    public void testApiError() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pokemons2")
                .then()
                .statusCode(200);
    }

    @Test
    public void testApiError2() {

        List<Pokemon> all = pokemonRepository.findAll();

        for (Pokemon pokemon : all) {
            System.out.println(pokemon);
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/pokemon2/" + pokemon.getId())
                    .then()
                    .statusCode(200);
        }

    }
}
