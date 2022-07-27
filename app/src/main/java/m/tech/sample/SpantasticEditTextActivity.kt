package m.tech.sample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SpantasticEditTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spantastic_edit_text)

        val editText = findViewById<EditText>(R.id.edt_test)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("DSK", "onTextChanged: $s $start $before $count")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}