package poke.rogue.helper.data.di

import org.koin.dsl.module
import poke.rogue.helper.analytics.di.analyticsModule
import poke.rogue.helper.local.di.localModule
import poke.rogue.helper.remote.di.remoteModule

val dataModule
    get() =
        module {
            includes(
                localModule,
                remoteModule,
                dataSourceModule,
                repositoryModule,
                analyticsModule,
                cacheModule,
            )
        }
