package m.tech.spantastictext.span.style

inline fun spanStyle(
    crossinline block: SpantasticTextSpanStyle.Builder.() -> Unit
): SpantasticTextSpanStyle {
    return SpantasticTextSpanStyle.Builder().apply(block).build()
}