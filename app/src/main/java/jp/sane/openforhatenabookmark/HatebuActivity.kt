package jp.sane.openforhatenabookmark

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class HatebuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hatebu)
        if (!Intent.ACTION_VIEW.equals(intent.action)) {
            return
        }
        val url = intent.data ?: return
    }
}
