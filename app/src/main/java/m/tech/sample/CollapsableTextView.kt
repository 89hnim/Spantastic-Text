package m.tech.sample

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.transition.TransitionManager
import m.tech.spantastictext.model.SpantasticText
import m.tech.spantastictext.span.clickable.SpantasticTextLinkMovementMethod

/**
 * @author 89hnim
 * @since 25/07/2021
 * Collapsable Text like see more text in feeds Facebook
 */
internal class CollapsableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var isFullTextShow = false
    private var fullText: Spanned = SpannableString("")
    private var shortText: Spanned = SpannableString("")

    init {
        movementMethod = SpantasticTextLinkMovementMethod()
        highlightColor = Color.TRANSPARENT
    }

    fun setText(
        spantasticText: SpantasticText,
        isFullTextShow: Boolean
    ) {
        this.isFullTextShow = isFullTextShow
        this.fullText = spantasticText.span
        this.shortText = spantasticText.shortSpan

        //text can't collapse, always show fulltext
        if (!isCollapsable()) {
            this.isFullTextShow = true
        }

        val textSpan = if (isFullTextShow) {
            fullText
        } else {
            shortText
        }

        setText(textSpan, BufferType.SPANNABLE)
    }

    fun toggleCollapse(useAnimation: Boolean = false) {
        if (isCollapsable()) {
            if (useAnimation) {
                val parentLayout = parent as? ViewGroup ?: return
                TransitionManager.beginDelayedTransition(parentLayout)
            }

            val text = if (isFullTextShow) {
                shortText
            } else {
                fullText
            }

            setText(text, BufferType.SPANNABLE)
            scrollTo(0, 0)

            isFullTextShow = !isFullTextShow
        }
    }

    private fun isCollapsable() = shortText != fullText

    //clear running animation
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        val parentLayout = parent as? ViewGroup ?: return
        TransitionManager.endTransitions(parentLayout)
    }

}