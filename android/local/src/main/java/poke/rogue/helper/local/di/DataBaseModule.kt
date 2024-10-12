package poke.rogue.helper.local.di

import androidx.room.Room
import org.koin.dsl.module
import poke.rogue.helper.local.db.PokeRogueDatabase

internal val dataBaseModule = module {
    single<PokeRogueDatabase> {
        Room.databaseBuilder(
            get(),
            PokeRogueDatabase::class.java,
            PokeRogueDatabase.DATABASE_NAME
        ).build()
    }
}