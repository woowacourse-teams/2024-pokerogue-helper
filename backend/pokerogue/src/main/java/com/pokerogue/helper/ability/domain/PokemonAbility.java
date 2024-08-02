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
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "pokemon_ability")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "ko_name", nullable = false)
    private String koName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "detail_description", nullable = false)
    private String detailDescription;

    @OneToMany(mappedBy = "pokemonAbility", fetch = FetchType.LAZY)
    private Set<PokemonAbilityMapping> pokemonAbilityMappings;

    public PokemonAbility(String name, String koName, String description, String detailDescription) {
        this.name = name;
        this.koName = koName;
        this.description = description;
        this.detailDescription = detailDescription;
    }
}
