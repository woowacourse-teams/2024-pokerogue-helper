// import { afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";
//
// import { WeatherType } from "#app/enums/weather-type";
// import { arenaFlyout } from "#app/locales/ko/arena-flyout";
// import { weather } from "#app/locales/ko/weather.ts";
// import { Weather } from "#app/data/weather.ts";
// import * as fs from "fs";
// import * as path from 'path';
// import i18next, { init } from "i18next";
// import { exit } from "process";
// import GameManager from "#test/utils/gameManager";
//
// const filePath = path.join(__dirname, 'weather.txt');
// const logStream = fs.createWriteStream(filePath, { flags: 'w' });
// console.log = (...args: any[]) => {
//   const message = args.map(arg => typeof arg === 'object' ? JSON.stringify(arg) : arg).join(' ');
//   logStream.write(message + '\n');
// };
//
// describe("Weather", () => {
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
// //   const print = (((x: PokemonForm, sp: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes) => {
// //     var ret = []
// //     // id
// //     ret.push(pokemonId)
// //     ret.push(x.speciesId)
// //     //name
// //     ret.push(sp.name +"-"+ x.formName)
// //     // type
// //     ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
// //     ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
// //     // ability (todo: passive)
// //     ret.push(new Ability(x.ability1, x.generation).name)
// //     ret.push(new Ability(x.ability2, x.generation).name)
// //     ret.push(new Ability(x.abilityHidden, x.generation).name)
// //     ret.push(new Ability(passive, x.generation).name)
// //     // metadata
// //     ret.push(x.generation)
// //     ret.push(sp.legendary)
// //     ret.push(sp.subLegendary)
// //     ret.push(sp.mythical)
// //     // evolution (speciesId, level)
// //     ret.push(sp.getEvolutionLevels())
// //     // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
// //     ret.push(x.baseTotal)
// //     ret.push(x.baseStats)
// //     ret.push(x.height)
// //     ret.push(x.weight)
// //     // form
// //     ret.push(sp.canChangeForm)
// //     // eggMoves
// //     var tmp = []
// //     for(let i =0; i < eggMoves.length; i++){
// //       tmp.push(eggMoves[i].name)
// //     }
// //     ret.push(tmp)
// //     // moves
// //     tmp = []
// //     for(let i =0; i < moves.length; i++){
// //       tmp.push(moves[i][1].name)
// //     }
// //     ret.push(tmp)
//
//
// //     tmp = []
// //     for(let i =0; i < biomes.length; i++){
// //       tmp.push(getBiomeName(biomes[i]))
// //     }
// //     ret.push(tmp)
//
// //     console.log(ret.join(" / "))
// //   }));
//
//
//
// //   const print2 = (((x: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes
// //   ) => {
// //     var ret = []
// //     // id
// //     ret.push(pokemonId)
// //     ret.push(x.speciesId)
// //     //name
// //     ret.push(x.name)
// //     // type
// //     ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
// //     ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
// //     // ability (todo: passive)
// //     ret.push(new Ability(x.ability1, x.generation).name)
// //     ret.push(new Ability(x.ability2, x.generation).name)
// //     ret.push(new Ability(x.abilityHidden, x.generation).name)
// //     ret.push(new Ability(passive, x.generation).name)
// //     // metadata
// //     ret.push(x.generation)
// //     ret.push(x.legendary)
// //     ret.push(x.subLegendary)
// //     ret.push(x.mythical)
// //     // evolution (speciesId, level)
// //     ret.push(x.getEvolutionLevels())
// //     // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
// //     ret.push(x.baseTotal)
// //     ret.push(x.baseStats)
// //     ret.push(x.height)
// //     ret.push(x.weight)
// //     // form
// //     ret.push(x.canChangeForm)
// //     // eggMoves
// //     var tmp = []
// //     for(let i =0; i < eggMoves.length; i++){
// //       tmp.push(eggMoves[i].name)
// //     }
// //     ret.push(tmp)
// //     // moves
// //     tmp = []
// //     for(let i =0; i < moves.length; i++){
// //       tmp.push(moves[i][1].name)
// //     }
// //     ret.push(tmp)
//
//
// //     tmp = []
// //     for(let i =0; i < biomes.length; i++){
// //       tmp.push(getBiomeName(biomes[i]))
// //     }
// //     ret.push(tmp)
//
// //     console.log(ret.join(" / "))
// //   }));
//
//   function getEnumValues<T>(enumObj: T): T[keyof T][] {
//     return Object.values(enumObj).filter(value => typeof value === 'number') as T[keyof T][];
//   }
//
//   function toCamelCase(str: string): string {
//     return str
//       .toLowerCase()
//       .replace(/_./g, match => match.charAt(1).toUpperCase());
//   }
//
//   it("날씨 정보 추출 테스트", async () => {
//     console.log("이름 / 출력 메시지 / 효과")
//     console.log()
//     console.log("없음 / 없음 / 없음")
//
//     const weatherTypes: WeatherType[] = getEnumValues(WeatherType);
//     for (let i = 1; i < weatherTypes.length; i++) {
//         var ret = []
//         ret.push(i18next.t(`arenaFlyout:${toCamelCase(WeatherType[i])}`))
//         ret.push(i18next.t(`weather:${toCamelCase(WeatherType[i]) + "LapseMessage"}`))
//         if (i === 1) {
//           ret.push("불꽃 타입 기술의 위력이 1.5배가 된다 , 물 타입 기술의 위력이 0.5배가 된다")
//         }
//         if (i === 2) {
//           ret.push("물 타입 기술의 위력이 1.5배가 된다 , 불꽃 타입 기술의 위력이 0.5배가 된다")
//         }
//         if (i === 3) {
//           ret.push("바위 또는 땅 또는 강철 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다 , 바위 타입 포켓몬의 특수방어가 1.5배가 된다")
//         }
//         if (i === 4) {
//           ret.push("얼음 타입 포켓몬이 아니면 매 턴마다 체력의 1/16의 데미지를 받는다")
//         }
//         if (i === 5) {
//           ret.push("얼음 타입 포켓몬의 방어가 1.5배 올라간다")
//         }
//         if (i === 6) {
//           ret.push("모든 기술의 명중률이 0.9배가 된다")
//         }
//         if (i === 7) {
//           ret.push("불타입 기술은 모두 실패한다 , 불꽃 타입 기술의 위력이 1.5배가 된다 , 물 타입 기술의 위력이 1.5배가 된다")
//         }
//         if (i === 8) {
//           ret.push("물타입 기술은 모두 실패한다 , 불꽃 타입 기술의 위력이 1.5배가 된다 , 물 타입 기술의 위력이 0.5배가 된다")
//         }
//         if (i === 9) {
//           ret.push("비행 타입의 약점을 없애준다")
//         }
//         console.log(ret.join(" / "))
//     }
//
//     // for (let i = 0; i < allSpecies.length; i++) {
//     //   let x = allSpecies[i]
//
//     //   var passive = starterPassiveAbilities[x.getRootSpeciesId()]
//     //   var eggMoves: Moves[] = speciesEggMoves[x.getRootSpeciesId()].map((value, index, arr) => allMoves[value])
//     //   var moves = pokemonSpeciesLevelMoves[x.getRootSpeciesId()].map((value, index, arr) => [value[0], allMoves[value[1]]])
//     //   var biomes = biomeMap.get(Species[x.speciesId])
//     //   print2(x, pokemonId++, passive, eggMoves, moves, biomes)
//     //   if (x.canChangeForm) {
//     //     for (var form of x.forms) {
//     //       // if(form.formName.toLowerCase() === "normal"){
//     //       //   continue
//     //       // }
//     //       print(form, x, pokemonId++, passive, eggMoves, moves, biomes)
//     //     }
//     //   }
//     // }
//   });
// });
