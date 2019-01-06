package jp.sane.openforhatenabookmark

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URI

class HatebuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hatebu)
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val uri = intent.data ?: return
                GlobalScope.launch {
                    val canonicalUri = getCanonicalUri(URI(uri.toString()))
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(this@HatebuActivity, Uri.parse(getEntryUri(canonicalUri).toString()))
                    }

                }
            }
            Intent.ACTION_SEND -> {
                val dataString = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (TextUtils.isEmpty(dataString)) {
                    return
                }
                GlobalScope.launch {
                    val canonicalUri = getCanonicalUri(URI(dataString))
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(this@HatebuActivity, Uri.parse(getEntryUri(canonicalUri).toString()))
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
