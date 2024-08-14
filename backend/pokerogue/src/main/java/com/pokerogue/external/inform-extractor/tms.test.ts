import { afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";

import { Moves } from "#app/enums/moves"
import { allMoves } from "#app/data/move.js";
import PokemonSpecies, { starterPassiveAbilities, initSpecies, allSpecies, PokemonSpeciesForm, PokemonForm } from "#app/data/pokemon-species.js";
import * as fs from "fs";
import * as path from 'path';
import { Species } from "#app/enums/species.js";
import i18next, { init } from "i18next";
import { exit } from "process";
import { tmSpecies } from "#app/data/tms.js";

const filePath = path.join(__dirname, 'tms.txt');
const logStream = fs.createWriteStream(filePath, { flags: 'w' });
console.log = (...args: any[]) => {
  const message = args.map(arg => typeof arg === 'object' ? JSON.stringify(arg) : arg).join(' ');
  logStream.write(message + '\n');
};

describe("Pokemon", () => {
  beforeAll(() => {
  });

  afterEach(() => {
  });

  beforeEach(() => {
  });

//   const print = (((x: PokemonForm, sp: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes) => {
//       var ret = []
//       // id
//       ret.push(pokemonId)
//       ret.push(x.speciesId)
//       //name
//       ret.push(sp.name +"-"+ x.formName)
//       // type
//       ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
//       ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
//       // ability (todo: passive)
//       ret.push(new Ability(x.ability1, x.generation).name)
//       ret.push(new Ability(x.ability2, x.generation).name)
//       ret.push(new Ability(x.abilityHidden, x.generation).name)
//       ret.push(new Ability(passive, x.generation).name)
//       // metadata
//       ret.push(x.generation)
//       ret.push(sp.legendary)
//       ret.push(sp.subLegendary)
//       ret.push(sp.mythical)
//       // evolution (speciesId, level)
//       ret.push(sp.getEvolutionLevels())
//       // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
//       ret.push(x.baseTotal)
//       ret.push(x.baseStats)
//       ret.push(x.height)
//       ret.push(x.weight)
//       // form
//       ret.push(sp.canChangeForm)
//       // eggMoves
//       var tmp = []
//       for(let i =0; i < eggMoves.length; i++){
//         tmp.push(eggMoves[i].name) 
//       }
//       ret.push(tmp)
//       // moves
//       tmp = []
//       for(let i =0; i < moves.length; i++){
//         tmp.push(moves[i][1].name)
//       }
//       ret.push(tmp)
      
  
//       tmp = []
//       for(let i =0; i < biomes.length; i++){
//         tmp.push(getBiomeName(biomes[i]))
//       }
//       ret.push(tmp)
  
//       console.log(ret.join(" / "))
//   }));



//   const print2 = (((x: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes
//   ) => {

//     var ret = []
//     // id
//     ret.push(pokemonId)
//     ret.push(x.speciesId)
//     //name
//     ret.push(x.name)
//     // type
//     ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
//     ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
//     // ability (todo: passive)
//     ret.push(new Ability(x.ability1, x.generation).name)
//     ret.push(new Ability(x.ability2, x.generation).name)
//     ret.push(new Ability(x.abilityHidden, x.generation).name)
//     ret.push(new Ability(passive, x.generation).name)
//     // metadata
//     ret.push(x.generation)
//     ret.push(x.legendary)
//     ret.push(x.subLegendary)
//     ret.push(x.mythical)
//     // evolution (speciesId, level)
//     ret.push(x.getEvolutionLevels())
//     // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
//     ret.push(x.baseTotal)
//     ret.push(x.baseStats)
//     ret.push(x.height)
//     ret.push(x.weight)
//     // form
//     ret.push(x.canChangeForm)
//     // eggMoves
//     var tmp = []
//     for(let i =0; i < eggMoves.length; i++){
//       tmp.push(eggMoves[i].name) 
//     }
//     ret.push(tmp)
//     // moves
//     tmp = []
//     for(let i =0; i < moves.length; i++){
//       tmp.push(moves[i][1].name)
//     }
//     ret.push(tmp)
    

//     tmp = []
//     for(let i =0; i < biomes.length; i++){
//       tmp.push(getBiomeName(biomes[i]))
//     }
//     ret.push(tmp)

//     console.log(ret.join(" / "))
//   }));

  function getEnumValues<T>(enumObj: T): T[keyof T][] {
    return Object.values(enumObj).filter(value => typeof value === 'number') as T[keyof T][];
  }
  function toCamelCase(str: string): string {
    return str
      .toLowerCase()
      .replace(/_./g, match => match.charAt(1).toUpperCase());
  }

  it("포켓몬이 배울 수 있는 기술 정보 추출 테스트", async () => {

    let p = allSpecies;
    var pokemonId: integer = 1

    // initBiomes();
    // const biomeMap = new Map<Species, Biome[]>();
    // for (let i = 0; i < pokemonBiomes.length; i++) {
    //   var species = Species[pokemonBiomes[i][0]]
    //   var biomeEntries = pokemonBiomes[i][3]
    //   biomeMap.set(species, biomeEntries
    //     .map((a, b, c) => a[0])
    //     .filter((value, index, self) => self.indexOf(value) === index))
    // }
    const moves: Moves[] = getEnumValues(Moves);
    const species: Species[] = getEnumValues(Species);
    console.log("이름 / 배울 수 있는 기술 목록")
    console.log()
    for (let i = 0; i < allSpecies.length; i++) {
        var x = allSpecies[i]
        var ret = []
        ret.push(x.name)
        var ts = []
        for (let j = 0; j < allMoves.length; j++) {
            var y = tmSpecies[allMoves[j].id]
            if (Array.isArray(y)) {
                for (let k = 0; k < y.length; k++) {
                    var speciesEntry = y[k];
        
                    // speciesEntry가 배열인지 확인하고 처리
                    if (Array.isArray(speciesEntry)) {
                        // 배열의 첫 번째 항목이 x.speciesId와 일치하는지 확인
                        if (speciesEntry[0] === x.speciesId) {
                            ts.push(allMoves[j].name);
                        }
                    } else {
                        // speciesEntry가 단일 Species인지 확인
                        if (speciesEntry === x.speciesId) {
                            ts.push(allMoves[j].name);
                        }
                    }
                }
            }
        }
        ret.push(ts.join(" , "))
        console.log(ret.join(" / "))
    }
  });
});
