package m.tech.spantastictext.span.style

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import m.tech.spantastictext.model.SpantasticTextItem
import m.tech.spantastictext.span.clickable.SpantasticTextOnSpanClickedListener

class SpantasticTextSpanStyle private constructor(
    private val bold: Boolean,
    private val underline: Boolean,
    private val strikeThrough: Boolean,
    private val foregroundColor: Int?,
    private val backgroundColor: Int?,
    private val clickSpanListener: SpantasticTextOnSpanClickedListener? = null,
    private val customSpanStyles: List<CharacterStyle>
) {

    fun createSpans(item: SpantasticTextItem): MutableList<CharacterStyle> {
        val spans = mutableListOf<CharacterStyle>()
        if (clickSpanListener != null) {
            spans.add(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    clickSpanListener.onClick(widget, item)
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                    clickSpanListener.updateDrawState(ds)
                }

            })
        }
        if (bold) {
            spans.add(StyleSpan(Typeface.BOLD))
        }
        if (underline) {
            spans.add(UnderlineSpan())
        }
        if (strikeThrough) {
            spans.add(StrikethroughSpan())
        }
        if (foregroundColor != null) {
            spans.add(ForegroundColorSpan(foregroundColor))
        }
        if (backgroundColor != null) {
            spans.add(BackgroundColorSpan(backgroundColor))
        }
        customSpanStyles.forEach {
            // must create new object of character style
            // https://stackoverflow.com/a/68851329/14391924
            spans.add(CharacterStyle.wrap(it))
        }
        return spans
    }

    class Builder {
        private var bold: Boolean = false
        private var underline: Boolean = false
        private var strikeThrough: Boolean = false
        private var foregroundColor: Int? = null
        private var backgroundColor: Int? = null
        private var customSpanStyles = mutableListOf<CharacterStyle>()
        private var clickSpanListener: SpantasticTextOnSpanClickedListener? = null

        fun bold() = apply {
            this.bold = true
        }

        fun underline() = apply {
            this.underline = true
        }

        fun strikeThrough() = apply {
            this.strikeThrough = true
        }

        fun foregroundColor(@ColorInt foregroundColor: Int) = apply {
            this.foregroundColor = foregroundColor
        }

        fun backgroundColor(@ColorInt backgroundColor: Int) = apply {
            this.backgroundColor = backgroundColor
        }

        fun customSpanStyle(span: CharacterStyle) = apply {
            customSpanStyles.add(span)
        }

        fun clickable(listener: SpantasticTextOnSpanClickedListener) = apply {
            this.clickSpanListener = listener
        }

        fun build(): SpantasticTextSpanStyle {
            return SpantasticTextSpanStyle(
                bold,
                underline,
                strikeThrough,
                foregroundColor,
                backgroundColor,
                clickSpanListener,
                customSpanStyles
            )
        }

    }

}