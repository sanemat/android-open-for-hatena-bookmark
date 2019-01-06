package jp.sane.openforhatenabookmark

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.runBlocking
import java.net.URI

class HatebuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hatebu)
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val uri = intent.data ?: return
                runBlocking {
                    val canonicalUri = getCanonicalUri(URI(uri.toString()))
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(applicationContext, Uri.parse(getEntryUri(canonicalUri).toString()))
                    }
                }
            }
            Intent.ACTION_SEND -> {
                val dataString = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (TextUtils.isEmpty(dataString)) {
                    return
                }
                runBlocking {
                    val canonicalUri = getCanonicalUri(URI(dataString))
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(applicationContext, Uri.parse(getEntryUri(canonicalUri).toString()))
                    }
                }
            }
            else -> return
        }
    }
}

suspend fun getCanonicalUri(uri: URI): URI {
    return uri
}

/**
 * @see http://b.hatena.ne.jp/help/entry/api
 */
suspend fun getEntryUri(uri: URI): URI {
    return URI("http://b.hatena.ne.jp/entry/" + uri.toString().replace("#", "%23"))
}
