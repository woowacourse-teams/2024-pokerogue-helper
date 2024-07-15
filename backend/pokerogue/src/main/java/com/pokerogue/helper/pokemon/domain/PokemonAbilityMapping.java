package com.pokerogue.helper.pokemon.domain;

import com.pokerogue.helper.ability.domain.PokemonAbility;
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
@Table(name = "pokemon_ability_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonAbilityMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    @ManyToOne
    @JoinColumn(name = "pokemon_ability_id", nullable = false)
    private PokemonAbility pokemonAbility;

    public String getPokemonAbilityName() {
        return pokemonAbility.getName();
    }
}
