package poke.rogue.helper.data.cache

interface ImageCacher {
    suspend fun cacheImages(urls: List<String>)

    suspend fun clear()
}
