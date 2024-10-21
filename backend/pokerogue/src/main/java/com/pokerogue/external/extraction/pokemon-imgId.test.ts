import { afterEach, beforeAll, beforeEach, describe, expect, it, vi } from "vitest";

import { Abilities } from "#app/enums/abilities.js";
import { ability } from "#app/locales/ko/ability"
import { Moves } from "#app/enums/moves"
import { Type } from "#app/data/type"
import { allMoves } from "#app/data/move.js";
import PokemonSpecies, { starterPassiveAbilities, initSpecies, allSpecies, PokemonSpeciesForm, PokemonForm, SpeciesFormKey } from "#app/data/pokemon-species.js";
import * as fs from "fs";
import * as path from 'path';
import { speciesEggMoves } from "#app/data/egg-moves"
import { Species } from "#app/enums/species.js";
import i18next, { init } from "i18next";
import { pokemonSpeciesLevelMoves } from "#app/data/pokemon-level-moves.js";
import { biomePokemonPools, getBiomeName, initBiomes, pokemonBiomes } from "#app/data/biomes.js";
import { Biome } from "#app/enums/biome.js";
import { exit } from "process";
import { Ability } from "#app/data/ability.js";

const filePath = path.join(__dirname, 'pokemon-imgId.txt');
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


  const toEvolutionNames = (x) => {
    var ret = []
    for (var z in x) {
      var y = x[z]
      ret.push(i18next.t(`pokemon:${Species[y[0]].toLowerCase()}`), y[1])
    }
    return ret
  }

  const print = (((x: PokemonForm, sp: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes) => {
    var ret = []
    // id
    ret.push(x.speciesId)
    //name
    ret.push(sp.name + '-' + x.formName)
    // ret.push(i18next.t(`pokemonForm:${x.formName}`))
    // ret.push(sp.name + " " + )
    // type
    ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
    ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
    // ability (todo: passive)
    ret.push(new Ability(x.ability1, x.generation).name)
    ret.push(new Ability(x.ability2, x.generation).name)
    ret.push(new Ability(x.abilityHidden, x.generation).name)
    ret.push(new Ability(passive, x.generation).name)
    // metadata
    ret.push(x.generation)
    ret.push(sp.legendary)
    ret.push(sp.subLegendary)
    ret.push(sp.mythical)
    // evolution (speciesId, level)
    ret.push(toEvolutionNames(sp.getEvolutionLevels()))
    // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
    ret.push(x.baseTotal)
    ret.push(x.baseStats)
    ret.push(x.height)
    ret.push(x.weight)
    // form
    ret.push(sp.canChangeForm)
    // eggMoves
    var tmp = []
    for (let i = 0; i < eggMoves.length; i++) {
      tmp.push(eggMoves[i].name)
    }
    ret.push(tmp)
    // moves
    tmp = []
    for (let i = 0; i < moves.length; i++) {
      tmp.push(moves[i][1].name, moves[i][0])
    }
    ret.push(tmp)


    tmp = []
    for (let i = 0; i < biomes.length; i++) {
      tmp.push(getBiomeName(biomes[i]))
    }
    ret.push(tmp)

    console.log(ret.join(" / "))
  }));



  const print2 = (((x: PokemonSpecies, pokemonId: integer, passive: Abilities, eggMoves, moves, biomes
  ) => {

    var ret = []
    // id
    ret.push(x.speciesId)
    //name
    ret.push(x.name)
    // type
    ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type1]}`))
    ret.push(i18next.t(`pokemonInfo:Type.${Type[x.type2]}`))
    // ability (todo: passive)
    ret.push(new Ability(x.ability1, x.generation).name)
    ret.push(new Ability(x.ability2, x.generation).name)
    ret.push(new Ability(x.abilityHidden, x.generation).name)
    ret.push(new Ability(passive, x.generation).name)
    // metadata
    ret.push(x.generation)
    ret.push(x.legendary)
    ret.push(x.subLegendary)
    ret.push(x.mythical)
    // evolution (speciesId, level)
    ret.push(toEvolutionNames(x.getEvolutionLevels()))
    // stats (baseHp, baseAtk, baseDef, baseSpatk, baseSpdef, baseSpd)
    ret.push(x.baseTotal)
    ret.push(x.baseStats)
    ret.push(x.height)
    ret.push(x.weight)
    // form
    ret.push(x.canChangeForm)
    // eggMoves
    var tmp = []
    for (let i = 0; i < eggMoves.length; i++) {
      tmp.push(eggMoves[i].name)
    }
    ret.push(tmp)
    // moves
    tmp = []
    for (let i = 0; i < moves.length; i++) {
      tmp.push(moves[i][1].name, moves[i][0])
    }
    ret.push(tmp)


    tmp = []
    for (let i = 0; i < biomes.length; i++) {
      tmp.push(getBiomeName(biomes[i]))
    }

    ret.push(tmp)

    console.log(ret.join(" / "))
  }));


  const printMetaData = () => {
    console.log(
      `speciesId
    name
    type1
    type2
    ability1
    ability2
    abilityHidden
    abilitypassive
    generation
    legendary
    subLegendary
    mythical
    getEvolutionLevels()
    x.baseTotal
    x.baseStats
    x.height
    x.weight
    canChangeForm
    eggMoves
    moves
    biomes
    `)
  }

  it("테스트 환경입니다 >_< ", async () => {

    let p = allSpecies;
    var pokemonId: integer = 1

    printMetaData()

    // initBiomes();
    const biomeMap = new Map<Species, Biome[]>();
    for (let i = 0; i < pokemonBiomes.length; i++) {
      var species = Species[pokemonBiomes[i][0]]
      var biomeEntries = pokemonBiomes[i][3]
      biomeMap.set(species, biomeEntries
        .map((a, b, c) => a[0])
        .filter((value, index, self) => self.indexOf(value) === index))
    }

    for (let i = 0; i < allSpecies.length; i++) {
      let x = allSpecies[i]

      var passive = starterPassiveAbilities[x.getRootSpeciesId()]
      var eggMoves: Moves[] = speciesEggMoves[x.getRootSpeciesId()].map((value, index, arr) => allMoves[value])
      var moves = pokemonSpeciesLevelMoves[x.getRootSpeciesId()].map((value, index, arr) => [value[0], allMoves[value[1]]])

    i18next.changeLanguage("en")
    console.log(i18next.t(`pokemon:${Species[x.speciesId].toLowerCase()}`))
    i18next.changeLanguage("ko")
    }

  });
});
