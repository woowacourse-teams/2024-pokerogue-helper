package poke.rogue.helper.data.model

import poke.rogue.helper.local.datastore.SavedPokemonWithSkill

data class PokemonWithSkillIds(val pokemonId: String, val skillId: String)

fun SavedPokemonWithSkill.toData() = PokemonWithSkillIds(pokemonId, skillId)

data class PokemonWithSkill(val pokemon: Pokemon, val skill: BattleSkill)
