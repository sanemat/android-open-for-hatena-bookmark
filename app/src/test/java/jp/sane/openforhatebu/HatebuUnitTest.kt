package jp.sane.openforhatebu

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.net.URI

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HatebuUnitTest {
    @Test
    fun example_com() = runBlocking {
        assertEquals(URI("http://b.hatena.ne.jp/entry/https://example.com"), getEntryUri(URI("https://example.com")))
    }

    @Test
    fun example_com_hash() = runBlocking {
        assertEquals(URI("http://b.hatena.ne.jp/entry/https://example.com%23foo"), getEntryUri(URI("https://example.com#foo")))
    }

    @Test
    fun canonical_example_com() = runBlocking {
        assertEquals(null, getCanonicalUri(File("src/test/res/example-com.html").readText()))
    }

    @Test
    fun canonical_twitter_com() = runBlocking {
        assertEquals(URI("https://twitter.com/"), getCanonicalUri(File("src/test/res/twitter-com.html").readText()))
    }
}
