package poke.rogue.helper.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import poke.rogue.helper.data.datasource.LocalBattleDataSource
import poke.rogue.helper.data.datasource.LocalDexDataSource
import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.datasource.LocalVersionDataSource
import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.datasource.RemoteBattleDataSource
import poke.rogue.helper.data.datasource.RemoteBiomeDataSource
import poke.rogue.helper.data.datasource.RemoteDexDataSource
import poke.rogue.helper.data.datasource.RemoteVersionDataSource

internal val dataSourceModule
    get() =
        module {
            singleOf(::LocalBattleDataSource)
            singleOf(::LocalDexDataSource)
            singleOf(::LocalTypeDataSource)
            singleOf(::LocalVersionDataSource)

            singleOf(::RemoteBattleDataSource)
            singleOf(::RemoteAbilityDataSource)
            singleOf(::RemoteDexDataSource)
            singleOf(::RemoteBiomeDataSource)
            singleOf(::RemoteVersionDataSource)
        }
