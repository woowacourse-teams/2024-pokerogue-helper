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
@Table(name = "pokemon_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "ko_name", nullable = false)
    private String koName;

    @Column(name = "image", nullable = false)
    private String image;

    public PokemonType(String name, String koName, String image) {
        this.name = name;
        this.koName = koName;
        this.image = image;
    }
}
