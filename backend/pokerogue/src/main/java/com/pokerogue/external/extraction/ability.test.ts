// import { afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
//
// import { Abilities } from "#app/enums/abilities.js";
// import { ability } from "#app/locales/ko/ability"
// import { Moves } from "#app/enums/moves"
// import { Type } from "#app/data/type"
// import { allMoves } from "#app/data/move.js";
// import PokemonSpecies, { starterPassiveAbilities, initSpecies, allSpecies, PokemonSpeciesForm, PokemonForm } from "#app/data/pokemon-species.js";
// import * as fs from "fs";
// import * as path from 'path';
// import { speciesEggMoves } from "#app/data/egg-moves"
// import { Species } from "#app/enums/species.ts";
// import i18next, { init } from "i18next";
// import { pokemonSpeciesLevelMoves } from "#app/data/pokemon-level-moves.ts";
// import { biomePokemonPools, getBiomeName, initBiomes, pokemonBiomes } from "#app/data/biomes.ts";
// import { Biome } from "#app/enums/biome.ts";
// import { exit } from "process";
// import { Ability, allAbilities } from "#app/data/ability.ts";
// import GameManager from "#test/utils/gameManager";
// import { enConfig } from "#app/locales/en/config.js";
// import { koConfig } from "#app/locales/ko/config.js";
//
// const filePath = path.join(__dirname, 'ability.txt');
// const logStream = fs.createWriteStream(filePath, { flags: 'w' });
// console.log = (...args: any[]) => {
//   const message = args.map(arg => typeof arg === 'object' ? JSON.stringify(arg) : arg).join(' ');
//   logStream.write(message + '\n');
// };
//
// describe("Ability", () => {
//   let phaserGame: Phaser.Game;
//   let game: GameManager;
//   const TIMEOUT = 1000 * 20;
//
//   beforeAll(() => {
//   });
//
//   afterEach(() => {
//   });
//
//   beforeEach(() => {
//   });
//
//   // const print = (((x: PokemonForm, sp: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes) => {
//   //   var ret = []
//   //   // id
//   //   ret.push(pokemonId)
//   //   ret.push(x.speciesId)
//   //   //name
//   //   ret.push(sp.name +"-"+ x.formName)
//   //   // type
//   //   ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
//   //   ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
//   //   // ability (todo: passive)
//   //   ret.push(new Ability(x.ability1, x.generation).name)
//   //   ret.push(new Ability(x.ability2, x.generation).name)
//   //   ret.push(new Ability(x.abilityHidden, x.generation).name)
//   //   ret.push(new Ability(passive, x.generation).name)
//   //   // metadata
//   //   ret.push(x.generation)
//   //   ret.push(sp.legendary)
//   //   ret.push(sp.subLegendary)
//   //   ret.push(sp.mythical)
//   //   // evolution (speciesId, level)
//   //   ret.push(sp.getEvolutionLevels())
//   //   // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
//   //   ret.push(x.baseTotal)
//   //   ret.push(x.baseStats)
//   //   ret.push(x.height)
//   //   ret.push(x.weight)
//   //   // form
//   //   ret.push(sp.canChangeForm)
//   //   // eggMoves
//   //   var tmp = []
//   //   for(let i =0; i < eggMoves.length; i++){
//   //     tmp.push(eggMoves[i].name)
//   //   }
//   //   ret.push(tmp)
//   //   // moves
//   //   tmp = []
//   //   for(let i =0; i < moves.length; i++){
//   //     tmp.push(moves[i][1].name)
//   //   }
//   //   ret.push(tmp)
//
//
//   //   tmp = []
//   //   for(let i =0; i < biomes.length; i++){
//   //     tmp.push(getBiomeName(biomes[i]))
//   //   }
//   //   ret.push(tmp)
//
//   //   console.log(ret.join(" / "))
//   // }));
//
//
//
//   // const print2 = (((x: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes
//   // ) => {
//   //   var ret = []
//   //   // id
//   //   ret.push(pokemonId)
//   //   ret.push(x.speciesId)
//   //   //name
//   //   ret.push(x.name)
//   //   // type
//   //   ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
//   //   ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
//   //   // ability (todo: passive)
//   //   ret.push(new Ability(x.ability1, x.generation).name)
//   //   ret.push(new Ability(x.ability2, x.generation).name)
//   //   ret.push(new Ability(x.abilityHidden, x.generation).name)
//   //   ret.push(new Ability(passive, x.generation).name)
//   //   // metadata
//   //   ret.push(x.generation)
//   //   ret.push(x.legendary)
//   //   ret.push(x.subLegendary)
//   //   ret.push(x.mythical)
//   //   // evolution (speciesId, level)
//   //   ret.push(x.getEvolutionLevels())
//   //   // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
//   //   ret.push(x.baseTotal)
//   //   ret.push(x.baseStats)
//   //   ret.push(x.height)
//   //   ret.push(x.weight)
//   //   // form
//   //   ret.push(x.canChangeForm)
//   //   // eggMoves
//   //   var tmp = []
//   //   for(let i =0; i < eggMoves.length; i++){
//   //     tmp.push(eggMoves[i].name)
//   //   }
//   //   ret.push(tmp)
//   //   // moves
//   //   tmp = []
//   //   for(let i =0; i < moves.length; i++){
//   //     tmp.push(moves[i][1].name)
//   //   }
//   //   ret.push(tmp)
//
//
//   //   tmp = []
//   //   for(let i =0; i < biomes.length; i++){
//   //     tmp.push(getBiomeName(biomes[i]))
//   //   }
//   //   ret.push(tmp)
//
//   //   console.log(ret.join(" / "))
//   // }));
//
//   it("특성 정보 추출 테스트", async () => {
//     console.log("이름 / 효과 설명 / 세대 / 해당 특성을 가진 포켓몬")
//     console.log()
//
//     let p = allAbilities;
//     var abilityId: integer = 1
//     let q = allSpecies
//
//     for (let i = 0; i < allAbilities.length; i++) {
//       var ret = []
//       let x = allAbilities[i]
//       if (x.id === Abilities.NONE) {
//         continue
//       }
//       var name = x.name
//       ret.push(name)
//       var description = x.description
//       ret.push(description)
//       var generation = x.generation
//       ret.push(generation)
//       var abret = []
//       for (let i = 0; i < allSpecies.length; i++) {
//         let y = allSpecies[i]
//         var passive = starterPassiveAbilities[y.getRootSpeciesId()]
//         if (y.ability1 === x.id || y.ability2 === x.id || y.abilityHidden === x.id || passive === x.id) {
//           abret.push(y.name)
//         }
//         if (y.canChangeForm) {
//           for (var form of y.forms) {
//             if (form.ability1 === x.id || form.ability2 === x.id || form.abilityHidden === x.id) {
//               if (form.formName === "Normal") {
//                 continue
//               }
//               abret.push(y.name + "-" + form.formName)
//             }
//           }
//         }
//       }
//       ret.push(abret.join(" , "))
//       console.log(ret.join(" / "))
//     }
//   });
// });
