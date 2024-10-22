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

let filePath = path.join(__dirname, 'biome.txt');
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


  const printBiomeSpecies = () => {
    for (const b of Object.keys(biomePokemonPools)) {
      const BIOME = b
      for (const t of Object.keys(biomePokemonPools[b])) {
        const TIER = BiomePoolTier[t]
        for (const tod of Object.keys(biomePokemonPools[b][t])) {
          let ret = []
          const TOD = TimeOfDay[tod]
          const biomeTierTimePool = biomePokemonPools[b][t][tod];
          for (let e = 0; e < biomeTierTimePool.length; e++) {
            let x = biomeTierTimePool[e]
            if (typeof x === 'object') {
              let y = Object.values(x)
              for (let i = 0; i < y.length; i++) {
                ret.push(y[i][0])
              }
              continue
            }
            ret.push(x)
          }
          
          console.log(getBiomeName(BIOME), TOD, TIER, ret.map(r => i18next.t(`pokemon:${Species[r].toLowerCase()}`)))
        }
      }
      
    }
  }

  const printMetaData = () => {
    console.log(`
    Biome
    TimeOfDay
    Tier
    Species[]
    `)
  }

  it("테스트 환경입니다 >_< ", async () => {
    printMetaData()
    printBiomeSpecies()
  })

})
