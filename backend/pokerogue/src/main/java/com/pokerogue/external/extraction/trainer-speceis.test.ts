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
import { signatureSpecies } from "#app/data/trainer-config";
import { TrainerType } from "#enums/trainer-type";
import { trainerClasses } from "#app/locales/de/trainers";
import { trainerNamePools } from "#app/data/trainer-names";
import { titles, trainerNames } from "#app/locales/ko/trainers";

let filePath = path.join(__dirname, 'trainer-species.txt');
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


    const print = () => {

        for (var a in signatureSpecies) {
            var ret = []
            ret.push(getTrainerKoName(TrainerType[a]))
            var tmp = []
            for (var b in signatureSpecies[a]) {
                if (Array.isArray(signatureSpecies[a][b])) {
                    for (var c in signatureSpecies[a][b]){
                        tmp.push(signatureSpecies[a][b][c])
                    }
                    continue
                }
                tmp.push(signatureSpecies[a][b])
            }
            ret.push(tmp.map(r=>i18next.t(`pokemon:${Species[r].toLowerCase()}`)))
            console.log(ret.join(" / "))
        }
    }

    const printMetaData = () => {
        console.log(`
        trainer
        species[]
                    `)
    }


    const getTrainerKoName = (r) => {
        const nameForCall = TrainerType[r].toLowerCase().replace(/\s/g, "_");
        var key = ''
        if (trainerClasses[nameForCall]) {
            key = `trainerClasses:${nameForCall}`;
        } else if (trainerNames[nameForCall]) {
            key = `trainerNames:${nameForCall}`;
        } else if (titles[nameForCall]) {
            key = `titles:${nameForCall}`;
        } else {
            key = nameForCall
        }

        // 해당 키를 사용하여 번역된 문자열을 가져옵니다.
        const translated = i18next.t(key) as string;

        // 결과 문자열을 반환합니다.
        return `${translated}`;
    }

    it("테스트 환경입니다 >_< ", async () => {
        printMetaData()
        print()
    })

})
