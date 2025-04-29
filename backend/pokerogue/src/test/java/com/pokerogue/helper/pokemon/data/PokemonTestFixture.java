package com.pokerogue.helper.pokemon.data;

import com.pokerogue.helper.type.data.Type;
import java.util.Arrays;
import java.util.List;

public class PokemonTestFixture {
    public static List<Evolution> BULBASAUR_EVOLUTIONS = Arrays.asList(
            new Evolution("bulbasaur", 16, "ivysaur", null, null),
            new Evolution("ivysaur", 32, "venusaur", null, null)
    );

    public static List<LevelMove> BULBASAUR_LEVEL_MOVES = Arrays.asList(
            new LevelMove(1, "tackle"),
            new LevelMove(1, "growl"),
            new LevelMove(3, "vine_whip"),
            new LevelMove(6, "growth"),
            new LevelMove(9, "leech_seed"),
            new LevelMove(12, "razor_leaf"),
            new LevelMove(15, "poison_powder"),
            new LevelMove(15, "sleep_powder"),
            new LevelMove(18, "seed_bomb"),
            new LevelMove(21, "take_down"),
            new LevelMove(24, "sweet_scent"),
            new LevelMove(27, "synthesis"),
            new LevelMove(30, "worry_seed"),
            new LevelMove(33, "power_whip"),
            new LevelMove(36, "solar_beam")
    );

    public static Pokemon BULBASAUR = new Pokemon(
            "gfdsgfadf",
            "bulbasaur", // id
            "bulbasaur", // imageId
            1, // pokedexNumber
            "bulbasaur", // name
            "bulbasaur", // speciesName
            false, // canChangeForm
            "", // formName
            64, // baseExp
            50, // friendship
            Arrays.asList(Type.GRASS, Type.POISON), // types
            List.of("overgrow"), // normalAbilityIds
            "chlorophyll", // hiddenAbilityId
            "grassy_surge", // passiveAbilityId
            1, // generation
            false, // legendary
            false, // subLegendary
            false, // mythical
            BULBASAUR_EVOLUTIONS, // evolutions
            List.of(), // formChanges
            318, // baseTotal
            45, // hp
            49, // attack
            49, // defense
            65, // specialAttack
            65, // specialDefense
            45, // speed
            0.7, // height
            6.9, // weight
            Arrays.asList("sappy_seed", "sludge_wave", "earth_power", "matcha_gotcha"), // eggMoveIds
            BULBASAUR_LEVEL_MOVES, // levelMoves
            Arrays.asList(
                    "swords_dance", "cut", "body_slam", "take_down", "double_edge", "strength", "solar_beam",
                    "toxic", "double_team", "light_screen", "reflect", "amnesia", "flash", "rest", "substitute",
                    "snore", "curse", "protect", "sludge_bomb", "mud_slap", "outrage", "giga_drain", "endure",
                    "charm", "false_swipe", "swagger", "attract", "sleep_talk", "return", "frustration", "safeguard",
                    "synthesis", "hidden_power", "sunny_day", "rock_smash", "facade", "nature_power", "helping_hand",
                    "knock_off", "weather_ball", "bullet_seed", "magical_leaf", "worry_seed", "seed_bomb", "energy_ball",
                    "leaf_storm", "power_whip", "grass_knot", "venoshock", "acid_spray", "round", "echoed_voice",
                    "grass_pledge", "work_up", "grassy_terrain", "confide", "grassy_glide", "tera_blast", "trailblaze"
            ), // technicalMachineMoveIds
            List.of("grass"), // biomeIds,
            "en"
    );

    public static List<Evolution> CHARMANDER_EVOLUTIONS = List.of(
            new Evolution("charmander", 16, "charmeleon", null, null),
            new Evolution("charmeleon", 36, "charizard", null, null)
    );

    public static List<LevelMove> CHARMANDER_LEVEL_MOVES = List.of(
            new LevelMove(1, "scratch"),
            new LevelMove(1, "growl"),
            new LevelMove(4, "ember"),
            new LevelMove(8, "smokescreen"),
            new LevelMove(12, "dragon_breath"),
            new LevelMove(17, "fire_fang"),
            new LevelMove(20, "slash"),
            new LevelMove(24, "flamethrower"),
            new LevelMove(28, "scary_face"),
            new LevelMove(32, "fire_spin"),
            new LevelMove(36, "inferno"),
            new LevelMove(40, "flare_blitz")
    );

    public static Pokemon CHARMANDER = new Pokemon(
            "gfdsgfadf",
            "charmander", // id
            "charmander", // imageId
            4, // pokedexNumber
            "charmander", // name
            "charmander", // speciesName
            false, // canChangeForm
            "", // formName
            62, // baseExp
            50, // friendship
            List.of(Type.FIRE), // types
            List.of("blaze"), // normalAbilityIds
            "solar_power", // hiddenAbilityId
            "beast_boost", // passiveAbilityId
            1, // generation
            false, // legendary
            false, // subLegendary
            false, // mythical
            CHARMANDER_EVOLUTIONS, // evolutions
            List.of(), // formChanges
            309, // baseTotal
            39, // hp
            52, // attack
            43, // defense
            60, // specialAttack
            50, // specialDefense
            65, // speed
            0.6, // height
            8.5, // weight
            List.of("dragon_dance", "bitter_blade", "earth_power", "oblivion_wing"), // eggMoveIds
            CHARMANDER_LEVEL_MOVES, // levelMoves
            List.of(
                    "mega_punch", "fire_punch", "thunder_punch", "swords_dance", "cut", "mega_kick", "body_slam", "take_down", "double_edge",
                    "roar", "flamethrower", "counter", "strength", "fire_spin", "dig", "toxic", "double_team", "reflect", "fire_blast",
                    "swift", "rest", "rock_slide", "substitute", "snore", "curse", "protect", "scary_face", "mud_slap", "outrage",
                    "endure", "false_swipe", "swagger", "attract", "sleep_talk", "return", "frustration", "iron_tail", "metal_claw",
                    "hidden_power", "sunny_day", "crunch", "rock_smash", "beat_up", "heat_wave", "will_o_wisp", "facade", "focus_punch",
                    "helping_hand", "brick_break", "weather_ball", "air_cutter", "overheat", "rock_tomb", "aerial_ace", "dragon_claw",
                    "dragon_dance", "fling", "flare_blitz", "dragon_pulse", "focus_blast", "shadow_claw", "fire_fang", "hone_claws",
                    "flame_charge", "round", "echoed_voice", "incinerate", "acrobatics", "fire_pledge", "dragon_tail", "work_up",
                    "confide", "power_up_punch", "breaking_swipe", "tera_blast", "temper_flare"
            ), // technicalMachineMoveIds
            List.of("volcano"), // biomeIds
            "en"
    );
}
