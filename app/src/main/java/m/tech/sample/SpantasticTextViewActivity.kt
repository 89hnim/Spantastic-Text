package m.tech.sample

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import m.tech.spantastictext.factory.spantasticText
import m.tech.spantastictext.model.SpantasticText
import m.tech.spantastictext.model.SpantasticTextItem
import m.tech.spantastictext.span.clickable.SpantasticTextLinkMovementMethod
import m.tech.spantastictext.span.clickable.SpantasticTextOnSpanClickedListener
import m.tech.spantastictext.span.pattern.SpantasticTextPattern
import m.tech.spantastictext.span.readmore.SpantasticReadMoreData
import m.tech.spantastictext.span.style.SpantasticTextSpanStyle

class SpantasticTextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spantastic_text_view)

        val text =
            "This text is clickable <<<\nLorem Ipsum is simply #dummy #text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
        val spantasticText = createSpantasticText(text, true)

        val tv1 = findViewById<TextView>(R.id.text_1)
        tv1.movementMethod = SpantasticTextLinkMovementMethod()
        tv1.highlightColor = Color.TRANSPARENT
        tv1.setText(
            spantasticText.span,
            TextView.BufferType.SPANNABLE
        )

        val tv2 = findViewById<CollapsableTextView>(R.id.text_2)
        tv2.setText(spantasticText, false)
        tv2.setOnClickListener {
            if (tv2.selectionStart == -1 && tv2.selectionEnd == -1) {
                tv2.toggleCollapse(false)
            }
        }
    }

    private fun createSpantasticText(text: String, isEnableCache: Boolean): SpantasticText {
        return spantasticText(text) {
            enableCache(isEnableCache)
            setReadMore(
                SpantasticReadMoreData(
                    readMoreCharacterLimit = 180,
                    readMoreLinesLimit = 4,
                    readMoreTextColor = Color.GRAY,
                    readMoreText = "read more..."
                )
            )
            addOption {
                items(
                    listOf(
                        SpantasticTextItem(
                            "Lorem Ipsum",
                            text.indexOf("Lorem Ipsum"),
                            text.indexOf("Lorem Ipsum") + 11
                        )
                    )
                )
                spanStyle(
                    SpantasticTextSpanStyle.Builder()
                        .bold()
                        .underline()
                        .strikeThrough()
                        .foregroundColor(Color.GRAY)
                        .backgroundColor(Color.CYAN)
                        .build()
                )
            }
            addOption {
                pattern(SpantasticTextPattern.Hashtag)
                spanStyle(
                    SpantasticTextSpanStyle.Builder()
                        .foregroundColor(Color.GREEN)
                        .clickable(object : SpantasticTextOnSpanClickedListener {
                            override fun onClick(widget: View, item: SpantasticTextItem) {
                                Toast.makeText(
                                    this@SpantasticTextViewActivity,
                                    "Clicked $item",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        })
                        .build()
                )
            }
            addOption {
                pattern(SpantasticTextPattern.Custom("\\b(This text is clickable)\\b"))
                spanStyle(
                    SpantasticTextSpanStyle.Builder()
                        .customSpanStyle(StyleSpan(Typeface.BOLD))
                        .foregroundColor(Color.RED)
                        .clickable(object : SpantasticTextOnSpanClickedListener {
                            override fun onClick(widget: View, item: SpantasticTextItem) {
                                Toast.makeText(
                                    this@SpantasticTextViewActivity,
                                    "Clicked span $item",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        })
                        .build()
                )
            }
        }
    }
}