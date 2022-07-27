package m.tech.spantastictext.cache

import m.tech.spantastictext.model.SpantasticTextItem

internal object SpantasticTextCache {

    private val cache = mutableMapOf<Any, List<SpantasticTextItem>>()

    fun getOrPut(key: Any, defaultValue: () -> List<SpantasticTextItem>): List<SpantasticTextItem> {
        return cache.getOrPut(key, defaultValue)
    }

    fun clear() {
        cache.clear()
    }

}