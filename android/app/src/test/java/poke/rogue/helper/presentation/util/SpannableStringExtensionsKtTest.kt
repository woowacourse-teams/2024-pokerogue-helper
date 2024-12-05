package poke.rogue.helper.presentation.util

import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import androidx.core.text.buildSpannedString
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
    fun `SpannableStringBuilder 에 아이콘이 구분자 위치에 추가된다`() {
        // given
        val delimiter = "|"
        val fullText = "Fire is $delimiter strong against Grass"
        val iconBound = Rect(0, 0, 50, 50)

        // when
        val spannedString =
            buildSpannedString(fullText) {
                addIcon(
                    fullText = fullText,
                    targetDelimiter = delimiter,
                    iconDrawable = mockDrawable,
                    bounds = iconBound,
                )
            }

        // then
        val spans = spannedString.getSpans(0, spannedString.length, ImageSpan::class.java)
        spans.size shouldBe 1

        val imageSpan = spans[0]
        val iconStart = spannedString.getSpanStart(imageSpan)
        val iconENd = spannedString.getSpanEnd(imageSpan)

        iconStart shouldBe 8
        iconENd shouldBe 9
    }

    @Test
    fun `SpannableStringBuilder 에 컬러가 특정 단어에 적용된다`() {
        // given
        val fullText = "Fire is strong"
        val targetWord = "Fire"
        val color = 0xFFFF0000.toInt() // Red color

        val spannedString =
            buildSpannedString(fullText) {
                applyColor(
                    fullText = fullText,
                    target = targetWord,
                    color = color,
                )
            }

        val spans = spannedString.getSpans(0, spannedString.length, ForegroundColorSpan::class.java)
        spans.size shouldBe 1

        val colorSpan = spans[0]
        val colorStart = spannedString.getSpanStart(colorSpan)
        val endStart = spannedString.getSpanEnd(colorSpan)

        colorStart shouldBe 0
        endStart shouldBe 4
    }

    @Test
    fun `SpannableStringBuilder 에 폰트 스타일이 특정 단어에 적용된다`() {
        // given
        val fullText = "Fire is strong"
        val targetWord = "Fire"

        // when
        val spannedString =
            buildSpannedString(fullText) {
                applyFontStyle(
                    fullText = fullText,
                    target = targetWord,
                    styleSpan = StyleSpan(Typeface.BOLD),
                )
            }

        // then
        val spans = spannedString.getSpans(0, spannedString.length, StyleSpan::class.java)
        spans.size shouldBe 1

        val styleSpan = spans[0]
        val styleStart = spannedString.getSpanStart(styleSpan)
        val styleEnd = spannedString.getSpanEnd(styleSpan)

        styleStart shouldBe 0
        styleEnd shouldBe 4
        styleSpan.style shouldBe Typeface.BOLD
    }
}
