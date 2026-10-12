package com.example.data.quiz

object IslamicQuizBankPart2 {

    val questions: List<QuizQuestion> = listOf(
        // ==================== নামাজ ও ইবাদত (৫১ - ১০০) ====================
        QuizQuestion(
            id = 51,
            category = QuizCategory.SALAH_IBADAH,
            question = "ইসলামের ভিত্তি মোট কয়টি স্তম্ভের ওপর প্রতিষ্ঠিত?",
            options = listOf("৩টি", "৫টি", "৭টি", "১০টি"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: ইসলামের ভিত্তি পাঁচটি — শাহাদাহ, নামাজ কায়েম, জাকাত আদায়, রমজানের রোজা ও হজ পালন (সহিহ বুখারী ৮)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 52,
            category = QuizCategory.SALAH_IBADAH,
            question = "কেয়ামতের দিন বান্দার আমলের মধ্যে সর্বপ্রথম কোনটির হিসাব নেওয়া হবে?",
            options = listOf("জাকাত", "রোজা", "নামাজ", "হজ"),
            correctOptionIndex = 2,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: কেয়ামতের দিন মানুষের আমলসমূহের মধ্যে সর্বপ্রথম নামাজের হিসাব গ্রহণ করা হবে (তিরমিযী ৪১৩)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 53,
            category = QuizCategory.SALAH_IBADAH,
            question = "অজুর মধ্যে সর্বমোট কয়টি ফরজ রয়েছে?",
            options = listOf("৩টি", "৪টি", "৫টি", "৬টি"),
            correctOptionIndex = 1,
            explanation = "অজুতে চারটি ফরজ রয়েছে: ১. সমস্ত মুখমণ্ডল ধোয়া, ২. দুই হাতের কনুইসহ ধোয়া, ৩. মাথার এক-চতুর্থাংশ মাসেহ করা, এবং ৪. দুই পায়ের গোড়ালি পর্যন্ত ধোয়া।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 54,
            category = QuizCategory.SALAH_IBADAH,
            question = "গোসলের মধ্যে মোট কয়টি ফরজ রয়েছে?",
            options = listOf(" ২টি", "৩টি", "৪টি", "৫টি"),
            correctOptionIndex = 1,
            explanation = "ফরজ গোসলে ৩টি ফরজ কাজ রয়েছে: ১. কুলি করা, ২. নাকের ভেতরে পানি পৌঁছে পরিষ্কার করা, ৩. সমস্ত শরীরে এমনভাবে পানি পৌঁছানো যেন চুলের গোড়াও শুকনো না থাকে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 55,
            category = QuizCategory.SALAH_IBADAH,
            question = "তায়াম্মুমের মধ্যে সর্বমোট কয়টি ফরজ রয়েছে?",
            options = listOf("২টি", "৩টি", "৪টি", "৫টি"),
            correctOptionIndex = 1,
            explanation = "তায়াম্মুমের ফরজ ৩টি: ১. পবিত্রতার নিয়ত করা, ২. পবিত্র মাটিতে হাত মেরে সমস্ত মুখমণ্ডল মাসেহ করা, ৩. মাটিতে পুনরায় হাত মেরে উভয় হাত কনুইসহ মাসেহ করা।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 56,
            category = QuizCategory.SALAH_IBADAH,
            question = "প্রতিদিনের পাঁচ ওয়াক্ত নামাজের মোট ফরজ রাকাত সংখ্যা কত?",
            options = listOf("১৫ রাকাত", "১৭ রাকাত", "২০ রাকাত", "২৫ রাকাত"),
            correctOptionIndex = 1,
            explanation = "দৈনিক ৫ ওয়াক্তের ফরজ মোট ১৭ রাকাত: ফজর ২, যোহর ৪, আসর ৪, মাগরিব ৩, এবং এশা ৪ রাকাত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 57,
            category = QuizCategory.SALAH_IBADAH,
            question = "কোন নামাজে কোনো রুকু ও সিজদা নেই, শুধুমাত্র দাঁড়িয়ে তাকবীর দিয়ে দোয়া পড়তে হয়?",
            options = listOf("ঈদের নামাজ", "জানাযার নামাজ", "ইশরাকের নামাজ", "বিতরের নামাজ"),
            correctOptionIndex = 1,
            explanation = "জানাযার নামাজে কোনো রুকু ও সিজদা নেই; এটি ৪ তাকবীরে দাঁড়িয়ে আদায়কৃত বিশেষ জানাযার দোয়া ও প্রার্থনা।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 58,
            category = QuizCategory.SALAH_IBADAH,
            question = "দিনের কোন তিন সময়ে নামাজ পড়া ও সিজদা করা সম্পূর্ণ নিষিদ্ধ ও হারাম?",
            options = listOf("সূর্যোদয়, ঠিক দ্বিপ্রহর ও সূর্যাস্তের সময়", "ফজরের পর ও এশার পর", "মাগরিব ও এশার মাঝে", "তাহাজ্জুদের সময়"),
            correctOptionIndex = 0,
            explanation = "সূর্য ওঠার সময়, ঠিক দুপুরবেলা সূর্য মাথার উপর অবস্থানের সময় এবং সূর্যাস্তের ঠিক পূর্বমুহূর্তে নামাজ আদায় ও সিজদা করা নিষিদ্ধ।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 59,
            category = QuizCategory.SALAH_IBADAH,
            question = "সোনা বা স্বর্ণের ক্ষেত্রে জাকাত ফরজ হওয়ার ন্যূনতম নিসাব কত?",
            options = listOf("৫ ভরি", "৭.৫ ভরি (প্রায় ৮৫ গ্রাম)", "১০ ভরি", "১২.৫ ভরি"),
            correctOptionIndex = 1,
            explanation = "স্বর্ণের ক্ষেত্রে নিসাব হলো সাড়ে সাত (৭.৫) ভরি বা তোলা (প্রায় ৮৫ গ্রাম খাঁটি স্বর্ণ)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 60,
            category = QuizCategory.SALAH_IBADAH,
            question = "রুপা বা রৌপ্যের ক্ষেত্রে জাকাত ফরজ হওয়ার ন্যূনতম নিসাব কত?",
            options = listOf("৪০ তোলা", "৫২.৫ তোলা (প্রায় ৫৯৫ গ্রাম)", "৬০ তোলা", "৭০ তোলা"),
            correctOptionIndex = 1,
            explanation = "রুপার ক্ষেত্রে নিসাব হলো বায়ান্ন তোলা আট আনা অর্থাৎ সাড়ে বাহান্ন (৫২.৫) তোলা (প্রায় ৫৯৫ গ্রাম খাঁটি রুপা)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 61,
            category = QuizCategory.SALAH_IBADAH,
            question = "নিসাব পরিমাণ উদ্বৃত্ত সম্পদে এক চান্দ্রবছর পূর্ণ হলে কত শতাংশ জাকাত আদায় করা ফরজ?",
            options = listOf("১.৫%", "২.৫% (৪০ ভাগের এক ভাগ)", "৫%", "১০%"),
            correctOptionIndex = 1,
            explanation = "শরিয়তের বিধান অনুযায়ী নিসাব পরিমাণ সম্পদে বছরান্তে ২.৫% বা চল্লিশ ভাগের এক ভাগ জাকাত প্রদান করা ফরজ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 62,
            category = QuizCategory.SALAH_IBADAH,
            question = "পবিত্র হজের মূল ও সর্বপ্রধান রুকন বা ফরজ কোনটি?",
            options = listOf("ইহরাম বাঁধা", "৯ই জিলহজ আরাফাতের ময়দানে অবস্থান", "কাবা তওয়াফ করা", "কোরবানি করা"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: 'আল-হাজ্জু আরাফাহ' অর্থাৎ ৯ই জিলহজ আরাফায় অবস্থানই হলো হজের মূল ভিত্তি (তিরমিযী ৮৮৯)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 63,
            category = QuizCategory.SALAH_IBADAH,
            question = "কাবা শরীফ তওয়াফ করার সময় হাজরে আসওয়াদ থেকে শুরু করে কয় চক্করে এক তওয়াফ পূর্ণ হয়?",
            options = listOf("৩ চক্করে", "৫ চক্করে", "৭ চক্করে", "১০ চক্করে"),
            correctOptionIndex = 2,
            explanation = "হাজরে আসওয়াদ থেকে শুরু করে কাবার চারদিকে পূর্ণ সাত (৭) চক্করে একটি তওয়াফ সমাপ্ত হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 64,
            category = QuizCategory.SALAH_IBADAH,
            question = "হজ ও ওমরাহর সময় সাফা ও মারওয়া পাহাড়দ্বয়ের মাঝে কয়বার সায়ী বা প্রদক্ষিণ করা আবশ্যক?",
            options = listOf("৩ বার", "৫ বার", "৭ বার", "৯ বার"),
            correctOptionIndex = 2,
            explanation = "সাফা থেকে শুরু করে মারওয়া পর্যন্ত মোট ৭ বার যাতায়াত করে সায়ী সম্পন্ন করতে হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 65,
            category = QuizCategory.SALAH_IBADAH,
            question = "রমজান মাসের রাতের বিশেষ সুন্নত নামায যার প্রতি ৪ রাকাত পর বিশ্রাম নেওয়া হয়, তার নাম কী?",
            options = listOf("সালাতুত তাসবীহ", "সালাতুত তারাবীহ", "সালাতুল ইশরাক", "সালাতুল আউয়াবীন"),
            correctOptionIndex = 1,
            explanation = "তারাবীহ শব্দের অর্থ বিশ্রাম নেওয়া। রমজানের রাতে এশার পর জামাতে তারাবীহ নামাজ আদায় করা সুন্নাতে মুয়াক্কাদাহ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 66,
            category = QuizCategory.SALAH_IBADAH,
            question = "রাতের শেষ তৃতীয়াংশে ঘুম থেকে উঠে আদায়কৃত অত্যন্ত ফজিলতপূর্ণ নফল নামাজের নাম কী?",
            options = listOf("তাহাজ্জুদ নামাজ", "চাশতের নামাজ", "ইশরাকের নামাজ", "আউয়াবীনের নামাজ"),
            correctOptionIndex = 0,
            explanation = "তাহাজ্জুদ নামাজ রাতের শেষ ভাগে গভীর রাতে আদায় করা হয় এবং এটি নফল নামাজের মধ্যে সর্বাধিক মর্যাদাশীল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 67,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজে কোনো ওয়াজিব কাজ ভুলবশত ছুটে গেলে বা বেশকম হলে নামাজের শেষে কী আদায় করতে হয়?",
            options = listOf("নতুন করে সম্পূর্ণ নামাজ পড়া", "সাহু সিজদা (সিজদায়ে সাহু)", "নফল রোজা রাখা", "সদকা করা"),
            correctOptionIndex = 1,
            explanation = "নামাজে ভুলবশত কোনো ওয়াজিব বাদ পড়লে শেষ বৈঠকে তাশাহহুদ পাঠের পর ডান দিকে সালাম ফিরিয়ে দুটি সাহু সিজদা দিতে হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 68,
            category = QuizCategory.SALAH_IBADAH,
            question = "সপ্তাহের কোন দিনে জামাতে নামাজ আদায় করা প্রাপ্তবয়স্ক সুস্থ মুসলিম পুরুষের জন্য ফরজ আইন?",
            options = listOf("সোমবার", "বৃহস্পতিবার", "শুক্রবার (জুমু'আ)", "শনিবার"),
            correctOptionIndex = 2,
            explanation = "শুক্রবার জুমার নামাজ জামাতে খুতবাসহ দুই রাকাত ফরজ নামাজ আদায় করা স্বতন্ত্র ফরজ ইবাদত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 69,
            category = QuizCategory.SALAH_IBADAH,
            question = "রমজান মাসের শেষ দশকে আল্লাহর নৈকট্য লাভের উদ্দেশ্যে মসজিদে অবস্থান করার আমলকে কী বলে?",
            options = listOf("সদকাতুল ফিতর", "ইতিকাফ", "কিয়ামুল লাইল", "ওমরাহ"),
            correctOptionIndex = 1,
            explanation = "রমজানের শেষ দশকে মসজিদে লাইলাতুল কদরের অন্বেষণে ইতিকাফ করা সুন্নাতে মুয়াক্কাদাহ আলাল কিফায়াহ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 70,
            category = QuizCategory.SALAH_IBADAH,
            question = "ঈদুল ফিতরের নামাজের পূর্বে দরিদ্রদের মাঝে যে নির্ধারিত আর্থিক দান প্রদান করতে হয় তার নাম কী?",
            options = listOf("সদকাতুল ফিতর (ফিতরা)", "কোরবানি", "মান্নত", "কাফফারা"),
            correctOptionIndex = 0,
            explanation = "সদকাতুল ফিতর রোজার ত্রুটি-বিচ্যুতির ক্ষতিপূরণ এবং ঈদের দিনে গরিব-মিসকিনদের আনন্দের অংশীদার করার জন্য ওয়াজিব।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 71,
            category = QuizCategory.SALAH_IBADAH,
            question = "মুসাফির অবস্থায় চার রাকাত বিশিষ্ট ফরজ নামাজ (যোহর, আসর, এশা) কত রাকাত পড়তে হয়?",
            options = listOf("১ রাকাত", "২ রাকাত (কসর)", "৩ রাকাত", "৪ রাকাত"),
            correctOptionIndex = 1,
            explanation = "শরিয়তসম্মত ভ্রমণের ক্ষেত্রে আল্লাহ মুসাফিরের জন্য চার রাকাতের ফরজ নামাজকে দুই রাকাত সংক্ষেপ (কসর) করার বিধান দিয়েছেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 72,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজের শুরুতে তাকবীরে তাহরীমা বলার পর ইমাম ও মুক্তাদী প্রথমে যে দোয়া পাঠ করে তাকে কী বলে?",
            options = listOf("সানা ('সুবহানাকাল্লাহুম্মা...')", "আত্তাহিয়্যাতু", "দরূদ শরীফ", "দোয়া কুনুত"),
            correctOptionIndex = 0,
            explanation = "তাকবীরে তাহরীমার পর প্রথমে সানা ('সুবহানাকা আল্লাহুম্মা ওয়া বিহামদিকা...') পাঠ করা সুন্নত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 73,
            category = QuizCategory.SALAH_IBADAH,
            question = "বিতর নামাজের তৃতীয় রাকাতে সূরা কেরাত শেষ করে অতিরিক্ত তাকবীর দিয়ে যে বিশেষ দোয়া পড়তে হয় তার নাম কী?",
            options = listOf("দোয়া ইউনুস", "দোয়ায়ে কুনুত", "দোয়া মাছুরা", "দরূদে ইব্রাহিম"),
            correctOptionIndex = 1,
            explanation = "বিতর নামাজের শেষ রাকাতে কিরাতের পর তাকবীর বলে হাত বেঁধে দোয়ায়ে কুনুত পড়া ওয়াজিব।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 74,
            category = QuizCategory.SALAH_IBADAH,
            question = "ফজরের আযানে মুয়াজ্জিন অতিরিক্ত কোন মধুর বাক্যটি উচ্চারণ করেন?",
            options = listOf("আস-সালাতু খাইরুম মিনান নাওম", "কাদ কামাতিস সালাহ", "আল্লাহু আকবার কাবীরা", "লা হাওলা ওয়ালা কুওয়াতা"),
            correctOptionIndex = 0,
            explanation = "'আস-সালাতু খাইরুম মিনান নাওম' অর্থ 'ঘুম অপেক্ষা নামাজ উত্তম', যা ফজরের আযানে বিশেষভাবে পাঠ করা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 75,
            category = QuizCategory.SALAH_IBADAH,
            question = "যেকোনো কঠিন বিষয়ে সঠিক সিদ্ধান্ত নেওয়ার জন্য রাসূলুল্লাহ (সাঃ) যে বিশেষ নফল নামাজ ও দোয়া শিখিয়েছেন তার নাম কী?",
            options = listOf("সালাতুল হাজত", "সালাতুল ইস্তেখারা", "সালাতুত তাওবা", "সালাতুল কুসুফ"),
            correctOptionIndex = 1,
            explanation = "কোনো কাজের কল্যাণ ও মঙ্গল জানার জন্য দুই রাকাত নামাজ পড়ে ইস্তেখারার বিশেষ দোয়া করতে হয় (সহিহ বুখারী ১১৬২)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 76,
            category = QuizCategory.SALAH_IBADAH,
            question = "সূর্যগ্রহণের সময় মহানবী (সাঃ) জামাতে যে দীর্ঘ নামাজ আদায় করতেন তাকে কী বলা হয়?",
            options = listOf("সালাতুল খুসুফ", "সালাতুল কুসুফ", "সালাতুল ইসতিসকা", "সালাতুল খাওফ"),
            correctOptionIndex = 1,
            explanation = "সূর্যগ্রহণের সময় আদায়কৃত নামাজকে 'সালাতুল কুসুফ' এবং চন্দ্রগ্রহণের নামাজকে 'সালাতুল খুসুফ' বলা হয়।",
            difficulty = QuizDifficulty.HARD
        ),
        QuizQuestion(
            id = 77,
            category = QuizCategory.SALAH_IBADAH,
            question = "তীব্র অনাবৃষ্টি ও খরার সময় বৃষ্টির জন্য মাঠে ময়দানে গিয়ে যে বিশেষ প্রার্থনা নামাজ আদায় করা হয় তার নাম কী?",
            options = listOf("সালাতুল ইসতিসকা", "সালাতুল জানাজা", "সালাতুল জুমুআ", "সালাতুশ শোকর"),
            correctOptionIndex = 0,
            explanation = "আল্লাহর কাছে বৃষ্টির প্রার্থনায় মাঠে উপস্থিত হয়ে বিনয়ের সাথে দুই রাকাত নামাজ ও দোয়াই হলো সালাতুল ইসতিসকা।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 78,
            category = QuizCategory.SALAH_IBADAH,
            question = "রমজান মাসের রোজা রাখার জন্য সুবহে সাদিকের পূর্বে যে খাবার গ্রহণ করা সুন্নত তার নাম কী?",
            options = listOf("ইফতার", "সাহরি (সেহরি)", "ওয়ালিমা", "আকিকা"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: তোমরা সাহরি খাও, কেননা সাহরির মধ্যে বরকত রয়েছে (সহিহ বুখারী ১৯২৩)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 79,
            category = QuizCategory.SALAH_IBADAH,
            question = "হজের সময় শয়তানকে লক্ষ্য করে পাথর নিক্ষেপ (রমী) কোন ময়দানে করা হয়?",
            options = listOf("মিনা", "আরাফাত", "মুজদালিফা", "মদিনা"),
            correctOptionIndex = 0,
            explanation = "১০, ১১ ও ১২ই জিলহজ মিনায় অবস্থিত জামারাতসমূহে পাথর নিক্ষেপ করা ওয়াজিব।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 80,
            category = QuizCategory.SALAH_IBADAH,
            question = "পবিত্র কুরআন অনুসারে জাকাত বণ্টনের সর্বমোট খাত বা প্রাপকের সংখ্যা কতটি?",
            options = listOf("৬টি", "৭টি", "৮টি", "১০টি"),
            correctOptionIndex = 2,
            explanation = "সূরা আত-তাওবার ৬০ নম্বর আয়াতে আল্লাহ তায়ালা জাকাত বণ্টনের মোট আটটি (৮) সুনির্দিষ্ট খাত নির্ধারণ করেছেন।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 81,
            category = QuizCategory.SALAH_IBADAH,
            question = "সন্তানের জন্মের সপ্তম দিনে আল্লাহর শুকরিয়াস্বরূপ পশু জবাই করার বিধানকে কী বলা হয়?",
            options = listOf("কোরবানি", "আকিকা", "মান্নত", "সদকা"),
            correctOptionIndex = 1,
            explanation = "সন্তানের জন্মের সপ্তম দিনে নাম রাখা, মাথা মুণ্ডন এবং পশু জবাই করে আকিকা করা সুন্নাতে মুয়াক্কাদাহ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 82,
            category = QuizCategory.SALAH_IBADAH,
            question = "রোজার দিনে ভুলে কোনো কিছু খেয়ে বা পান করে ফেললে রোজার বিধান কী?",
            options = listOf("রোজা ভেঙে যাবে", "রোজা ভাঙবে না এবং তা পূর্ণ করতে হবে", "কাফফারা দিতে হবে", "৬০টি রোজা রাখতে হবে"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: যে ব্যক্তি ভুলে পানাহার করে সে যেন রোজা পূর্ণ করে, কারণ আল্লাহই তাকে পানাহার করিয়েছেন (সহিহ বুখারী ১৯৩৩)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 83,
            category = QuizCategory.SALAH_IBADAH,
            question = "প্রতি আরবি চান্দ্রমাসের ১৩, ১৪ ও ১৫ তারিখে যে তিনটি নফল রোজা রাখা সুন্নত সেগুলোকে কী বলা হয়?",
            options = listOf("সিয়ামু আইয়ামে বিজ", "আশুরার রোজা", "শাওয়ালের রোজা", "আরাফার রোজা"),
            correctOptionIndex = 0,
            explanation = "প্রতি হিজরি মাসের পূর্ণিমার তিন দিন (১৩, ১৪ ও ১৫ তারিখ) নফল রোজা রাখাকে আইয়ামে বীজের রোজা বলা হয়, যা সারা বছর রোজার সমান সওয়াব এনে দেয়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 84,
            category = QuizCategory.SALAH_IBADAH,
            question = "ঈদুল ফিতরের পর শাওয়াল মাসে কয়টি নফল রোজা রাখলে সারা বছর রোজা রাখার সমান সওয়াব মেলে?",
            options = listOf("৩টি", "৬টি", "৯টি", "১০টি"),
            correctOptionIndex = 1,
            explanation = "হাদিসে এসেছে: যে ব্যক্তি রমজানের রোজা রাখল অতঃপর শাওয়াল মাসে ছয়টি রোজা রাখল, সে যেন সারা বছরই রোজা রাখল (সহিহ মুসলিম ১১৬৪)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 85,
            category = QuizCategory.SALAH_IBADAH,
            question = "আরাফাতের দিন (৯ই জিলহজ) হাজী ব্যতীত সাধারণ মুসলিমদের নফল রোজা রাখার ফজিলত কী?",
            options = listOf("পূর্ববর্তী ও পরবর্তী এক বছরের গুনাহ মাফ হয়", "এক মাসের গুনাহ মাফ হয়", "হজের সওয়াব পাওয়া যায়", "একটি উটের সওয়াব মেলে"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) বলেন: আরাফার দিনের রোজার ব্যাপারে আমি আল্লাহর কাছে আশাবাদী যে, তিনি পূর্ববর্তী এক বছর ও পরবর্তী এক বছরের গুনাহ ক্ষমা করে দেবেন (সহিহ মুসলিম)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 86,
            category = QuizCategory.SALAH_IBADAH,
            question = "মহররম মাসের কোন তারিখে আশুরার রোজা রাখা সুন্নত?",
            options = listOf("১লা মহররম", "৭ই মহররম", "১০ই মহররম (সাথে ৯ বা ১১ই মহররম)", "১৫ই মহররম"),
            correctOptionIndex = 2,
            explanation = "১০ই মহররম আশুরার দিন রোজা রাখা সুন্নত এবং ইহুদিদের বিপরীত করার জন্য সাথে ৯ অথবা ১১ তারিখ যোগ করে রোজা রাখা উত্তম।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 87,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজের মধ্যে প্রথম বৈঠক ও শেষ বৈঠকে 'আত্তাহিয়্যাতু' পাঠ করার হুকুম কী?",
            options = listOf("ফরজ", "ওয়াজিব", "সুন্নত", "মুস্তাহাব"),
            correctOptionIndex = 1,
            explanation = "নামাজে প্রথম ও দ্বিতীয় উভয় বৈঠকে তাশাহহুদ বা আত্তাহিয়্যাতু পাঠ করা ওয়াজিব।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 88,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজে রুকুতে যাওয়ার সময় কী তাসবীহ কমপক্ষে তিনবার পাঠ করতে হয়?",
            options = listOf("সুবহানা রাব্বিয়াল আজীম", "সুবহানা রাব্বিয়াল আ'লা", "সামিআল্লাহু লিমান হামিদাহ", "রাব্বানা লাকাল হামদ"),
            correctOptionIndex = 0,
            explanation = "রুকুতে 'সুবহানা রাব্বিয়াল আজীম' (আমার মহান প্রতিপালকের পবিত্রতা ঘোষণা করছি) পাঠ করা সুন্নাত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 89,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজে সিজদায় গিয়ে সর্বনিম্ন তিনবার কোন তাসবীহ পাঠ করা সুন্নত?",
            options = listOf("সুবহানা রাব্বিয়াল আ'লা", "সুবহানা রাব্বিয়াল আজীম", "লা ইলাহা ইল্লাল্লাহ", "আসতাগফিরুল্লাহ"),
            correctOptionIndex = 0,
            explanation = "সিজদায় 'সুবহানা রাব্বিয়াল আ'লা' (আমার সর্বোচ্চ মহান রবের পবিত্রতা ঘোষণা করছি) পাঠ করা সুন্নাত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 90,
            category = QuizCategory.SALAH_IBADAH,
            question = "রুকু থেকে সোজা হয়ে দাঁড়ানোর সময় ইমাম ও একাকী নামাজী কী বাক্য পাঠ করেন?",
            options = listOf("সামিআল্লাহু লিমান হামিদাহ", "আল্লাহু আকবার", "সুবহানাল্লাহ", "আলহামদুলিল্লাহ"),
            correctOptionIndex = 0,
            explanation = "রুকু থেকে ওঠার সময় 'সামিআল্লাহু লিমান হামিদাহ' (যে আল্লাহর প্রশংসা করে আল্লাহ তার কথা শোনেন) বলা সুন্নত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 91,
            category = QuizCategory.SALAH_IBADAH,
            question = "জাকাত কোন কোন নিকট আত্মীয়কে প্রদান করা শরিয়তে সম্পূর্ণ নিষিদ্ধ?",
            options = listOf("পিতা-মাতা, দাদা-দাদী এবং নিজের সন্তান-সন্ততিকে", "চাচা-ফুফুকে", "ভাই-বোনকে", "প্রতিবেশীকে"),
            correctOptionIndex = 0,
            explanation = "নিজের ঔরসজাত সন্তান-সন্ততি ও তাদের বংশধর এবং পিতা-মাতা, দাদা-দাদী ও স্বামী-স্ত্রীকে জাকাত দেওয়া শরিয়তে বৈধ নয়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 92,
            category = QuizCategory.SALAH_IBADAH,
            question = "হজের ইহরাম অবস্থায় পুরুষদের জন্য কেমন পোশাক পরিধান করা নির্ধারিত?",
            options = listOf("সেলাই করা পায়জামা-পাঞ্জাবি", "দুই টুকরো সেলাইবিহীন সাদা চাদর", "কালো বোরকা", "সামরিক পোশাক"),
            correctOptionIndex = 1,
            explanation = "পুরুষদের জন্য ইহরামের নির্ধারিত লেবাস হলো দুটি সেলাইবিহীন সাদা চাদর (একটি তহবন্দ ও অপরটি গায়ে জড়ানো)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 93,
            category = QuizCategory.SALAH_IBADAH,
            question = "হজ ও ওমরাহর তালবিয়াহর মূল বাক্য কোনটি?",
            options = listOf("লাব্বাইকা আল্লাহুম্মা লাব্বাইক...", "সুবহানাল্লাহি ওয়া বিহামদিহী", "আল্লাহু আকবার ওয়া লিল্লাহিল হামদ", "লা হাওলা ওয়ালা কুওয়াতা ইল্লা বিল্লাহ"),
            correctOptionIndex = 0,
            explanation = "তালবিয়ার প্রসিদ্ধ বাক্য: 'লাব্বাইকা আল্লাহুম্মা লাব্বাইক, লাব্বাইকা লা শারীকা লাকা লাব্বাইক...'",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 94,
            category = QuizCategory.SALAH_IBADAH,
            question = "কোন নামাজে মুক্তাদীর জন্য কোনো কিরাত পড়তে হয় না, ইমামের কিরাত মুক্তাদীর কিরাত হিসেবে গণ্য হয়?",
            options = listOf("সকল জামাতবদ্ধ ফরজ নামাজে", "কেবল তারাবীহ নামাজে", "কেবল জুমার নামাজে", "কেবল জানাজার নামাজে"),
            correctOptionIndex = 0,
            explanation = "হানাফী মাযহাব অনুযায়ী জামাতের নামাজে ইমামের কিরাত মুক্তাদীর কিরাত হিসেবে গণ্য হয়, তাই মুক্তাদী কিরাত না পড়ে নীরব থাকে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 95,
            category = QuizCategory.SALAH_IBADAH,
            question = "ফরজ নামাজের শেষ বৈঠকে দরূদ শরীফ পাঠ করার পর কোন দোয়া পাঠ করা সুন্নত?",
            options = listOf("দোয়া মাছুরা ('আল্লাহুম্মা ইন্নী জালামতু নাফসী...')", "দোয়া কুনুত", "আয়াতুল কুরসী", "সূরা নাস"),
            correctOptionIndex = 0,
            explanation = "দরূদ পাঠের পর দোয়া মাছুরা পাঠ করা সুন্নাত, যার দ্বারা আল্লাহর দরবারে পাপ মোচনের আকুল প্রার্থনা জানানো হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 96,
            category = QuizCategory.SALAH_IBADAH,
            question = "বছরে এমন কয়টি বিশেষ দিন রয়েছে যে দিনগুলোতে রোজা রাখা সম্পূর্ণ হারাম ও নিষিদ্ধ?",
            options = listOf("২ দিন", "৩ দিন", "৫ দিন", "৭ দিন"),
            correctOptionIndex = 2,
            explanation = "বছরে মোট ৫ দিন রোজা রাখা হারাম: ঈদুল ফিতরের দিন, ঈদুল আযহার দিন এবং কুরবানির পরবর্তী তিন দিন (১১, ১২ ও ১৩ই জিলহজ - তাশরীকের দিনসমূহ)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 97,
            category = QuizCategory.SALAH_IBADAH,
            question = "জিলহজ মাসের ৯ তারিখ ফজর থেকে ১৩ তারিখ আসর পর্যন্ত প্রত্যেক ফরজ নামাজের পর তাকবীরে তাশরীক পাঠের হুকুম কী?",
            options = listOf("ওয়াজিব", "সুন্নত", "মুস্তাহাব", "নফল"),
            correctOptionIndex = 0,
            explanation = "আইয়ামে তাশরীকের মোট ২৩ ওয়াক্ত ফরজ নামাজের পর একবার 'আল্লাহু আকবার আল্লাহু আকবার লা ইলাহা ইল্লাল্লাহু...' পাঠ করা ওয়াজিব।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 98,
            category = QuizCategory.SALAH_IBADAH,
            question = "সারা জীবনে সামর্থ্যবান ব্যক্তির ওপর হজ কতবার ফরজ হয়?",
            options = listOf("প্রতি বছর", "জীবনে একবার", "জীবনে তিনবার", "পাঁচ বছর পর পর"),
            correctOptionIndex = 1,
            explanation = "শারীরিক ও আর্থিকভাবে সক্ষম মুসলিমের ওপর জীবনে একবার হজ সম্পাদন করা ফরজ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 99,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজের শেষ বৈঠকে কোন দরূদ পাঠ করা হয় যা সর্বশ্রেষ্ঠ দরূদ হিসেবে পরিচিত?",
            options = listOf("দরূদে ইব্রাহিম", "দরূদে তাজ", "দরূদে মাহী", "দরূদে শেফা"),
            correctOptionIndex = 0,
            explanation = "সহিহ হাদিসে বর্ণিত দরূদে ইব্রাহিম ('আল্লাহুম্মা সাল্লি আলা মুহাম্মাদ...') শ্রেষ্ঠ দরূদ যা নামাজে তাশাহহুদের পর পাঠ করা সুন্নাত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 100,
            category = QuizCategory.SALAH_IBADAH,
            question = "নামাজ অবস্থায় কথা বললে, পানাহার করলে বা হাসলে নামাজের কী হয়?",
            options = listOf("নামাজ মাকরুহ হয়", "নামাজ সম্পূর্ণ ভেঙে বাতিল হয়ে যায়", "সাহু সিজদা দিলে ঠিক হয়", "কোনো ক্ষতি হয় না"),
            correctOptionIndex = 1,
            explanation = "নামাজ অবস্থায় ইচ্ছাকৃত বা অনিচ্ছাকৃত কোনো কথা বলা, কিছু খাওয়া-দাওয়া বা শব্দ করে হাসলে নামাজ ভঙ্গ হয়ে যায়।",
            difficulty = QuizDifficulty.EASY
        )
    )
}
