package jp.sane.openforhatenabookmark

import android.net.Uri
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import java.net.URI

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HatebuUnitTest {
    @Test
    fun example_com() {
        assertEquals(URI("http://b.hatena.ne.jp/entry/https://example.com"), runBlocking { getEntryUri(URI("https://example.com")) })
    }
}
