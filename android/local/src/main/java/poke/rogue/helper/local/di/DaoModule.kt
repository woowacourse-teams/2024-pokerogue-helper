package poke.rogue.helper.local.di

import org.koin.dsl.module
import poke.rogue.helper.local.dao.PokemonDao
import poke.rogue.helper.local.db.PokeRogueDatabase

internal val daoModule
    get() =
        module {
            single<PokemonDao> { get<PokeRogueDatabase>().pokemonDao() }
        }
