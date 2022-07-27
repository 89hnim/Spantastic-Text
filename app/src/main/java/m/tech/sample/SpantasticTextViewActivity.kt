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
            "email TEstne @mention OneMount Volio www.gapowork.vn thu minhvip@gmail.com ^^ MinhTA Gapo In My <3 \n Testing#gapo #onemount #twoast#ha  ooo \n www.gapowork.vn  OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMoun OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMoun OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMoun OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount Volio OneMount  www.volio.vn Voliov OneMount Volio"
        val spantasticText = createSpantasticText(text, true)

        findViewById<TextView>(R.id.text_1).movementMethod = SpantasticTextLinkMovementMethod()
        findViewById<TextView>(R.id.text_1).highlightColor = Color.TRANSPARENT
        findViewById<TextView>(R.id.text_1).setText(
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
                    readMoreCharacterLimit = 180
                )
            )
            addOption {
                items(
                    listOf(
                        SpantasticTextItem(
                            "MinhTA",
                            text.indexOf("MinhTA"),
                            text.indexOf("MinhTA") + 6
                        ),
                        SpantasticTextItem(
                            "Gapo In My <3",
                            text.indexOf("Gapo In My <3"),
                            text.indexOf("Gapo In My <3") + 13
                        )
                    )
                )
                spanStyle(
                    SpantasticTextSpanStyle.Builder()
                        .bold()
                        .underline()
                        .strikeThrough()
                        .foregroundColor(Color.RED)
                        .backgroundColor(Color.YELLOW)
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
                                createSpantasticText(text, true)
                            }

                        })
                        .build()
                )
            }
            addOption {
                pattern(SpantasticTextPattern.Custom("\\b(TEstne)\\b"))
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
                                createSpantasticText(text, false)
                            }

                        })
                        .build()
                )
            }
        }
    }
}