package com.pokerogue.helper.type.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "pokemon_type_matching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonTypeMatching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_from", nullable = false)
    private String typeFrom;

    @Column(name = "type_to", nullable = false)
    private String typeTo;

    @Column(name = "result", nullable = false)
    private Double result;

    public PokemonTypeMatching(String typeFrom, String typeTo, Double result) {
        this.typeFrom = typeFrom;
        this.typeTo = typeTo;
        this.result = result;
    }
}
