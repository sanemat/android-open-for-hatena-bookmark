package jp.sane.openforhatenabookmark

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class HatebuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hatebu)
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val url = intent.data ?: return
                Toast.makeText(applicationContext, url.toString(), Toast.LENGTH_SHORT).show()
            }
            else -> return
        }
    }
}
