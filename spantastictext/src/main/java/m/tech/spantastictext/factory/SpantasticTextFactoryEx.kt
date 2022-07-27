package m.tech.spantastictext.factory

import m.tech.spantastictext.model.SpantasticText

inline fun spantasticText(
    text: String,
    crossinline block: SpantasticTextFactory.() -> Unit
): SpantasticText {
    return SpantasticTextFactory(text).apply(block).create()
}