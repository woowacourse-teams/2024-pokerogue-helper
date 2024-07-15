package com.pokerogue.helper.pokemon.domain;

import com.pokerogue.helper.type.domain.PokemonType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "pokemon_type_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonTypeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    @ManyToOne
    @JoinColumn(name = "pokemon_type_id", nullable = false)
    private PokemonType pokemonType;

    public String getCircleTypeImage() {
        return pokemonType.getCircleImage();
    }
}
