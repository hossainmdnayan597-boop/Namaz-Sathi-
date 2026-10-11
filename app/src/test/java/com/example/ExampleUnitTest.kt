package com.example

import com.example.data.calendar.DateCalendarHelper
import com.example.data.quiz.IslamicQuizData
import com.example.data.quiz.QuizCategory
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testDateCalendarHelper_BanglaDate() {
    val tz = TimeZone.getTimeZone("Asia/Dhaka")
    val cal = Calendar.getInstance(tz).apply {
      set(2026, Calendar.OCTOBER, 10, 12, 0, 0)
    }
    val bangla = DateCalendarHelper.getBanglaDate(cal)
    assertNotNull(bangla)
    assertTrue("Should contain Ashwina or Ashwin", bangla.monthNameBangla == "আশ্বিন")
    assertEquals(1433, bangla.yearBangla)
    assertTrue(bangla.formattedBangla.contains("বঙ্গাব্দ"))
  }

  @Test
  fun testDateCalendarHelper_EnglishDate() {
    val tz = TimeZone.getTimeZone("Asia/Dhaka")
    val cal = Calendar.getInstance(tz).apply {
      set(2026, Calendar.OCTOBER, 10, 12, 0, 0)
    }
    val eng = DateCalendarHelper.getEnglishDate(cal)
    assertNotNull(eng)
    assertEquals(10, eng.day)
    assertEquals(2026, eng.year)
    assertEquals("Saturday", eng.dayOfWeekEnglish)
    assertEquals("শনিবার", eng.dayOfWeekBangla)
  }

  @Test
  fun testDateCalendarHelper_ThreeCalendars() {
    val tz = TimeZone.getTimeZone("Asia/Dhaka")
    val cal = Calendar.getInstance(tz).apply {
      set(2026, Calendar.OCTOBER, 10, 12, 0, 0)
    }
    val threeDates = DateCalendarHelper.getThreeCalendarDates(cal.timeInMillis, tz)
    assertNotNull(threeDates.banglaDate)
    assertNotNull(threeDates.englishDate)
    assertNotNull(threeDates.hijriDate)
    assertTrue(threeDates.hijriDate.formattedBangla.contains("হিজরি"))
  }

  @Test
  fun testIslamicQuizData_Integrity() {
    val allQuestions = IslamicQuizData.questions
    assertTrue("Should have at least 40 questions", allQuestions.size >= 40)

    allQuestions.forEach { q ->
      assertNotNull("Question text should not be null", q.question)
      assertTrue("Question text should not be blank", q.question.isNotBlank())
      assertEquals("Question should have exactly 4 options", 4, q.options.size)
      assertTrue("Correct option index must be in range 0..3", q.correctOptionIndex in 0..3)
      assertTrue("Explanation should be informative", q.explanation.isNotBlank())
    }

    val quranQuestions = IslamicQuizData.getQuestionsByCategory(QuizCategory.QURAN)
    assertTrue(quranQuestions.isNotEmpty())

    val randomQuiz = IslamicQuizData.getRandomQuiz(QuizCategory.ALL, 10)
    assertEquals(10, randomQuiz.size)

    val dailyQuiz = IslamicQuizData.getDailyQuiz()
    assertEquals(5, dailyQuiz.size)
  }
}
