package com.pokerogue.helper.ability.domain;

import com.pokerogue.helper.pokemon.domain.PokemonAbilityMapping;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "pokemon_ability")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "detail_description", nullable = false)
    private String detailDescription;

    @OneToMany(mappedBy = "pokemonAbility", fetch = FetchType.LAZY)
    private List<PokemonAbilityMapping> pokemonAbilityMappings;
}
