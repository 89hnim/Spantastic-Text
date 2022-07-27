package m.tech.spantastictext.span.pattern

import androidx.core.util.PatternsCompat
import m.tech.spantastictext.model.SpantasticTextItem

sealed class SpantasticTextPattern {

    internal abstract val pattern: String

    private val _regex: Regex
        get() = pattern.toRegex()

    internal fun getSpantasticItems(input: CharSequence): List<SpantasticTextItem> {
        return _regex.findAll(input).map {
            val value = it.value
            val start = it.range.first
            val end = start + value.length
            SpantasticTextItem(value, start, end)
        }.toList()
    }

    object Email : SpantasticTextPattern() {
        override val pattern: String
            get() = PatternsCompat.EMAIL_ADDRESS.pattern()
    }

    object Hashtag : SpantasticTextPattern() {
        override val pattern: String
            get() = "#([A-Za-z0-9_-]+)"
    }

    data class Custom(override val pattern: String) : SpantasticTextPattern()

}