package m.tech.spantastictext.span.clickable

import android.text.Layout
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

/**
 * handle properly clickable span for textview
 * https://stackoverflow.com/a/34698757/14391924
 */
class SpantasticTextLinkMovementMethod : LinkMovementMethod() {

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout: Layout = widget.layout
            val line: Int = layout.getLineForVertical(y)
            val off: Int = layout.getOffsetForHorizontal(line, x.toFloat())
            val candidates = buffer.getSpans(off, off, CharacterStyle::class.java)
            var clickableSpan: ClickableSpan? = null
            for (characterStyle in candidates) {
                if (characterStyle.underlying is ClickableSpan) {
                    clickableSpan = characterStyle.underlying as ClickableSpan
                    break
                }
            }
            if (clickableSpan != null) {
                if (action == MotionEvent.ACTION_UP) {
                    clickableSpan.onClick(widget)
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(
                        buffer,
                        buffer.getSpanStart(clickableSpan),
                        buffer.getSpanEnd(clickableSpan)
                    )
                }
                return true
            } else {
                Selection.removeSelection(buffer)
            }
        }

        return super.onTouchEvent(widget, buffer, event)
    }

}