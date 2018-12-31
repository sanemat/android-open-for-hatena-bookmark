package jp.sane.openforhatenabookmark

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
            Intent.ACTION_SEND -> {
                val dataString = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (TextUtils.isEmpty(dataString)) {
                    return
                }
                Toast.makeText(applicationContext, Uri.parse(dataString).toString(), Toast.LENGTH_SHORT).show()
            }
            else -> return
        }
    }
}
