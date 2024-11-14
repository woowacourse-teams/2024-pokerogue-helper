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
import { biomeLinks, biomePokemonPools, BiomePoolTier, biomeTrainerPools, getBiomeName, initBiomes, pokemonBiomes } from "#app/data/biomes.js";
import { Biome } from "#app/enums/biome.js";
import { TimeOfDay } from "#app/enums/time-of-day.js";
import { TrainerType } from "#enums/trainer-type";
import { trainerClasses, trainerNames } from "#app/locales/de/trainers.js";
import { TrainerPoolTier } from "#app/data/trainer-config.js";

let filePath = path.join(__dirname, 'biome-trainer.txt');
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


    enum BiomePoolTierToKo {
        COMMON = "보통",
        UNCOMMON = "드묾",
        RARE = "레어",
        SUPER_RARE = "슈퍼 레어",
        ULTRA_RARE = "울트라 레어",
        BOSS = "보스",
        BOSS_RARE = "레어 보스",
        BOSS_SUPER_RARE = "슈퍼 레어 보스",
        BOSS_ULTRA_RARE = "슈퍼 울트라 레어 보스"
      }
      


    const print = () => {
        // i18next.changeLanguage('ko')
        var ret =[]

        for (var i in biomeTrainerPools) {
            var z = biomeTrainerPools[i];
            // console.log(z)
            for (var j in z) {
                var tmp = []
                var z2 = z[j]
                tmp.push(
                    getBiomeName(i),
                    BiomePoolTierToKo[BiomePoolTier[j]],
                    z2.map(r => {
                        // 먼저 키를 생성합니다.
                        const nameForCall = TrainerType[r].toLowerCase().replace(/\s/g, "_");
                        var key = ''
                        if (trainerClasses[nameForCall]) {
                             key = `trainerClasses:${nameForCall}`;
                        }else{
                            key = `trainerNames:${nameForCall}`;
                        }
                        
                        // 해당 키를 사용하여 번역된 문자열을 가져옵니다.
                        const translated = i18next.t(key) as string;

                        // 결과 문자열을 반환합니다.
                        return `${translated}`;
                    }),
                )
                ret.push(tmp.join(" / "))
            }
        }

        for(var x in ret){
            console.log(ret[x])
        }

    }

    const printMetaData = () => {
        console.log(`
    Biome
    Tier
    Trainer[]
    `)
    }

    it("테스트 환경입니다 >_< ", async () => {
        printMetaData()
        print()
    })

})
