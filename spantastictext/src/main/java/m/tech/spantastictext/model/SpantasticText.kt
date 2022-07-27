package m.tech.spantastictext.model

import android.text.Spanned

data class SpantasticText(
    val text: String,
    val span: Spanned,
    val shortSpan: Spanned
)