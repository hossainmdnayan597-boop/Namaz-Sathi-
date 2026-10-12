package com.example.data.hadith

data class HadithCategory(
    val id: String,
    val nameBangla: String,
    val nameEnglish: String
) {
    val titleBangla: String get() = nameBangla
}

data class HadithItem(
    val id: String,
    val categoryId: String,
    val titleBangla: String,
    val bookSourceBangla: String,
    val hadithNumber: String,
    val narratorBangla: String,
    val arabicText: String,
    val translationBangla: String,
    val lessonsBangla: String,
    val grade: String = "সহিহ"
)

object HadithData {

    val CATEGORIES: List<HadithCategory> get() = Sahih1000HadithRepository.ALL_CATEGORIES

    val SAHIH_HADITHS: List<HadithItem> get() = Sahih1000HadithRepository.ALL_1000_HADITHS

    fun searchHadiths(query: String, categoryId: String = "all"): List<HadithItem> {
        return Sahih1000HadithRepository.searchHadiths(query, categoryId)
    }
}
