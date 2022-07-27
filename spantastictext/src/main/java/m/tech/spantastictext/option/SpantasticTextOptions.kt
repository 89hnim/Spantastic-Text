package m.tech.spantastictext.option

import m.tech.spantastictext.model.SpantasticTextItem
import m.tech.spantastictext.span.pattern.SpantasticTextPattern
import m.tech.spantastictext.span.style.SpantasticTextSpanStyle

class SpantasticTextOptions private constructor(
    val items: List<SpantasticTextItem> = listOf(),
    val pattern: SpantasticTextPattern? = null,
    val spanStyle: SpantasticTextSpanStyle? = null
) {

    /**
     * override equals for caching. The reason I ignore spanStyle b/c:
     * 1. Not reality
     * 2. Hard to compare List<CharacterStyle> in [SpantasticTextSpanStyle]
     * 3. <!> The purpose of our caching is find all [SpantasticTextItem] matched pattern, not their span styles
     */
    override fun equals(other: Any?): Boolean {
        return if (other !is SpantasticTextOptions) false
        else items == other.items
                && pattern == other.pattern
    }

    override fun hashCode(): Int {
        return items.hashCode() + pattern.hashCode()
    }

    class Builder {
        private val items: MutableList<SpantasticTextItem> = mutableListOf()
        private var pattern: SpantasticTextPattern? = null
        private var spanStyle: SpantasticTextSpanStyle? = null

        fun items(items: List<SpantasticTextItem>) = apply {
            this.items.clear()
            this.items.addAll(items)
        }

        fun pattern(pattern: SpantasticTextPattern) = apply {
            this.pattern = pattern
        }

        fun spanStyle(spanStyle: SpantasticTextSpanStyle) = apply {
            this.spanStyle = spanStyle
        }

        fun build(): SpantasticTextOptions {
            return SpantasticTextOptions(items, pattern, spanStyle)
        }
    }

}
