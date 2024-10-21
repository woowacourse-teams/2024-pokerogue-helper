import { afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";

import { Abilities } from "#app/enums/abilities.js"
import { ability } from "#app/locales/ko/ability"
import { Moves } from "#app/enums/moves"
import { Type } from "#app/data/type"
import { allMoves } from "#app/data/move.js";
import PokemonSpecies, { starterPassiveAbilities, initSpecies, allSpecies, PokemonSpeciesForm, PokemonForm } from "#app/data/pokemon-species.js";
import * as fs from "fs";
import * as path from 'path';
import { speciesEggMoves } from "#app/data/egg-moves"
import { Species } from "#app/enums/species.js";
import i18next, { init } from "i18next";
import { pokemonSpeciesLevelMoves } from "#app/data/pokemon-level-moves.js";
import { biomeLinks, biomePokemonPools, BiomePoolTier, getBiomeName, initBiomes, pokemonBiomes } from "#app/data/biomes.js";
import { Biome } from "#app/enums/biome.js";
import { TimeOfDay } from "#app/enums/time-of-day.js";

let filePath = path.join(__dirname, 'biome-names.txt');
const logStream = fs.createWriteStream(filePath, { flags: 'w' });
console.log = (...args: any[]) => {
    const message = args.map(arg => typeof arg === 'object' ? JSON.stringify(arg) : arg).join(' ');
    logStream.write(message + '\n');
};

// 포켓몬,
// 기술,
// 바이옴,
// 진화체(트리거),
// 날씨, 
// 특성

describe("Pokemon", () => {
    beforeAll(() => {
    });

    afterEach(() => {
    });

    beforeEach(() => {
    });

    const print = (((x: PokemonForm, sp: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes) => {
        var ret = []

        // console.log(ret.join(" / "))
    }));


    const printBiomeLinks = () => {

        for (var e in biomeLinks) {
            var key = Biome[e]
            var value = biomeLinks[e]
            if (!Array.isArray(value)) {
                value = [value]
            }

            value = value.map(r => {
                if (!Array.isArray(r)) {
                    return getBiomeName(r)
                }
                return getBiomeName(r[0]);
            })
            console.log(getBiomeName(Biome[key]) + " " + value)
        }
    }

    const printMetaData = () => {
        console.log(`
        links
                    `)
    }

    it("테스트 환경입니다 >_< ", async () => {
        // printMetaData()
        // printBiomeLinks()
        var x = Object.values(Biome)
        
        for (var value in x){
            console.log(getBiomeName(x[value]))
        }

        // console.log(v)
    })

})
