package poke.rogue.helper.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import poke.rogue.helper.data.cache.GlideImageCacher
import poke.rogue.helper.data.cache.ImageCacher

internal val cacheModule
    get() = module {
        singleOf(::GlideImageCacher).bind<ImageCacher>()
    }