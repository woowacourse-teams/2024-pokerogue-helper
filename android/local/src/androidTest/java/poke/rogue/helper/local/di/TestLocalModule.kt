package poke.rogue.helper.local.di

import androidx.room.Room
import org.koin.dsl.module
import poke.rogue.helper.local.db.PokeRogueDatabase
import poke.rogue.helper.local.utils.testContext

val testLocalModule
    get() = module {
        includes(daoModule)

        single<PokeRogueDatabase> {
            Room.inMemoryDatabaseBuilder(
                testContext,
                PokeRogueDatabase::class.java,
            ).build()
        }
    }