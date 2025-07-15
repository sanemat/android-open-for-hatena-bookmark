package jp.sane.openforhatebu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import mozilla.components.support.utils.WebURLFinder
import org.jsoup.Jsoup
import java.net.URI
import java.net.URL

import jp.sane.openforhatebu.databinding.ActivityHatebuBinding

class HatebuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHatebuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHatebuBinding.inflate(layoutInflater)
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val uri = intent.data ?: return
                val urlString = uri.toString()
                openHatenabookmarkEntry(urlString)
            }
            Intent.ACTION_SEND -> {
                val dataString = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (TextUtils.isEmpty(dataString)) {
                    return
                }
                val bestUrl = WebURLFinder(dataString).bestWebURL() ?: return
                openHatenabookmarkEntry(bestUrl)
            }
            else -> return
        }
    }

    private fun openHatenabookmarkEntry(urlString: String) {
        lifecycleScope.launch {
            val targetUri = getTargetUri(URI(urlString))
            val entryUri = getEntryUri(targetUri)
            withContext(Dispatchers.Main) {
                binding.openingURI.text = entryUri.toString()
            }
            CustomTabsIntent.Builder().build().apply {
                launchUrl(this@HatebuActivity, Uri.parse(entryUri.toString()))
            }
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
            if (links[i].hasAttr("rel") && links[i].attr("rel").lowercase() == "canonical") {
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
