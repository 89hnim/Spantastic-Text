package m.tech.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun textView(view: View) {
        startActivity(Intent(this, SpantasticTextViewActivity::class.java))
    }

    fun editText(view: View) {
        startActivity(Intent(this, SpantasticEditTextActivity::class.java))
    }

}