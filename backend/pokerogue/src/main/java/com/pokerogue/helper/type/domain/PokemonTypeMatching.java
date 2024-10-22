package com.pokerogue.helper.type.domain;

import jakarta.persistence.Column;
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
@Table(name = "pokemon_type_matching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonTypeMatching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_type_id", nullable = false)
    private PokemonType fromType;

    @ManyToOne
    @JoinColumn(name = "to_type_id", nullable = false)
    private PokemonType toType;

    @Column(name = "result", nullable = false)
    private Double result;

    public PokemonTypeMatching(PokemonType fromType, PokemonType toType, Double result) {
        this.fromType = fromType;
        this.toType = toType;
        this.result = result;
    }

    public String getFromTypeName() {
        return fromType.getName();
    }

    public String getToTypeName() {
        return toType.getName();
    }
}
