package com.example.data.quiz

enum class QuizCategory(val titleBangla: String, val subtitleBangla: String, val iconEmoji: String) {
    ALL("সকল প্রশ্ন", "সব বিষয়ের কুইজ", "🌟"),
    QURAN("পবিত্র কুরআন", "কুরআনের আয়াত ও সূরা", "📖"),
    PROPHETS("নবী ও রাসূলগণ", "আম্মিয়া আলাইহিমুস সালাম", "🕊️"),
    SALAH_IBADAH("নামাজ ও ইবাদত", "নামাজ, রোজা, জাকাত ও হজ", "🕌"),
    HISTORY_SAHABA("ইতিহাস ও সাহাবী", "সাহাবায়ে কেরাম ও ইসলামের ইতিহাস", "⚔️"),
    GENERAL("সাধারণ জ্ঞান", "মৌলিক দ্বীনি জ্ঞান ও শিষ্টাচার", "💡")
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

    val questions: List<QuizQuestion> = listOf(
        // === পবিত্র কুরআন ===
        QuizQuestion(
            id = 1,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে মোট কতটি সূরা রয়েছে?",
            options = listOf("১১২টি", "১১৪টি", "১১৬টি", "১২০টি"),
            correctOptionIndex = 1,
            explanation = "পবিত্র কুরআনে মোট ১১৪টি সূরা রয়েছে। এর মধ্যে মাক্কী সূরা ৮৬টি এবং মাদানী সূরা ২৮টি।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 2,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের দীর্ঘতম বা সবচেয়ে বড় সূরার নাম কী?",
            options = listOf("সূরা আল-ইমরান", "সূরা আন-নিসা", "সূরা আল-বাকারা", "সূরা আল-মায়েদা"),
            correctOptionIndex = 2,
            explanation = "সূরা আল-বাকারা হলো কুরআনের সবচেয়ে বড় সূরা, যার আয়াত সংখ্যা ২৮৬টি এবং এতে কুরআনের অন্যতম শ্রেষ্ঠ আয়াত 'আয়াতুল কুরসী' অবস্থিত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 3,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরায় কোনো বিসমিল্লাহ নেই?",
            options = listOf("সূরা আত-তাওবাহ", "সূরা আল-আনফাল", "সূরা আল-হুদ", "সূরা ইউনুস"),
            correctOptionIndex = 0,
            explanation = "সূরা আত-তাওবাহ (সূরা বারাআত)-এর শুরুতে বিসমিল্লাহির রাহমানির রাহিম পাঠ করা হয় না। তবে সূরা আন-নামলে দুটি বিসমিল্লাহ রয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 4,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরায় দুটি 'বিসমিল্লাহ' রয়েছে?",
            options = listOf("সূরা আল-কাহাফ", "সূরা আন-নামল", "সূরা আল-ওয়াকিয়া", "সূরা আর-রহমান"),
            correctOptionIndex = 1,
            explanation = "সূরা আন-নামল-এ দুটি বিসমিল্লাহ রয়েছে — একটি সূরার শুরুতে এবং অপরটি ৩০ নম্বর আয়াতে সুলাইমান (আঃ)-এর চিঠির বর্ণনায়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 5,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরাকে 'উম্মুল কুরআন' বা কুরআনের মা বলা হয়?",
            options = listOf("সূরা আল-ফাতিহা", "সূরা ইয়াসিন", "সূরা আল-ইখলাস", "সূরা আল-মুলক"),
            correctOptionIndex = 0,
            explanation = "সূরা আল-ফাতিহাকে 'উম্মুল কুরআন' (কুরআনের মূল/মা) এবং 'আস-সাবউ আল-মাসানী' (পুনরাবৃত্ত সাতটি আয়াত) বলা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 6,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরাটি পাঠ করলে এক-তৃতীয়াংশ কুরআন পড়ার সওয়াব পাওয়া যায়?",
            options = listOf("সূরা আল-ফালাক", "সূরা আন-নাস", "সূরা আল-কাউসার", "সূরা আল-ইখলাস"),
            correctOptionIndex = 3,
            explanation = "সহিহ হাদিস অনুসারে সূরা আল-ইখলাস পাঠ করা পবিত্র কুরআনের এক-তৃতীয়াংশের সমতুল্য (সহিহ বুখারী ৫০১৫)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 7,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরায় সবচেয়ে বেশি বার 'আল্লাহর অনুগ্রহকে অস্বীকার করবে?' আয়াতটি এসেছে?",
            options = listOf("সূরা আর-রহমান", "সূরা আল-ওয়াকিয়া", "সূরা আল-হাদীদ", "সূরা আল-মুলক"),
            correctOptionIndex = 0,
            explanation = "সূরা আর-রহমানে 'ফাবিআইয়্যি আ-লা-ই রাব্বিকুমা তুকায্যিবান' আয়াতটি মোট ৩১ বার এসেছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 8,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের সবচেয়ে ছোট সূরার নাম কী?",
            options = listOf("সূরা আল-আসর", "সূরা আল-কাউসার", "সূরা আল-ইখলাস", "সূরা আন-নাসর"),
            correctOptionIndex = 1,
            explanation = "সূরা আল-কাউসার হলো কুরআনের ক্ষুদ্রতম সূরা, এতে মাত্র ৩টি আয়াত এবং ১০টি শব্দ রয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 9,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরা কবরের আজাব থেকে হেফাজতকারী হিসেবে বর্ণিত হয়েছে?",
            options = listOf("সূরা আল-কাহাফ", "সূরা আল-মুলক", "সূরা আস-সাজদা", "সূরা আদ-দুখান"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন, সূরা আল-মুলক (তাবারাকাল্লাযী) পাঠকারী ব্যক্তির জন্য সুপারিশ করবে যতক্ষণ না তাকে ক্ষমা করা হয় এবং তা কবরের আজাব থেকে মুক্তি দেয় (তিরমিযী ২৮৯১)।",
            difficulty = QuizDifficulty.MEDIUM
        ),

        // === নবী ও রাসূলগণ ===
        QuizQuestion(
            id = 10,
            category = QuizCategory.PROPHETS,
            question = "মানবজাতির আদি পিতা এবং পৃথিবীর প্রথম নবী কে ছিলেন?",
            options = listOf("হযরত নূহ (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত আদম (আঃ)", "হযরত শীষ (আঃ)"),
            correctOptionIndex = 2,
            explanation = "আল্লাহ তায়ালা সর্বপ্রথম হযরত আদম (আঃ)-কে সৃষ্টি করেন এবং তিনিই পৃথিবীর প্রথম মানুষ ও প্রথম নবী।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 11,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীকে 'খলিলুল্লাহ' (আল্লাহর অন্তরঙ্গ বন্ধু) উপাধি দেওয়া হয়েছে?",
            options = listOf("হযরত মুসা (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত ঈসা (আঃ)", "হযরত দাউদ (আঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত ইব্রাহিম (আঃ)-কে আল্লাহ তায়ালা নিজের খলিল বা বন্ধু হিসেবে গ্রহণ করেছিলেন। সূরা নিসার ১২৫ নং আয়াতে ইরশাদ হয়েছে: 'আর আল্লাহ ইব্রাহিমকে অন্তরঙ্গ বন্ধুরূপে গ্রহণ করেছেন।'",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 12,
            category = QuizCategory.PROPHETS,
            question = "পবিত্র কুরআনে কোন নবীর নাম সবচেয়ে বেশি বার (১৩৬ বার) উল্লেখিত হয়েছে?",
            options = listOf("হযরত মুসা (আঃ)", "হযরত ঈসা (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত নূহ (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত মুসা (আলাইহিস সালাম)-এর নাম পবিত্র কুরআনে সর্বাধিক ১৩৬ বার সরাসরি উল্লেখিত হয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 13,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীর উপর পবিত্র তাওরাত কিতাব নাজিল হয়েছিল?",
            options = listOf("হযরত দাউদ (আঃ)", "হযরত ঈসা (আঃ)", "হযরত মুসা (আঃ)", "হযরত ইউসুফ (আঃ)"),
            correctOptionIndex = 2,
            explanation = "আল্লাহ তায়ালা হযরত মুসা (আঃ)-এর উপর তাওরাত কিতাব নাজিল করেছিলেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 14,
            category = QuizCategory.PROPHETS,
            question = "কোন নবী পশু-পাখি ও বাতাসের ভাষা বুঝতে পারতেন এবং জ্বিনদের উপর কর্তৃত্ব পেয়েছিলেন?",
            options = listOf("হযরত আইয়ুব (আঃ)", "হযরত সুলাইমান (আঃ)", "হযরত জাকারিয়া (আঃ)", "হযরত ইউনুস (আঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত সুলাইমান (আঃ)-কে আল্লাহ তায়ালা এমন অনন্য বাদশাহী দান করেছিলেন যার অধীনে মানুষ, জ্বিন, বাতাস এবং পশু-পাখির ভাষা বুঝার মুজিজা ছিল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 15,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীকে আল্লাহ তায়ালা মাছের পেটে জীবিত রেখেছিলেন?",
            options = listOf("হযরত ইউনুস (আঃ)", "হযরত ইয়াকুব (আঃ)", "হযরত লুত (আঃ)", "হযরত সালেহ (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত ইউনুস (আঃ) মাছের পেটে থাকা অবস্থায় দোয়া পাঠ করেছিলেন: 'লা ইলাহা ইল্লা আনতা সুবহানাকা ইন্নি কুনতু মিনায জোয়ালিমীন'।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 16,
            category = QuizCategory.PROPHETS,
            question = "আমাদের প্রিয় নবী হযরত মুহাম্মদ (সাঃ) কোন সালে জন্মগ্রহণ করেন?",
            options = listOf("৫৭০ খ্রিস্টাব্দে", "৫৭১ খ্রিস্টাব্দে", "৫৭৫ খ্রিস্টাব্দে", "৫৮০ খ্রিস্টাব্দে"),
            correctOptionIndex = 1,
            explanation = "হযরত মুহাম্মদ (সাঃ) ৫৭১ খ্রিস্টাব্দের ১২ই রবিউল আউয়াল (আমুল ফিল বা হস্তীবর্ষে) পবিত্র মক্কা নগরীতে কুরাইশ বংশে জন্মগ্রহণ করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 17,
            category = QuizCategory.PROPHETS,
            question = "নবীজী (সাঃ)-এর দুধমাতা কে ছিলেন যিনি শৈশবে তাকে লালন-পালন করেছিলেন?",
            options = listOf("হযরত উম্মে আইমান (রাঃ)", "হযরত হালিমা আস-সাদিয়া (রাঃ)", "হযরত ফাতিমা বিনতে আসাদ", "হযরত সুওয়াইবাহ"),
            correctOptionIndex = 1,
            explanation = "হযরত হালিমা আস-সাদিয়া (রাঃ) বনু সাদ গোত্র থেকে এসে শিশু মুহাম্মদ (সাঃ)-কে দুধ পান করান এবং পরম যত্নে লালন-পালন করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 18,
            category = QuizCategory.PROPHETS,
            question = "হযরত মুহাম্মদ (সাঃ) কত বছর বয়সে প্রথম ওহী বা নবুওয়াত লাভ করেন?",
            options = listOf("৩৫ বছর", "৩৮ বছর", "৪০ বছর", "৪৩ বছর"),
            correctOptionIndex = 2,
            explanation = "৪০ বছর বয়সে রমজান মাসের কদরের রাতে হেরা গুহায় জিবরাইল (আঃ)-এর মাধ্যমে রাসূলুল্লাহ (সাঃ)-এর উপর সূরা আলাকের প্রথম ৫টি আয়াত নাজিল হয়।",
            difficulty = QuizDifficulty.EASY
        ),

        // === নামাজ ও ইবাদত ===
        QuizQuestion(
            id = 19,
            category = QuizCategory.SALAH_IBADAH,
            question = "ইসলামের পাঁচটি মূল স্তম্ভের মধ্যে দ্বিতীয় স্তম্ভ কোনটি?",
            options = listOf("ঈমান", "নামাজ (সালাত)", "রোজা (সিয়াম)", "জাকাত"),
            correctOptionIndex = 1,
            explanation = "ইসলামের ৫টি স্তম্ভ: ১. কালিমা শাহাদাত, ২. সালাত (নামাজ), ৩. সিয়াম (রোজা), ৪. জাকাত, ৫. হজ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 20,
            category = QuizCategory.SALAH_IBADAH,
            question = "দৈনিক পাঁচ ওয়াক্ত ফরজ নামাজের মধ্যে মোট কত রাকাত ফরজ নামাজ রয়েছে?",
            options = listOf("১৫ রাকাত", "১৬ রাকাত", "১৭ রাকাত", "২০ রাকাত"),
            correctOptionIndex = 2,
            explanation = "দৈনিক ১৭ রাকাত ফরজ নামাজ: ফজর ২ + জোহর ৪ + আসর ৪ + মাগরিব ৩ + এশা ৪ = মোট ১৭ রাকাত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 21,
            category = QuizCategory.SALAH_IBADAH,
            question = "পাঁচ ওয়াক্ত নামাজের মধ্যে কোন নামাজে রুকু ও সিজদা নেই?",
            options = listOf("তাহাজ্জুদের নামাজ", "জানাজার নামাজ", "ইশরাকের নামাজ", "চাশতের নামাজ"),
            correctOptionIndex = 1,
            explanation = "জানাজার নামাজে কোনো রুকু বা সিজদা নেই; এটি ৪টি তাকবীর, সানা, দরুদ শরীফ এবং মৃত ব্যক্তির মাগফিরাতের দোয়ার মাধ্যমে দাঁড়িয়ে আদায় করা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 22,
            category = QuizCategory.SALAH_IBADAH,
            question = "ওযুর মোট কয়টি ফরজ রয়েছে?",
            options = listOf("৩টি", "৪টি", "৫টি", "৬টি"),
            correctOptionIndex = 1,
            explanation = "ওযুর ফরজ ৪টি: ১. সম্পূর্ণ মুখমণ্ডল ধোয়া, ২. দুই হাতের কনুইসহ ধোয়া, ৩. মাথার এক-চতুর্থাংশ মাসেহ করা, ৪. দুই পায়ের টাখনুসহ ধোয়া।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 23,
            category = QuizCategory.SALAH_IBADAH,
            question = "তায়াম্মুমের কয়টি ফরজ?",
            options = listOf("২টি", "৩টি", "৪টি", "৫টি"),
            correctOptionIndex = 1,
            explanation = "তায়াম্মুমের ফরজ ৩টি: ১. পবিত্রতার নিয়ত করা, ২. পবিত্র মাটিতে হাত মেরে পুরো মুখমণ্ডল মাসেহ করা, ৩. পুনরায় মাটিতে হাত মেরে দুই হাতের কনুই পর্যন্ত মাসেহ করা।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 24,
            category = QuizCategory.SALAH_IBADAH,
            question = "পবিত্র মিরাজের রাতে উম্মতে মুহাম্মদীর জন্য প্রাথমিকভাবে কত ওয়াক্ত নামাজ ফরজ করা হয়েছিল?",
            options = listOf("১০ ওয়াক্ত", "২৫ ওয়াক্ত", "৪০ ওয়াক্ত", "৫০ ওয়াক্ত"),
            correctOptionIndex = 3,
            explanation = "মিরাজের রজনীতে প্রথমে ৫০ ওয়াক্ত সালাত ফরজ করা হয়েছিল, পরবর্তীতে আল্লাহর রহমতে তা কমিয়ে ৫ ওয়াক্তে নির্ধারণ করা হয় কিন্তু সওয়াব ৫০ ওয়াক্তেরই বহাল রাখা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 25,
            category = QuizCategory.SALAH_IBADAH,
            question = "কোন রাতে ইবাদত করা হাজার মাসের চেয়েও উত্তম?",
            options = listOf("শবে বরাত", "লাইলাতুল কদর", "শবে মিরাজ", "আরাফার রাত"),
            correctOptionIndex = 1,
            explanation = "সূরা আল-কদরে আল্লাহ তায়ালা ইরশাদ করেছেন: 'লাইলাতুল কদরি খাইরুম মিন আলফি শাহর' — কদরের রজনী এক হাজার মাসের চেয়েও শ্রেষ্ঠ ও বরকতময়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 26,
            category = QuizCategory.SALAH_IBADAH,
            question = "ইসলামের বিধান অনুযায়ী প্রতি বছর নিসাব পরিমাণ সম্পদের কত শতাংশ জাকাত দিতে হয়?",
            options = listOf("১.৫%", "২.০%", "২.৫%", "৫.০%"),
            correctOptionIndex = 2,
            explanation = "নিসাব পরিমাণ সম্পদে পূর্ণ এক বছর অতিক্রান্ত হলে তার শতকরা আড়াই শতাংশ (২.৫% বা ১/৪০ অংশ) আল্লাহর রাস্তায় জাকাত দেওয়া ফরজ।",
            difficulty = QuizDifficulty.EASY
        ),

        // === ইসলামের ইতিহাস ও সাহাবীগণ ===
        QuizQuestion(
            id = 27,
            category = QuizCategory.HISTORY_SAHABA,
            question = "ইসলামের প্রথম খলিফা কে ছিলেন?",
            options = listOf("হযরত উমর ইবনুল খাত্তাব (রাঃ)", "হযরত আবু বকর সিদ্দিক (রাঃ)", "হযরত উসমান ইবনে আফফান (রাঃ)", "হযরত আলী ইবনে আবি তালিব (রাঃ)"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ)-এর ওফাতের পর মুসলমানদের প্রথম খলিফা নির্বাচিত হন হযরত আবু বকর সিদ্দিক (রাদিয়াল্লাহু আনহু)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 28,
            category = QuizCategory.HISTORY_SAHABA,
            question = "কোন সাহাবীকে 'সাইফুল্লাহ' (আল্লাহর তরবারি) উপাধিতে ভূষিত করা হয়েছিল?",
            options = listOf("হযরত খালিদ বিন ওয়ালিদ (রাঃ)", "হযরত হামজা (রাঃ)", "হযরত আলী (রাঃ)", "হযরত সাদ বিন আবি ওয়াক্কাস (রাঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত খালিদ বিন ওয়ালিদ (রাঃ)-এর অসাধারণ বীরত্ব ও রণকৌশলের কারণে রাসূলুল্লাহ (সাঃ) তাকে 'সাইফুল্লাহ' উপাধি দান করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 29,
            category = QuizCategory.HISTORY_SAHABA,
            question = "ইসলামের প্রথম মুয়াজ্জিন কে ছিলেন যিনি মদিনা মুনাওয়ারায় আজান দিয়েছিলেন?",
            options = listOf("হযরত সালমান ফারসি (রাঃ)", "হযরত বেলাল ইবনে রাবাহ (রাঃ)", "হযরত আবু জর গিফারী (রাঃ)", "হযরত আবদুল্লাহ ইবনে উম্মে মাকতুম (রাঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত বেলাল ইবনে রাবাহ (রাঃ) ছিলেন ইসলামের প্রথম মুয়াজ্জিন। রাসূলুল্লাহ (সাঃ) তাঁর মিষ্টি ও গম্ভীর কণ্ঠের জন্য তাকে আজান দেওয়ার নির্দেশ দিয়েছিলেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 30,
            category = QuizCategory.HISTORY_SAHABA,
            question = "কোন সাহাবীকে 'জুন-নুরাইন' (দুই নূরের অধিকারী) বলা হয়?",
            options = listOf("হযরত উসমান (রাঃ)", "হযরত আলী (রাঃ)", "হযরত যুবায়ের (রাঃ)", "হযরত তালহা (রাঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত উসমান (রাঃ) রাসূলুল্লাহ (সাঃ)-এর দুই কন্যা হযরত রুকাইয়া ও উম্মে কুলসুম (রাঃ)-কে একের পর এক বিবাহ করার সৌভাগ্য লাভ করায় তাকে 'জুন-নুরাইন' বলা হয়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 31,
            category = QuizCategory.HISTORY_SAHABA,
            question = "ইসলামের ইতিহাসের প্রথম সম্মুখ যুদ্ধ কোনটি ছিল?",
            options = listOf("উহুদের যুদ্ধ", "বদরের যুদ্ধ", "খন্দকের যুদ্ধ", "মুতার যুদ্ধ"),
            correctOptionIndex = 1,
            explanation = "দ্বিতীয় হিজরির ১৭ই রমজান ঐতিহাসিক বদরের যুদ্ধ সংঘটিত হয়। মাত্র ৩১৩ জন মুসলমান সেনাদল কাফেরদের এক হাজার সেনার বিরুদ্ধে মহান বিজয় লাভ করে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 32,
            category = QuizCategory.HISTORY_SAHABA,
            question = "রাসূলুল্লাহ (সাঃ)-এর প্রিয়তমা কন্যা সাইয়্যিদাতু নিসায়িল আলামিন (জান্নাতের নারীদের সর্দার) কে?",
            options = listOf("হযরত আয়েশা (রাঃ)", "হযরত ফাতিমা (রাঃ)", "হযরত খাদিজা (রাঃ)", "হযরত হাফসা (রাঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত ফাতিমাতুজ জাহরা (রাঃ) রাসূলুল্লাহ (সাঃ)-এর কনিষ্ঠা কন্যা এবং জান্নাতবাসী নারীদের নেত্রী।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 33,
            category = QuizCategory.HISTORY_SAHABA,
            question = "মক্কা বিজয় কোন হিজরি সনে সংঘটিত হয়েছিল?",
            options = listOf("৬ষ্ঠ হিজরি", "৮ম হিজরি", "৯ম হিজরি", "১০ম হিজরি"),
            correctOptionIndex = 1,
            explanation = "৮ম হিজরির ২০শে রমজান বিনা রক্তপাতে ঐতিহাসিক মক্কা বিজয় সাধিত হয় এবং কাবা শরীফ ৩৬০টি মূর্তি থেকে মুক্ত করা হয়।",
            difficulty = QuizDifficulty.MEDIUM
        ),

        // === সাধারণ জ্ঞান ও আকীদা ===
        QuizQuestion(
            id = 34,
            category = QuizCategory.GENERAL,
            question = "ইসলামের প্রথম কেবলা কোনটি ছিল?",
            options = listOf("কাবা শরীফ (মক্কা)", "মসজিদুল আকসা (বায়তুল মুকাদ্দাস)", "মসজিদে নববী (মদিনা)", "মসজিদে কুবা"),
            correctOptionIndex = 1,
            explanation = "ইসলামের প্রথম কেবলা ছিল জেরুজালেমের মসজিদুল আকসা। হিজরতের প্রায় ১৬-১৭ মাস পর কেবলা পরিবর্তন হয়ে মক্কার কাবা শরীফে নির্ধারিত হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 35,
            category = QuizCategory.GENERAL,
            question = "ইসলামের প্রথম মসজিদ কোনটি যা রাসূলুল্লাহ (সাঃ) মদিনা পৌঁছানোর আগে নির্মাণ করেছিলেন?",
            options = listOf("মসজিদে নববী", "মসজিদে কুবা", "মসজিদুল কিবলাতাইন", "মসজিদে জুমু'আ"),
            correctOptionIndex = 1,
            explanation = "হিজরতের সময় মদিনা প্রবেশের প্রাক্কালে কুবা নামক স্থানে ইসলামের প্রথম মসজিদ 'মসজিদে কুবা' প্রতিষ্ঠিত হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 36,
            category = QuizCategory.GENERAL,
            question = "আল্লাহ তায়ালার গুণবাচক সুন্দর নাম (আসমাউল হুসনা) কয়টি?",
            options = listOf("৬৩টি", "৭২টি", "৮৪টি", "৯৯টি"),
            correctOptionIndex = 3,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: 'নিশ্চয়ই আল্লাহর নিরানব্বইটি নাম রয়েছে, যে ব্যক্তি এগুলোর হিফাজত ও আমল করবে সে জান্নাতে প্রবেশ করবে' (সহিহ বুখারী ২৭৩৬)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 37,
            category = QuizCategory.GENERAL,
            question = "আল্লাহ তায়ালার কাছে সবচেয়ে প্রিয় চারটি তাসবীহ বা বাক্য কোনটি?",
            options = listOf(
                "সুবহানাল্লাহ, আলহামদুলিল্লাহ, লা ইলাহা ইল্লাল্লাহ, আল্লাহু আকবার",
                "আস্তাগফিরুল্লাহ, বিসমিল্লাহ, ইনশাআল্লাহ, মাশাআল্লাহ",
                "লা হাওলা ওয়ালা কুওয়াতা ইল্লা বিল্লাহ",
                "ইয়া রাহমানু, ইয়া রাহিমু, ইয়া মালিকু, ইয়া কুদ্দুস"
            ),
            correctOptionIndex = 0,
            explanation = "সহিহ মুসলিমের হাদিস অনুসারে আল্লাহর নিকট সর্বাধিক প্রিয় বাক্য চারটি: ১. সুবহানাল্লাহ, ২. আলহামদুলিল্লাহ, ৩. লা ইলাহা ইল্লাল্লাহ, ৪. আল্লাহু আকবার।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 38,
            category = QuizCategory.GENERAL,
            question = "ইসলামিক হিজরি ক্যালেন্ডারে মোট কয়টি মাসকে সম্মানিত বা 'আশহুরুল হুরুম' ঘোষণা করা হয়েছে?",
            options = listOf("২টি মাস", "৩টি মাস", "৪টি মাস", "৫টি মাস"),
            correctOptionIndex = 2,
            explanation = "পবিত্র কুরআনে উল্লেখিত সম্মানিত ৪টি মাস হলো: জিলকদ, জিলহজ, মহররম এবং রজব (সূরা তাওবাহ ৩৬)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 39,
            category = QuizCategory.GENERAL,
            question = "জমজম কূপের সৃষ্টি হয়েছিল কোন নবীর শিশুকালে তাঁর পায়ের আঘাতে?",
            options = listOf("হযরত ইসমাইল (আঃ)", "হযরত ইসহাক (আঃ)", "হযরত ইউসুফ (আঃ)", "হযরত ইয়াহিয়া (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত হাজেরা (আঃ) তৃষ্ণার্ত শিশু ইসমাইল (আঃ)-কে সাফা ও মারওয়া পাহাড়ে রেখে পানির সন্ধানে দৌড়াদৌড়ি করার পর শিশু ইসমাইল (আঃ)-এর গোড়ালির আঘাতে অলৌকিকভাবে পবিত্র জমজম কূপের সৃষ্টি হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 40,
            category = QuizCategory.GENERAL,
            question = "হজের মূল রুকন বা ওয়াকুফ কোন দিনে আরাফাতের ময়দানে অবস্থান করা?",
            options = listOf("৭ই জিলহজ", "৮ই জিলহজ", "৯ই জিলহজ", "১০ই জিলহজ"),
            correctOptionIndex = 2,
            explanation = "৯ই জিলহজ আরাফাতের ময়দানে অবস্থান করাই হলো হজের প্রধান ও আবশ্যিক রুকন। রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: 'আল-হাজ্জু আরাফাহ' — আরাফায় অবস্থানই মূল হজ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 41,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন আয়াতে জান্নাতবাসীদের পোশাক ও নদীসমূহের বিবরণ রয়েছে এবং একে 'আয়াতুল কুরসী' বলা হয়?",
            options = listOf("সূরা বাকারার ২৫৫ নং আয়াত", "সূরা বাকারার ২৮৬ নং আয়াত", "সূরা আল-ইমরানের ১৮ নং আয়াত", "সূরা তওবার ১২৮ নং আয়াত"),
            correctOptionIndex = 0,
            explanation = "সূরা বাকারার ২৫৫ নং আয়াত হলো 'আয়াতুল কুরসী', যা পবিত্র কুরআনের সর্বাধিক মর্যাদাপূর্ণ আয়াত। এতে আল্লাহর একত্ববাদ ও অসীম ক্ষমতার অনুপম বর্ণনা রয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 42,
            category = QuizCategory.PROPHETS,
            question = "কোন নবী দীর্ঘ রোগব্যাধি ও শারীরিক কষ্টের মধ্যে থেকেও অনন্য সবর ও ধৈর্যের উজ্জ্বল দৃষ্টান্ত স্থাপন করেছিলেন?",
            options = listOf("হযরত আইয়ুব (আঃ)", "হযরত ইউনুস (আঃ)", "হযরত জাকারিয়া (আঃ)", "হযরত হুদ (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত আইয়ুব (আলাইহিস সালাম) কঠিন অসুস্থতা ও সম্পদ হারানোর পরও অটল ধৈর্য ধারণ করেছিলেন। তাঁর সবর প্রবাদতুল্য।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 43,
            category = QuizCategory.SALAH_IBADAH,
            question = "জুমু'আর দিনের বিশেষ কোন সময় দোয়া কবুল হওয়ার কথা সহিহ হাদিসে বর্ণিত হয়েছে?",
            options = listOf("ফজরের পূর্বে", "সূর্যোদয়ের সময়", "আসরের পর থেকে মাগরিব পর্যন্ত", "মধ্যরাতে"),
            correctOptionIndex = 2,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: জুমুআর দিনে এমন একটি মুহূর্ত রয়েছে, যে সময়ে কোনো মুসলিম বান্দা আল্লাহর কাছে যা কিছু চায় আল্লাহ তাকে তা দান করেন। অধিকাংশ আলেমের মতে তা আসরের পর থেকে মাগরিবের মধ্যবর্তী সময়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 44,
            category = QuizCategory.HISTORY_SAHABA,
            question = "কোন নারী সর্বপ্রথম ইসলাম গ্রহণ করেছিলেন?",
            options = listOf("হযরত খাদিজা বিনতে খুওয়াইলিদ (রাঃ)", "হযরত আয়েশা (রাঃ)", "হযরত সুমাইয়া (রাঃ)", "হযরত ফাতিমা (রাঃ)"),
            correctOptionIndex = 0,
            explanation = "উম্মুল মুমিনীন হযরত খাদিজা (রাদিয়াল্লাহু আনহা) নবুওয়াত প্রকাশের পর রাসূলুল্লাহ (সাঃ)-এর প্রতি সর্বপ্রথম ঈমান এনেছিলেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 45,
            category = QuizCategory.GENERAL,
            question = "কোন আমলটি আল্লাহর কাছে সবচেয়ে প্রিয় বলে রাসূলুল্লাহ (সাঃ) জানিয়েছেন?",
            options = listOf("সময়ে নামাজ আদায় করা", "মা-বাবার সেবা করা", "দান-সদকা করা", "নফল রোজা রাখা"),
            correctOptionIndex = 0,
            explanation = "আবদুল্লাহ ইবনে মাসউদ (রাঃ) রাসূলুল্লাহ (সাঃ)-কে জিজ্ঞেস করেন: আল্লাহর কাছে সবচেয়ে প্রিয় আমল কোনটি? তিনি বললেন: 'ওয়াক্তমতো সময়মতো নামাজ আদায় করা' (সহিহ বুখারী ৫২৭)।",
            difficulty = QuizDifficulty.EASY
        )
    )

    fun getQuestionsByCategory(category: QuizCategory): List<QuizQuestion> {
        return if (category == QuizCategory.ALL) {
            questions
        } else {
            questions.filter { it.category == category }
        }
    }

    fun getRandomQuiz(category: QuizCategory, count: Int = 10): List<QuizQuestion> {
        val pool = getQuestionsByCategory(category)
        return pool.shuffled().take(count.coerceAtMost(pool.size))
    }

    fun getDailyQuiz(): List<QuizQuestion> {
        // Deterministic daily set based on day of year
        val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)
        val seed = dayOfYear % (questions.size - 5)
        return questions.subList(seed, seed + 5)
    }
}
