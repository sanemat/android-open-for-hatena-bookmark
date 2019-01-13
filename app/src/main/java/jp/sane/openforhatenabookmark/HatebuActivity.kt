package jp.sane.openforhatenabookmark

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.android.synthetic.main.activity_hatebu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mozilla.components.support.utils.WebURLFinder
import org.jsoup.Jsoup
import java.net.URI
import java.net.URL

class HatebuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hatebu)
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val uri = intent.data ?: return
                GlobalScope.launch {
                    val targetUri = getTargetUri(URI(uri.toString()))
                    val entryUri = getEntryUri(targetUri)
                    withContext(Dispatchers.Main) {
                        openingURI.text = entryUri.toString()
                    }
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(this@HatebuActivity, Uri.parse(entryUri.toString()))
                    }

                }
            }
            Intent.ACTION_SEND -> {
                val dataString = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (TextUtils.isEmpty(dataString)) {
                    return
                }
                GlobalScope.launch {
                    val bestUrl = WebURLFinder(dataString).bestWebURL()
                    val targetUri = getTargetUri(URI(bestUrl))
                    val entryUri = getEntryUri(targetUri)
                    withContext(Dispatchers.Main) {
                        openingURI.text = entryUri.toString()
                    }
                    CustomTabsIntent.Builder().build().apply {
                        launchUrl(this@HatebuActivity, Uri.parse(entryUri.toString()))
                    }
                }
            }
            else -> return
        }
    }
}

suspend fun getTargetUri(uri: URI): URI {
    return getCanonicalUri(URL(uri.toString()).readText()) ?: uri
}

suspend fun getCanonicalUri(html: String): URI? {
    Jsoup.parse(html).run {
        val links = getElementsByTag("link")
        for (i in 0 until links.count()) {
            if (links[i].hasAttr("rel") && links[i].attr("rel").toLowerCase() == "canonical") {
                return URI(links[i].attr("href"))
            }
        }
    }
    return null
}

/**
 * @see http://b.hatena.ne.jp/help/entry/api
 */
suspend fun getEntryUri(uri: URI): URI {
    return URI("http://b.hatena.ne.jp/entry/" + uri.toString().replace("#", "%23"))
}
