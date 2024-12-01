package poke.rogue.helper.presentation.util

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.SpannedString
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
class SpannableStringExtensionsKtTest {
    private lateinit var mockDrawable: Drawable

    @Before
    fun setUp() {
        mockDrawable = mockk(relaxed = true)
    }

    @Test
    fun `SpannableString에 아이콘이 버티컬 라인 위치에 정확히 추가된다`() {
        val delimiter = "|"
        val fullText = "Fire is $delimiter strong against Grass"
        val spannable = SpannableString(fullText)

        val iconSize = 50

        spannable.drawable(iconDrawable = mockDrawable, iconSize = iconSize, delimiter = delimiter)

        val spans = spannable.getSpans(0, spannable.length, ImageSpan::class.java)
        spans.size shouldBe 1

        val imageSpan = spans[0]
        val start = spannable.getSpanStart(imageSpan)
        val end = spannable.getSpanEnd(imageSpan)

        fullText.indexOf("|") shouldBe start
        fullText.indexOf("|") + 1 shouldBe end
    }

    @Test
    fun `SpannableString에 컬러 스팬이 특정 단어 위치에 정확히 적용된다`() {
        val fullText = "Fire is strong against Grass"
        val targetWord = "Grass"
        val color = 0xFFFF0000.toInt() // Red color

        val spannable = SpannableString(fullText)
        spannable.color(targetWord, color)

        val spans = spannable.getSpans(0, spannable.length, ForegroundColorSpan::class.java)
        spans.size shouldBe 1

        val colorSpan = spans[0]
        val start = spannable.getSpanStart(colorSpan)
        val end = spannable.getSpanEnd(colorSpan)

        fullText.indexOf(targetWord) shouldBe start
        fullText.indexOf(targetWord) + targetWord.length shouldBe end
    }

    @Test
    fun `SpannableString에 폰트 스타일 스팬이 특정 단어 위치에 정확히 적용된다`() {
        val fullText = "Fire is strong against Grass"
        val targetWord = "Fire"

        val spannable = SpannableString(fullText)
        spannable.style(targetWord = targetWord, styleSpan = StyleSpan(Typeface.BOLD))

        val spans = spannable.getSpans(0, spannable.length, StyleSpan::class.java)
        spans.size shouldBe 1

        val styleSpan = spans[0]
        val start = spannable.getSpanStart(styleSpan)
        val end = spannable.getSpanEnd(styleSpan)

        fullText.indexOf(targetWord) shouldBe start
        fullText.indexOf(targetWord) + targetWord.length shouldBe end

        styleSpan.style shouldBe Typeface.BOLD
    }

    @Test
    fun `SpannedString에 아이콘이 버티컬 라인 위치에 정확히 추가된다`() {
        val delimiter = "|"
        val fullText = "Fire is $delimiter strong against Grass"
        val spannedString = SpannedString(fullText)

        val iconSize = 50
        val updatedSpanned =
            spannedString.drawable(
                iconDrawable = mockDrawable,
                iconSize = iconSize,
                delimiter = delimiter,
            )

        val spans = updatedSpanned.getSpans(0, updatedSpanned.length, ImageSpan::class.java)
        spans.size shouldBe 1

        val imageSpan = spans[0]
        val start = updatedSpanned.getSpanStart(imageSpan)
        val end = updatedSpanned.getSpanEnd(imageSpan)

        fullText.indexOf("|") shouldBe start
        fullText.indexOf("|") + 1 shouldBe end
    }

    @Test
    fun `SpannedString에 컬러 스팬이 특정 단어 위치에 정확히 적용된다`() {
        val fullText = "Fire is strong against Grass"
        val targetWord = "Grass"
        val color = 0xFFFF0000.toInt() // Red color

        val spannedString = SpannedString(fullText)
        val updatedSpanned = spannedString.color(targetWord, color)

        val spans =
            updatedSpanned.getSpans(0, updatedSpanned.length, ForegroundColorSpan::class.java)
        spans.size shouldBe 1

        val colorSpan = spans[0]
        val start = updatedSpanned.getSpanStart(colorSpan)
        val end = updatedSpanned.getSpanEnd(colorSpan)

        fullText.indexOf(targetWord) shouldBe start
        fullText.indexOf(targetWord) + targetWord.length shouldBe end
    }

    @Test
    fun `SpannedString에 폰트 스타일 스팬이 특정 단어 위치에 정확히 적용된다`() {
        val fullText = "Fire is strong against Grass"
        val targetWord = "Fire"

        val spannedString = SpannedString(fullText)
        val updatedSpanned =
            spannedString.style(
                targetWord = targetWord,
                styleSpan = StyleSpan(Typeface.BOLD),
            )

        val spans = updatedSpanned.getSpans(0, updatedSpanned.length, StyleSpan::class.java)
        spans.size shouldBe 1

        val styleSpan = spans[0]
        val start = updatedSpanned.getSpanStart(styleSpan)
        val end = updatedSpanned.getSpanEnd(styleSpan)

        fullText.indexOf(targetWord) shouldBe start
        fullText.indexOf(targetWord) + targetWord.length shouldBe end

        styleSpan.style shouldBe Typeface.BOLD
    }
}
