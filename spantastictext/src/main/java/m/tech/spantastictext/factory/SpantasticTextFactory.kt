package m.tech.spantastictext.factory

import androidx.core.text.toSpannable
import m.tech.spantastictext.cache.SpantasticTextCache
import m.tech.spantastictext.span.readmore.SpantasticReadMoreData
import m.tech.spantastictext.model.SpantasticText
import m.tech.spantastictext.option.SpantasticTextOptions
import m.tech.spantastictext.utils.safeSpan

class SpantasticTextFactory(private val text: String) {

    private val options = mutableListOf<SpantasticTextOptions>()
    private var readMoreData: SpantasticReadMoreData? = null
    private var enableCache = false

    fun addOption(block: SpantasticTextOptions.Builder.() -> Unit) = apply {
        options.add(
            SpantasticTextOptions.Builder().apply { block() }.build()
        )
    }

    fun setReadMore(readMoreData: SpantasticReadMoreData) = apply {
        this.readMoreData = readMoreData
    }

    fun enableCache(isEnable: Boolean) = apply {
        this.enableCache = isEnable
    }

    fun create(): SpantasticText {
        val spanText = text.toSpannable()
        options.forEach { option ->
            val items = if (enableCache) {
                SpantasticTextCache.getOrPut(text to option) {
                    option.pattern?.getSpantasticItems(text) ?: option.items
                }
            } else {
                option.pattern?.getSpantasticItems(text) ?: option.items
            }

            items.forEach { item ->
                spanText.safeSpan(option.spanStyle, item)
            }
        }

        val shortSpan = readMoreData?.getShortSpannable(spanText) ?: spanText

        return SpantasticText(
            text = text,
            span = spanText,
            shortSpan = shortSpan
        )
    }

}