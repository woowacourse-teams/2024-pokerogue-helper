package poke.rogue.helper.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import poke.rogue.helper.data.datasource.*

internal val dataSourceModule
    get() = module {
    singleOf(::LocalBattleDataSource)
    singleOf(::LocalDexDataSource)
    singleOf(::LocalTypeDataSource)

    singleOf(::RemoteBattleDataSource)
    singleOf(::RemoteAbilityDataSource)
    singleOf(::RemoteDexDataSource)
    singleOf(::RemoteBiomeDataSource)
}
