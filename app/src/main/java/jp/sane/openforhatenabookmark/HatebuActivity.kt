package jp.sane.openforhatenabookmark

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.runBlocking

class HatebuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hatebu)
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val uri = intent.data ?: return
                runBlocking {
                    val canonicalUri = getCanonicalUri(uri)
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(applicationContext, canonicalUri)
                    }
                }
            }
            Intent.ACTION_SEND -> {
                val dataString = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (TextUtils.isEmpty(dataString)) {
                    return
                }
                runBlocking {
                    val canonicalUri = getCanonicalUri(Uri.parse(dataString))
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(applicationContext, canonicalUri)
                    }
                }
            }
            else -> return
        }
    }
}

suspend fun getCanonicalUri(uri: Uri): Uri {
    return uri
}
