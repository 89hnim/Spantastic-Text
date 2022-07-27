package m.tech.spantastictext.utils

import android.text.Spannable
import m.tech.spantastictext.model.SpantasticTextItem
import m.tech.spantastictext.span.style.SpantasticTextSpanStyle

internal fun Spannable.safeSpan(
    spans: SpantasticTextSpanStyle?,
    item: SpantasticTextItem
): Spannable {
    if (spans == null) return this
    val start = item.start
    val end = item.end
    if (start < 0 || end > length || start >= end) return this
    // must generate spans here
    // b/c if use same CharacterStyle instance, the span will only apply to last item
    // https://stackoverflow.com/a/68851329/14391924
    spans.createSpans(item).forEach { span ->
        setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return this
}