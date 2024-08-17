import { afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";

import { Abilities } from "#app/enums/abilities.js"
import { ability } from "#app/locales/ko/ability"
import { Moves } from "#app/enums/moves"
import { Type } from "#app/data/type"
import { MoveCategory, MoveFlags, MoveTarget, allMoves } from "#app/data/move.js";
import PokemonSpecies, { starterPassiveAbilities, initSpecies, allSpecies, PokemonSpeciesForm, PokemonForm } from "#app/data/pokemon-species.js";
import * as fs from "fs";
import * as path from 'path';
import { speciesEggMoves } from "#app/data/egg-moves"
import { Species } from "#app/enums/species.js";
import i18next, { init } from "i18next";
import { pokemonSpeciesLevelMoves } from "#app/data/pokemon-level-moves.js";
import { biomeLinks, biomePokemonPools, BiomePoolTier, biomeTrainerPools, getBiomeName, initBiomes, pokemonBiomes } from "#app/data/biomes.js";
import { Biome } from "#app/enums/biome.js";
import { TimeOfDay } from "#app/enums/time-of-day.js";
import { TrainerType } from "#enums/trainer-type";
import { trainerClasses, trainerNames } from "#app/locales/de/trainers.js";
import { TrainerPoolTier } from "#app/data/trainer-config.js";
import { move } from "#app/locales/de/move";
import { all } from "axios";

let filePath = path.join(__dirname, 'move.txt');
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

    // 원하는 순서 정의
    const order = [
        "id",
        "name",
        "nameAppend",
        "effect",
        "type",
        "defaultType",
        "category",
        "moveTarget",
        "power",
        "accuracy",
        "pp",
        "chance",
        "priority",
        "generation",
        "flags",
    ];

    // 객체의 키값들을 원하는 순서로 정렬하여 새로운 객체 생성


    const print = () => {
        for (var i in allMoves) {
            var move = allMoves[i]
            const sortedMove = order
            .map(key => {
                if(key === "moveTarget"){
                    return MoveTarget[move[key]] ? MoveTarget[move[key]] : "EMPTY"
                }
                if(key === "category"){
                    return MoveCategory[move[key]] ? MoveCategory[move[key]] : "EMPTY"
                }
                if(key === "flags"){
                    return MoveFlags[move[key]] ? MoveFlags[move[key]] : "EMPTY"
                }
                if(key === "type" || key === "defaultType"){
                    var tmp = i18next.t(`pokemonInfo:Type:${Type[move[key]]}`);
                    return tmp ? tmp : "EMPTY"
                }
                return move[key] ? move[key] : "EMPTY"
            })

            console.log(sortedMove.join(" / "))
            // console.log(Object.values(allMoves[i]).join(" / "))
        }
    }

    const printMetaData = () => {
        console.log(`
        "id",
        "name",
        "nameAppend",
        "effect",
        "type",
        "defaultType",
        "category",
        "moveTarget",
        "power",
        "accuracy",
        "pp",
        "chance",
        "priority",
        "generation",
        "flags",
        //todo: "attrs",
        //todo: "conditions",
        
    `)
    }

    it("테스트 환경입니다 >_< ", async () => {
        printMetaData()
        print()
    })

})
