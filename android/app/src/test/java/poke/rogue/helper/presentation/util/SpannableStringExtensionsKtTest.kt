package poke.rogue.helper.presentation.util

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import poke.rogue.helper.testing.TestApplication

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class SpannableExtensionsTest {

    private lateinit var mockDrawable: Drawable

    @Before
    fun setUp() {
        mockDrawable = mockk(relaxed = true)
    }

    @Test
    fun `아이콘이 버티컬 라인 위치에 drawable 스팬이 정확히 추가된다`() {
        val fullText = "Fire is | strong against Grass"
        val spannable = SpannableString(fullText)

        val iconSize = 50

        spannable.drawable(fullText, mockDrawable, iconSize)

        // getImageSpans only
        val spans = spannable.getSpans(0, spannable.length, ImageSpan::class.java)
        val imageSpan = spans[0] ?: error("ImageSpan not found")

        val start = spannable.getSpanStart(imageSpan)
        val end = spannable.getSpanEnd(imageSpan)

        fullText.indexOf("|") shouldBe start
        fullText.indexOf("|") + 1 shouldBe end
    }

    @Test
    fun `컬러 스팬이 strong 위치에 적용된다`() {
        val fullText = "Fire is strong against Grass"
        val targetWord = "Grass"
        val color = 0xFFFF0000.toInt() // Red color

        val spannable = SpannableString(fullText)
        spannable.color(targetWord, color, fullText)

        // getColoredSpan Only
        val spans = spannable.getSpans(0, spannable.length, ForegroundColorSpan::class.java)
        val colorSpan = spans[0] ?: error("ColorSpan not found")

        val start = spannable.getSpanStart(colorSpan)
        val end = spannable.getSpanEnd(colorSpan)

        fullText.indexOf(targetWord) shouldBe start
        fullText.indexOf(targetWord) + targetWord.length shouldBe end
    }

    @Test
    fun `폰트 스타일 스팬이 Fire 위치에 적용된다`() {
        val fullText = "Fire is strong against Grass"
        val targetWord = "Fire"

        val spannable = SpannableString(fullText)
        spannable.style(targetWord, fullText)

        // getStyleSpan Only
        val spans = spannable.getSpans(0, spannable.length, StyleSpan::class.java)
        val styleSpan = spans[0] ?: error("StyleSpan not found")

        val start = spannable.getSpanStart(styleSpan)
        val end = spannable.getSpanEnd(styleSpan)

        fullText.indexOf(targetWord) shouldBe start
        fullText.indexOf(targetWord) + targetWord.length shouldBe end

        styleSpan.style shouldBe Typeface.BOLD
    }
}
