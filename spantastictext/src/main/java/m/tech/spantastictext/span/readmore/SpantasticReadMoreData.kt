package m.tech.spantastictext.span.readmore

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.core.text.toSpannable

class SpantasticReadMoreData(
    private val readMoreCharacterLimit: Int = DEFAULT_READ_MORE_CHARACTER_LIMIT,
    private val readMoreLinesLimit: Int = DEFAULT_READ_MORE_LINES_LIMIT,
    @ColorInt private val readMoreTextColor: Int = Color.GRAY,
    private val readMoreText: String = DEFAULT_READ_MORE_TEXT
) {

    internal fun getShortSpannable(
        fullText: Spannable,
    ): Spanned {
        return when {
            readMoreCharacterLimit == DEFAULT_READ_MORE_CHARACTER_LIMIT -> {
                fullText
            }
            readMoreCharacterLimit >= fullText.length -> {
                // in my case I will append read more if there are more than readMoreLinesLimit break lines
                if (fullText.count { it.toString() == BREAK_LINE } > readMoreLinesLimit) {
                    //case 2.1: append read more at fourth line break
                    var linesBreakFound = 0
                    val indexOfFourthBreakLine = fullText.indexOfFirst {
                        if (it.toString() == BREAK_LINE) {
                            linesBreakFound++
                        }
                        linesBreakFound == 4
                    }
                    if (indexOfFourthBreakLine > 0) {
                        fullText.subSequence(0, indexOfFourthBreakLine).toSpannable()
                            .appendReadMore()
                    } else {
                        //if can't find the fourth break line for some reasons (should never occur)
                        fullText
                    }
                } else {
                    //case 2.2
                    fullText
                }
            }
            else -> {
                fullText.subSequence(0, readMoreCharacterLimit).toSpannable().appendReadMore()
            }
        }
    }

    private fun Spannable.appendReadMore(): Spannable {
        val spanStringBuilder = SpannableStringBuilder(this)
        spanStringBuilder.append(SPACE)
        spanStringBuilder.append(readMoreText)
        spanStringBuilder.setSpan(
            ForegroundColorSpan(readMoreTextColor),
            spanStringBuilder.length - readMoreText.length - SPACE.length,
            spanStringBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spanStringBuilder
    }

    companion object {
        private const val DEFAULT_READ_MORE_CHARACTER_LIMIT = -1
        private const val DEFAULT_READ_MORE_LINES_LIMIT = 4
        private const val DEFAULT_READ_MORE_TEXT = "read more..."
        private const val SPACE = " "
        private const val BREAK_LINE = "\n"
    }
}