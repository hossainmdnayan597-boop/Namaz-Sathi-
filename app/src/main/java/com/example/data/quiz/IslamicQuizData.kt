package com.example.data.quiz

enum class QuizCategory(val titleBangla: String, val subtitleBangla: String, val iconEmoji: String) {
    ALL("সকল বিষয়", "সব বিষয়ের সংমিশ্রিত কুইজ", "🌟"),
    QURAN("পবিত্র কুরআন", "কুরআনের আয়াত, সূরা ও ফজিলত", "📖"),
    PROPHETS("নবী ও রাসূলগণ", "আম্বিয়া আলাইহিমুস সালাম", "🕊️"),
    SALAH_IBADAH("নামাজ ও ইবাদত", "নামাজ, রোজা, জাকাত ও হজ", "🕌"),
    HISTORY_SAHABA("ইতিহাস ও সাহাবী", "সাহাবায়ে কেরাম ও সোনালী ইতিহাস", "⚔️"),
    GENERAL("সাধারণ জ্ঞান", "আকীদা, ফেরেশতা ও ইসলামী শিষ্টাচার", "💡")
}

enum class QuizDifficulty(val titleBangla: String) {
    EASY("সহজ"),
    MEDIUM("মাঝারি"),
    HARD("উন্নত")
}

data class QuizQuestion(
    val id: Int,
    val category: QuizCategory,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val difficulty: QuizDifficulty = QuizDifficulty.EASY
)

object IslamicQuizData {

    // 200 authentic, comprehensive Islamic questions across 4 modular banks
    val allQuestions: List<QuizQuestion> by lazy {
        (IslamicQuizBankPart1.questions +
         IslamicQuizBankPart2.questions +
         IslamicQuizBankPart3.questions +
         IslamicQuizBankPart4.questions)
            .distinctBy { it.id }
    }

    val questions: List<QuizQuestion> get() = allQuestions

    fun getQuestionsByCategory(category: QuizCategory): List<QuizQuestion> {
        return if (category == QuizCategory.ALL) {
            allQuestions
        } else {
            allQuestions.filter { it.category == category }
        }
    }

    /**
     * Retrieves a random set of questions ensuring NO duplicate or similar questions are returned.
     * @param category Selected category
     * @param count Requested question count (e.g. 10, 20, 30, 50)
     * @param excludeIds Set of question IDs already answered recently, avoiding repetition.
     */
    fun getRandomQuiz(
        category: QuizCategory,
        count: Int = 10,
        excludeIds: Set<Int> = emptySet()
    ): List<QuizQuestion> {
        val pool = getQuestionsByCategory(category)
        val unserved = pool.filter { it.id !in excludeIds }

        val targetPool = if (unserved.size >= count) {
            unserved
        } else {
            // If the user has exhausted unseen questions, reshuffle the entire pool
            pool
        }

        return targetPool.shuffled().distinctBy { it.id }.take(count.coerceAtMost(targetPool.size))
    }

    /**
     * Daily Challenge: Generates exactly 50 unique questions for the day
     * Curated deterministically using day-of-year and year seed.
     * Guarantees 50 completely unique questions balanced across all Islamic topics.
     */
    fun getDailyQuiz(dayOffset: Int = 0): List<QuizQuestion> {
        val calendar = java.util.Calendar.getInstance()
        if (dayOffset != 0) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, dayOffset)
        }
        val year = calendar.get(java.util.Calendar.YEAR)
        val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)
        val dailySeed = (year * 365L + dayOfYear)
        val random = java.util.Random(dailySeed)

        // Select 10 from each of the 5 distinct categories for an optimal, balanced 50 questions
        val quranQuestions = allQuestions.filter { it.category == QuizCategory.QURAN }.shuffled(random)
        val prophetsQuestions = allQuestions.filter { it.category == QuizCategory.PROPHETS }.shuffled(random)
        val salahQuestions = allQuestions.filter { it.category == QuizCategory.SALAH_IBADAH }.shuffled(random)
        val historyQuestions = allQuestions.filter { it.category == QuizCategory.HISTORY_SAHABA }.shuffled(random)
        val generalQuestions = allQuestions.filter { it.category == QuizCategory.GENERAL }.shuffled(random)

        val daily50 = mutableListOf<QuizQuestion>()
        daily50.addAll(quranQuestions.take(10))
        daily50.addAll(prophetsQuestions.take(10))
        daily50.addAll(salahQuestions.take(10))
        daily50.addAll(historyQuestions.take(10))
        daily50.addAll(generalQuestions.take(10))

        // If any category had fewer than 10, fill to reach exactly 50
        if (daily50.size < 50) {
            val remaining = allQuestions
                .filter { q -> daily50.none { it.id == q.id } }
                .shuffled(random)
            daily50.addAll(remaining.take(50 - daily50.size))
        }

        // Shuffle the final 50 questions deterministically so categories are mixed throughout the test
        return daily50.shuffled(random).distinctBy { it.id }.take(50)
    }
}
