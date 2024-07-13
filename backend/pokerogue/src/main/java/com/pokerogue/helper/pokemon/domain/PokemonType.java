package com.pokerogue.helper.pokemon.domain;

import com.pokerogue.helper.type.domain.Type;
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
@Table(name = "pokemon_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "pokemon_id", nullable = false)
    @ManyToOne
    private Pokemon pokemon;

    @JoinColumn(name = "type_id", nullable = false)
    @ManyToOne
    private Type type;

    public String getCircleTypeImage() {
        return type.getCircleImage();
    }
}
