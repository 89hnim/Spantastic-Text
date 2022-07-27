package m.tech.spantastictext.span.clickable

import android.text.TextPaint
import android.view.View
import m.tech.spantastictext.model.SpantasticTextItem

interface SpantasticTextOnSpanClickedListener {
    fun onClick(widget: View, item: SpantasticTextItem)

    fun updateDrawState(ds: TextPaint) {}

}