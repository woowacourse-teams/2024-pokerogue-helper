package com.pokerogue.helper.pokemon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "pokemon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pokedex_number", nullable = false)
    private Long pokedexNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "hp", nullable = false)
    private Integer hp;

    @Column(name = "speed", nullable = false)
    private Integer speed;

    @Column(name = "attack", nullable = false)
    private Integer attack;

    @Column(name = "defense", nullable = false)
    private Integer defense;

    @Column(name = "special_attack", nullable = false)
    private Integer specialAttack;

    @Column(name = "special_defense", nullable = false)
    private Integer specialDefense;

    @Column(name = "total_stats", nullable = false)
    private Integer totalStats;

    @Column(name = "image", nullable = false)
    private String image;

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.EAGER)
    private List<PokemonTypeMapping> pokemonTypeMappings;

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY)
    private List<PokemonAbilityMapping> pokemonAbilityMappings;

    public Pokemon(Long pokedexNumber, String name, Integer weight, Integer height, Integer hp, Integer speed,
                   Integer attack, Integer defense, Integer specialAttack, Integer specialDefense,
                   Integer totalStats, String image) {
        this.pokedexNumber = pokedexNumber;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.hp = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.totalStats = totalStats;
        this.image = image;
    }
}
