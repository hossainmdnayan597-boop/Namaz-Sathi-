package com.example.data.quiz

object IslamicQuizBankPart1 {

    val questions: List<QuizQuestion> = listOf(
        // ==================== পবিত্র কুরআন (১ - ২৫) ====================
        QuizQuestion(
            id = 1,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে মোট কতটি সূরা রয়েছে?",
            options = listOf("১১২টি", "১১৪টি", "১১৬টি", "১২০টি"),
            correctOptionIndex = 1,
            explanation = "পবিত্র কুরআনে মোট ১১৪টি সূরা রয়েছে। এর মধ্যে মাক্কী সূরা ৮৬টি এবং মাদানী সূরা ২৮টি।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 2,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের দীর্ঘতম বা সবচেয়ে বড় সূরার নাম কী?",
            options = listOf("সূরা আল-ইমরান", "সূরা আন-নিসা", "সূরা আল-বাকারা", "সূরা আল-মায়েদা"),
            correctOptionIndex = 2,
            explanation = "সূরা আল-বাকারা হলো কুরআনের সবচেয়ে বড় সূরা, যার মোট আয়াত সংখ্যা ২৮৬টি এবং এতে সর্বশ্রেষ্ঠ আয়াত 'আয়াতুল কুরসী' রয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 3,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরার শুরুতে 'বিসমিল্লাহ' নেই?",
            options = listOf("সূরা আত-তাওবাহ", "সূরা আল-আনফাল", "সূরা আল-হুদ", "সূরা ইউনুস"),
            correctOptionIndex = 0,
            explanation = "সূরা আত-তাওবাহ (সূরা বারাআত)-এর শুরুতে বিসমিল্লাহির রাহমানির রাহিম পাঠ করা হয় না।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 4,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরায় দুটি 'বিসমিল্লাহ' রয়েছে?",
            options = listOf("সূরা আল-কাহাফ", "সূরা আন-নামল", "সূরা আল-ওয়াকিয়া", "সূরা আর-রহমান"),
            correctOptionIndex = 1,
            explanation = "সূরা আন-নামল-এ দুটি বিসমিল্লাহ রয়েছে — একটি সূরার শুরুতে এবং অপরটি ৩০ নম্বর আয়াতে সুলাইমান (আঃ)-এর চিঠির বিবরণে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 5,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরাকে 'উম্মুল কুরআন' বা কুরআনের মা বলা হয়?",
            options = listOf("সূরা আল-ফাতিহা", "সূরা ইয়াসিন", "সূরা আল-ইখলাস", "সূরা আল-মুলক"),
            correctOptionIndex = 0,
            explanation = "সূরা আল-ফাতিহাকে 'উম্মুল কুরআন' (কুরআনের মূল/মা) এবং 'আস-সাবউ আল-মাসানী' (পুনরাবৃত্ত সাতটি আয়াত) বলা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 6,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরাটি পাঠ করলে এক-তৃতীয়াংশ কুরআন তিলাওয়াতের সওয়াব মেলে?",
            options = listOf("সূরা আল-ফালাক", "সূরা আন-নাস", "সূরা আল-কাউসার", "সূরা আল-ইখলাস"),
            correctOptionIndex = 3,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন, সূরা আল-ইখলাস পাঠ করা পবিত্র কুরআনের এক-তৃতীয়াংশ তিলাওয়াতের সমতুল্য (সহিহ বুখারী ৫০১৫)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 7,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের সবচেয়ে ছোট সূরার নাম কী?",
            options = listOf("সূরা আল-আসর", "সূরা আল-কাউসার", "সূরা আল-ইখলাস", "সূরা আন-নাসর"),
            correctOptionIndex = 1,
            explanation = "সূরা আল-কাউসার হলো কুরআনের ক্ষুদ্রতম সূরা, এতে মাত্র ৩টি আয়াত এবং ১০টি শব্দ রয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 8,
            category = QuizCategory.QURAN,
            question = "কবরের আজাব থেকে মুক্তির জন্য রাসূলুল্লাহ (সাঃ) রাতে কোন সূরা তিলাওয়াতের তাগিদ দিয়েছেন?",
            options = listOf("সূরা আল-কাহাফ", "সূরা আল-মুলক", "সূরা আস-সাজদা", "সূরা আদ-দুখান"),
            correctOptionIndex = 1,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: সূরা আল-মুলক (তাবারাকাল্লাযী) পাঠকারীর জন্য সুপারিশ করে যতক্ষণ না তাকে ক্ষমা করা হয় এবং তা কবরের আজাব থেকে রক্ষা করে (তিরমিযী ২৮৯১)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 9,
            category = QuizCategory.QURAN,
            question = "জুমু'আর দিনে কোন সূরা তিলাওয়াত করলে দুই জুমার মধ্যবর্তী সময় নূর দ্বারা আলোকিত থাকে?",
            options = listOf("সূরা আল-কাহাফ", "সূরা আর-রহমান", "সূরা আল-ওয়াকিয়া", "সূরা ইয়াসিন"),
            correctOptionIndex = 0,
            explanation = "সহিহ হাদিসে এসেছে: যে ব্যক্তি জুমার দিন সূরা আল-কাহাফ পাঠ করবে, তার জন্য দুই জুমার মধ্যবর্তী সময় নূর বিকিরণ করবে (বায়হাকী)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 10,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরাকে 'কুরআনের হৃৎপিণ্ড' বা দিল বলা হয়?",
            options = listOf("সূরা আর-রহমান", "সূরা ইয়াসিন", "সূরা আল-মুলক", "সূরা আল-ওয়াকিয়া"),
            correctOptionIndex = 1,
            explanation = "হাদিসে সূরা ইয়াসিনকে কুরআনের হৃৎপিণ্ড বা অন্তর (কলবুল কুরআন) হিসেবে আখ্যায়িত করা হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 11,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে সর্বমোট কতটি সিজদার আয়াত রয়েছে?",
            options = listOf("১২টি", "১৪টি", "১৬টি", "১৮টি"),
            correctOptionIndex = 1,
            explanation = "পবিত্র কুরআনে সর্বমোট ১৪টি তিলাওয়াতী সিজদার আয়াত রয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 12,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে মোট কতটি পারা এবং রুকু রয়েছে?",
            options = listOf("৩০ পারা ও ৫৪০ রুকু", "৩০ পারা ও ৫৫৮ রুকু", "৩০ পারা ও ৫০০ রুকু", "৩২ পারা ও ৫৬০ রুকু"),
            correctOptionIndex = 1,
            explanation = "পবিত্র কুরআনে ৩০টি পারা এবং ৫৫৮টি রুকু রয়েছে। এছাড়াও তিলাওয়াতের সুবিধার্থে ৭টি মঞ্জিল রয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 13,
            category = QuizCategory.QURAN,
            question = "হেরা গুহায় সর্বপ্রথম অবতীর্ণ ওহী পবিত্র কুরআনের কোন সূরার অংশ?",
            options = listOf("সূরা আল-ফাতিহা", "সূরা আল-মুদ্দাসসির", "সূরা আল-আলাক", "সূরা আল-কালাম"),
            correctOptionIndex = 2,
            explanation = "সূরা আল-আলাকের প্রথম পাঁচটি আয়াত ('ইকরা বিসমি রাব্বিকাল্লাযী খালাক') সর্বপ্রথম জিবরাইল (আঃ)-এর মাধ্যমে অবতীর্ণ হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 14,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে কোন নবীর নাম সবচেয়ে বেশি বার (১৩৬ বার) উল্লেখিত হয়েছে?",
            options = listOf("হযরত ইব্রাহিম (আঃ)", "হযরত মুসা (আঃ)", "হযরত ঈসা (আঃ)", "হযরত নূহ (আঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত মুসা (আলাইহিস সালাম)-এর নাম পবিত্র কুরআনে সর্বাধিক ১৩৬ বার উল্লেখিত হয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 15,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে একমাত্র কোন মহিলার নাম সরাসরি উল্লেখ করা হয়েছে?",
            options = listOf("হযরত আয়েশা (রাঃ)", "হযরত খাদিজা (রাঃ)", "হযরত মরিয়ম (আঃ)", "হযরত আসিয়া (আঃ)"),
            correctOptionIndex = 2,
            explanation = "ঈসা (আঃ)-এর মাতা হযরত মরিয়ম (আলাইহাস সালাম)-এর নাম পবিত্র কুরআনে ৩৪ বার এসেছে এবং তাঁর নামে একটি পূর্ণাঙ্গ সূরাও রয়েছে (সূরা মারইয়াম)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 16,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে একমাত্র কোন সাহাবীর নাম সরাসরি উল্লেখ রয়েছে?",
            options = listOf("হযরত আবু বকর (রাঃ)", "হযরত জায়েদ ইবনে হারেসা (রাঃ)", "হযরত ওমর (রাঃ)", "হযরত ওসমান (রাঃ)"),
            correctOptionIndex = 1,
            explanation = "সূরা আল-আহযাবের ৩৭ নম্বর আয়াতে হযরত জায়েদ ইবনে হারেসা (রাদিয়াল্লাহু আনহু)-এর নাম সরাসরি উল্লেখ রয়েছে।",
            difficulty = QuizDifficulty.HARD
        ),
        QuizQuestion(
            id = 17,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন আয়াতে 'আয়াতুল কুরসী' অবস্থিত?",
            options = listOf("সূরা আল-বাকারার ২৫৫ নং আয়াত", "সূরা আল-বাকারার ২৮৬ নং আয়াত", "সূরা আল-ইমরানের ১৮ নং আয়াত", "সূরা আন-নিসার ৩৬ নং আয়াত"),
            correctOptionIndex = 0,
            explanation = "সূরা বাকারার ২৫৫ নং আয়াত হলো 'আয়াতুল কুরসী', যা কুরআনের সর্বাধিক মর্যাদাশীল আয়াত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 18,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরায় 'ফাবিআইয়্যি আ-লাই রাব্বিকুমা তুকায্যিবান' আয়াতটি ৩১ বার এসেছে?",
            options = listOf("সূরা আর-রহমান", "সূরা আল-ওয়াকিয়া", "সূরা আল-হাদীদ", "সূরা আল-মুলক"),
            correctOptionIndex = 0,
            explanation = "সূরা আর-রহমানে 'তোমরা তোমাদের রবের কোন কোন অনুগ্রহ অস্বীকার করবে?' আয়াতটি ৩১ বার পুনরাবৃত্তি হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 19,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআন কোন রাতে লওহে মাহফুজ থেকে প্রথম আসমানে অবতীর্ণ হয়েছিল?",
            options = listOf("শবে বরাত", "লাইলাতুল কদর", "মিরাজের রাতে", "আশুরার রাতে"),
            correctOptionIndex = 1,
            explanation = "পবিত্র রমজান মাসের বরকতময় লাইলাতুল কদরে পবিত্র কুরআন প্রথম আসমানে নাযিল হয় (সূরা আল-কদর ১)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 20,
            category = QuizCategory.QURAN,
            question = "প্রথম খলিফা আবু বকর (রাঃ)-এর যুগে কুরআন সংকলন কমিটির প্রধান কে ছিলেন?",
            options = listOf("হযরত জায়েদ ইবনে সাবিত (রাঃ)", "হযরত আলী (রাঃ)", "হযরত উবাই ইবনে কাব (রাঃ)", "হযরত আব্দুল্লাহ ইবনে মাসউদ (রাঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত আবু বকর (রাঃ) সাহাবী হযরত জায়েদ ইবনে সাবিত (রাঃ)-কে কুরআন সংকলন ও একত্রিত করার প্রধান দায়িত্ব অর্পণ করেছিলেন।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 21,
            category = QuizCategory.QURAN,
            question = "কুরআনের কোন সূরা দারিদ্র্য ও অভাব থেকে রক্ষার জন্য রাতে পাঠ করতে উৎসাহিত করা হয়েছে?",
            options = listOf("সূরা আল-ওয়াকিয়া", "সূরা আল-মুযযাম্মিল", "সূরা আল-মুদ্দাসসির", "সূরা আত-ত্বলাক"),
            correctOptionIndex = 0,
            explanation = "আব্দুল্লাহ ইবনে মাসউদ (রাঃ) বর্ণিত হাদিসে এসেছে, যে ব্যক্তি প্রতি রাতে সূরা আল-ওয়াকিয়া তিলাওয়াত করে, সে কখনো অভাব-অনটনে পতিত হবে না।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 22,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনের কোন সূরাটি সম্পূর্ণ এক বৈঠকে একসাথে নাযিল হয়েছিল এবং একে বিদায়ী সূরা বলা হয়?",
            options = listOf("সূরা আন-নাসর", "সূরা আল-ফাতিহা", "সূরা আল-কাউসার", "সূরা আল-আসর"),
            correctOptionIndex = 0,
            explanation = "সূরা আন-নাসর ('ইযা জা-আ নাসরুল্লাহি ওয়াল ফাতহ') পূর্ণাঙ্গভাবে একসাথে অবতীর্ণ হয় এবং এতে রাসূলুল্লাহ (সাঃ)-এর ওফাত নিকটবর্তী হওয়ার ইঙ্গিত ছিল।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 23,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে বর্ণিত কোন সূরার নাম এক বিষাক্ত পতঙ্গের নামে রাখা হয়েছে?",
            options = listOf("সূরা আন-নাহল (মৌমাছি)", "সূরা আল-আনকাবুত (মাকড়সা)", "সূরা আন-নামল (পিঁপড়া)", "সূরা আল-ফিল (হাতি)"),
            correctOptionIndex = 1,
            explanation = "সূরা আল-আনকাবুত অর্থ মাকড়সা, যার দুর্বল জালকে মিথ্যা উপাস্যদের উপমা হিসেবে বর্ণনা করা হয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 24,
            category = QuizCategory.QURAN,
            question = "পবিত্র কুরআনে মোট কতজন নবীর নাম সুস্পষ্টভাবে উল্লেখ রয়েছে?",
            options = listOf("২০ জন", "২৫ জন", "৩০ জন", "৪০ জন"),
            correctOptionIndex = 1,
            explanation = "পবিত্র কুরআনে সর্বমোট ২৫ জন সম্মানিত নবী ও রাসূলের নাম সুস্পষ্টভাবে বর্ণিত হয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 25,
            category = QuizCategory.QURAN,
            question = "কুরআনের কোন সূরায় জুলকারনাইন ও আসহাবে কাহাফের অলৌকিক ইতিহাস বর্ণিত হয়েছে?",
            options = listOf("সূরা আল-কাহাফ", "সূরা ইউসুফ", "সূরা আল-কাসাস", "সূরা মারইয়াম"),
            correctOptionIndex = 0,
            explanation = "সূরা আল-কাহাফে আসহাবে কাহাফের গুহাবাসী যুবকবৃন্দ, মুসা ও খিজির (আঃ) এবং বাদশা জুলকারনাইনের ঈমানী ইতিহাস বর্ণিত হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),

        // ==================== নবী ও রাসূলগণ (২৬ - ৫০) ====================
        QuizQuestion(
            id = 26,
            category = QuizCategory.PROPHETS,
            question = "মানবজাতির আদি পিতা এবং পৃথিবীর প্রথম নবী কে ছিলেন?",
            options = listOf("হযরত নূহ (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত আদম (আঃ)", "হযরত শীষ (আঃ)"),
            correctOptionIndex = 2,
            explanation = "হযরত আদম (আলাইহিস সালাম) হলেন পৃথিবীর প্রথম মানব এবং মানবজাতির জন্য প্রথম নবী।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 27,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীকে 'আবুল আম্বিয়া' বা নবীদের পিতা বলা হয়?",
            options = listOf("হযরত আদম (আঃ)", "হযরত নূহ (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত মুসা (আঃ)"),
            correctOptionIndex = 2,
            explanation = "হযরত ইব্রাহিম (আলাইহিস সালাম)-এর বংশধরদের মধ্য থেকেই পরবর্তী প্রায় সকল সম্মানিত নবী-রাসূল আগমন করেছিলেন বিধায় তাঁকে 'আবুল আম্বিয়া' বলা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 28,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীকে অগ্নিকুণ্ডে নিক্ষেপ করার পর আল্লাহর হুকুমে আগুন ফুলবাগানে পরিণত হয়েছিল?",
            options = listOf("হযরত লুত (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত জাকারিয়া (আঃ)", "হযরত সালেহ (আঃ)"),
            correctOptionIndex = 1,
            explanation = "নমরুদ হযরত ইব্রাহিম (আঃ)-কে আগুনে ফেললে আল্লাহ আগুনকে নির্দেশ দেন: 'হে আগুন! ইব্রাহিমের জন্য শীতল ও শান্তিদায়ক হয়ে যাও' (সূরা আম্বিয়া ৬৯)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 29,
            category = QuizCategory.PROPHETS,
            question = "মহাপ্লাবনের সময় আল্লাহর নির্দেশে বিশাল কিশতী বা নৌকা নির্মাণ করেছিলেন কোন নবী?",
            options = listOf("হযরত নূহ (আঃ)", "হযরত হুদ (আঃ)", "হযরত সালেহ (আঃ)", "হযরত ইউনুস (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত নূহ (আলাইহিস সালাম) সাড়ে নয়শত বছর দীনের দাওয়াত দেন এবং আল্লাহর আদেশে মহাপ্লাবনের জন্য নৌকা প্রস্তুত করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 30,
            category = QuizCategory.PROPHETS,
            question = "কোন নবী মাছের পেটে বন্দি থাকা অবস্থায় 'লা ইলাহা ইল্লা আনতা সুবহানাকা ইন্নি কুনতু মিনাজ জোয়ালিমিন' দোয়া পড়ে মুক্তি পান?",
            options = listOf("হযরত ইউনুস (আঃ)", "হযরত ইয়াকুব (আঃ)", "হযরত শোয়াইব (আঃ)", "হযরত ইলিয়াস (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত ইউনুস (আলাইহিস সালাম) বা যুন-নূন মাছের পেটে দোয়া ইউনুস পাঠ করে আল্লাহর রহমতে মুক্তি লাভ করেছিলেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 31,
            category = QuizCategory.PROPHETS,
            question = "কঠিন রোগব্যাধি ও শারীরিক কষ্টের মধ্যে থেকে অনন্য সবর ও ধৈর্যের প্রতীক হিসেবে স্মরণীয় কোন নবী?",
            options = listOf("হযরত আইয়ুব (আঃ)", "হযরত দাউদ (আঃ)", "হযরত ইউনুস (আঃ)", "হযরত ইদ্রিস (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত আইয়ুব (আলাইহিস সালাম) দীর্ঘ রোগ ও পরীক্ষার মুখেও অটল ধৈর্য ধারণ করেছিলেন। তাঁর সবর প্রবাদতুল্য।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 32,
            category = QuizCategory.PROPHETS,
            question = "কোন নবী পশুপাখির ভাষা বুঝতেন এবং বাতাস ও জিন জাতি তাঁর অধীনস্থ ছিল?",
            options = listOf("হযরত দাউদ (আঃ)", "হযরত সুলাইমান (আঃ)", "হযরত মুসা (আঃ)", "হযরত ঈসা (আঃ)"),
            correctOptionIndex = 1,
            explanation = "আল্লাহ তায়ালা হযরত সুলাইমান (আলাইহিস সালাম)-কে এমন এক অনন্য রাজত্ব দান করেছিলেন যাতে বাতাস, জিন এবং পশুপাখি তাঁর অনুগত ছিল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 33,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীর অলৌকিক হাতের স্পর্শে লোহা মোমের মতো নরম হয়ে যেত এবং যিনি সুরেলা কণ্ঠে জাবুর তিলাওয়াত করতেন?",
            options = listOf("হযরত দাউদ (আঃ)", "হযরত সুলাইমান (আঃ)", "হযরত জাকারিয়া (আঃ)", "হযরত হারুন (আঃ)"),
            correctOptionIndex = 0,
            explanation = "আল্লাহ তায়ালা হযরত দাউদ (আলাইহিস সালাম)-এর জন্য লোহাকে নরম করে দিয়েছিলেন এবং তাঁর সাথে পাহাড়-পর্বত ও পাখিরাও তসবিহ পাঠ করত।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 34,
            category = QuizCategory.PROPHETS,
            question = "আল্লাহর সাথে তূর পাহাড়ে সরাসরি কথা বলার কারণে কোন নবীকে 'কালিমুল্লাহ' উপাধি দেওয়া হয়?",
            options = listOf("হযরত মুসা (আঃ)", "হযরত ইব্রাহিম (আঃ)", "হযরত ঈসা (আঃ)", "হযরত নূহ (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত মুসা (আলাইহিস সালাম) তূর পর্বতে আল্লাহর সাথে সরাসরি কথা বলতেন, তাই তাঁকে 'কালিমুল্লাহ' বলা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 35,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীকে পিতা ছাড়া শুধুমাত্র কুদরতিভাবে কুমারী মাতার গর্ভে জন্ম দেওয়া হয়েছিল?",
            options = listOf("হযরত ইয়াহইয়া (আঃ)", "হযরত ঈসা (আঃ)", "হযরত ইউসুফ (আঃ)", "হযরত ইসমাইল (আঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত ঈসা (আলাইহিস সালাম) কোনো পিতা ছাড়াই আল্লাহর রূহের ফুৎকারে হযরত মরিয়ম (আঃ)-এর গর্ভে অলৌকিকভাবে জন্মগ্রহণ করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 36,
            category = QuizCategory.PROPHETS,
            question = "রূপ-লাবণ্য, সৌন্দর্য এবং কূপে ও কারাগারে নিক্ষিপ্ত হওয়ার পর মিশরের ক্ষমতার অধিকারী হয়েছিলেন কোন নবী?",
            options = listOf("হযরত ইউসুফ (আঃ)", "হযরত ইয়াকুব (আঃ)", "হযরত ইসহাক (আঃ)", "হযরত দাউদ (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত ইউসুফ (আলাইহিস সালাম)-এর জীবনকাহিনিকে পবিত্র কুরআনে 'আহসানুল কাসাস' বা সর্বশ্রেষ্ঠ কাহিনি আখ্যা দেওয়া হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 37,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীর কওমের জন্য পাহাড়ের মধ্য থেকে অলৌকিক গর্ভবতী উটনী নিদর্শন হিসেবে প্রকাশ পেয়েছিল?",
            options = listOf("হযরত সালেহ (আঃ)", "হযরত হুদ (আঃ)", "হযরত শোয়াইব (আঃ)", "হযরত লুত (আঃ)"),
            correctOptionIndex = 0,
            explanation = "সামূদ জাতির জন্য হযরত সালেহ (আলাইহিস সালাম)-এর দোয়ায় পাহাড় ভেদ করে একটি অলৌকিক উটনী প্রকাশ পায়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 38,
            category = QuizCategory.PROPHETS,
            question = "হযরত মুহাম্মদ (সাঃ)-এর পিতা ও মাতার নাম কী ছিল?",
            options = listOf("আব্দুল মুত্তালিব ও হালিমা", "আব্দুল্লাহ ও আমিনা", "আবু তালিব ও ফাতেমা", "হামজা ও খাদিজা"),
            correctOptionIndex = 1,
            explanation = "বিশ্বনবী হযরত মুহাম্মদ (সাঃ)-এর পিতার নাম আব্দুল্লাহ এবং মাতার নাম আমিনা।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 39,
            category = QuizCategory.PROPHETS,
            question = "রাসূলুল্লাহ (সাঃ) কত বছর বয়সে নবুওয়াত লাভ করেছিলেন?",
            options = listOf("৩০ বছর", "৩৫ বছর", "৪০ বছর", "৪৫ বছর"),
            correctOptionIndex = 2,
            explanation = "রাসূলুল্লাহ (সাঃ) ৪০ বছর বয়সে হেরা গুহায় ধ্যানমগ্ন থাকা অবস্থায় প্রথম ওহী লাভের মাধ্যমে নবুওয়াত প্রাপ্ত হন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 40,
            category = QuizCategory.PROPHETS,
            question = "রাসূলুল্লাহ (সাঃ) মিরাজের রাতে আসমানে গমন করে উম্মতের জন্য উপহারস্বরূপ কী নিয়ে এসেছিলেন?",
            options = listOf("রমজানের রোজা", "পাঁচ ওয়াক্ত নামাজ", "জাকাত প্রদান", "হজ পালন"),
            correctOptionIndex = 1,
            explanation = "মিরাজের রজনীতে আল্লাহ তায়ালা উম্মতে মুহাম্মাদীর জন্য প্রতিদিন ৫০ ওয়াক্ত থেকে কমিয়ে ৫ ওয়াক্ত নামাজ উপহার হিসেবে ফরজ করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 41,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীর ওপর তাওরাত কিতাব নাযিল হয়েছিল?",
            options = listOf("হযরত দাউদ (আঃ)", "হযরত মুসা (আঃ)", "হযরত ঈসা (আঃ)", "হযরত ইব্রাহিম (আঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত মুসা (আলাইহিস সালাম)-এর ওপর আসমানী কিতাব তাওরাত নাযিল করা হয়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 42,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীর ওপর ইঞ্জিল কিতাব নাযিল হয়েছিল?",
            options = listOf("হযরত মুসা (আঃ)", "হযরত দাউদ (আঃ)", "হযরত ঈসা (আঃ)", "হযরত ইউনুস (আঃ)"),
            correctOptionIndex = 2,
            explanation = "হযরত ঈসা (আলাইহিস সালাম)-এর ওপর আসমানী কিতাব ইঞ্জিল নাযিল করা হয়েছিল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 43,
            category = QuizCategory.PROPHETS,
            question = "কোন নবীর ওপর জাবুর কিতাব নাযিল হয়েছিল?",
            options = listOf("হযরত দাউদ (আঃ)", "হযরত সুলাইমান (আঃ)", "হযরত হারুন (আঃ)", "হযরত লুত (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত দাউদ (আলাইহিস সালাম)-এর ওপর চার প্রধান আসমানী কিতাবের অন্যতম 'জাবুর' নাযিল হয়েছিল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 44,
            category = QuizCategory.PROPHETS,
            question = "হযরত মুহাম্মদ (সাঃ)-এর দুধমাতা কে ছিলেন যিনি তাঁকে শৈশবে লালন-পালন করেছিলেন?",
            options = listOf("হযরত হালিমা সাদিয়া (রাঃ)", "হযরত ফাতিমা (রাঃ)", "হযরত আসমা (রাঃ)", "হযরত সুমাইয়া (রাঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত হালিমা সাদিয়া (রাদিয়াল্লাহু আনহা) শৈশবে বনু সাদ গোত্রে বিশ্বনবী (সাঃ)-কে দুধপান করান ও লালন-পালন করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 45,
            category = QuizCategory.PROPHETS,
            question = "নবুওয়াত প্রকাশের পূর্বে সততা ও আমানতদারিতার জন্য মক্কাবাসীরা রাসূলুল্লাহ (সাঃ)-কে কী উপাধি দিয়েছিল?",
            options = listOf("আস-সাদিক ও আল-আমিন", "আল-ফারুক", "আস-সাইয়্যেদ", "আল-হাবিব"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ)-এর অসাধারণ সত্যবাদিতা ও আমানতদারির কারণে কুরাইশরা তাঁকে 'আস-সাদিক' (সত্যবাদী) এবং 'আল-আমিন' (বিশ্বস্ত) উপাধিতে ভূষিত করেছিল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 46,
            category = QuizCategory.PROPHETS,
            question = "হযরত ইব্রাহিম (আঃ) আল্লাহর নির্দেশে নিজ পুত্রকে কোরবানি করতে নিয়ে যান, সেই পুত্রের নাম কী?",
            options = listOf("হযরত ইসহাক (আঃ)", "হযরত ইসমাইল (আঃ)", "হযরত ইয়াকুব (আঃ)", "হযরত ইউসুফ (আঃ)"),
            correctOptionIndex = 1,
            explanation = "হযরত ইসমাইল (আলাইহিস সালাম) হাসিমুখে আল্লাহর সন্তুষ্টির জন্য কুরবানির উদ্দেশ্যে নিজেকে সঁপে দিয়েছিলেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 47,
            category = QuizCategory.PROPHETS,
            question = "পবিত্র কাবা ঘর পুনর্নির্মাণ করেছিলেন কোন দুজন সম্মানিত নবী?",
            options = listOf("হযরত ইব্রাহিম (আঃ) ও হযরত ইসমাইল (আঃ)", "হযরত আদম (আঃ) ও শীষ (আঃ)", "হযরত মুসা (আঃ) ও হারুন (আঃ)", "হযরত দাউদ (আঃ) ও সুলাইমান (আঃ)"),
            correctOptionIndex = 0,
            explanation = "পিতা হযরত ইব্রাহিম (আঃ) ও পুত্র হযরত ইসমাইল (আঃ) একসাথে মিলে পবিত্র কাবা ঘরের ভিত্তি পুনর্নির্মাণ করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 48,
            category = QuizCategory.PROPHETS,
            question = "হযরত মুহাম্মদ (সাঃ) কত বছর বেঁচে ছিলেন এবং কত হিজরিতে ওফাত লাভ করেন?",
            options = listOf("৬০ বছর, ৯ হিজরি", "৬৩ বছর, ১১ হিজরি", "৬৫ বছর, ১০ হিজরি", "৬১ বছর, ১২ হিজরি"),
            correctOptionIndex = 1,
            explanation = "বিশ্বনবী হযরত মুহাম্মদ (সাঃ) ৬৩ বছর বয়সে ১১ হিজরির ১২ই রবিউল আউয়াল সোমবার ওফাত লাভ করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 49,
            category = QuizCategory.PROPHETS,
            question = "হযরত মুসা (আঃ)-এর আপন ভাই এবং তাঁর সাহায্যকারী হিসেবে নবুওয়াত প্রাপ্ত নবীর নাম কী?",
            options = listOf("হযরত হারুন (আঃ)", "হযরত ইউশা (আঃ)", "হযরত খিজির (আঃ)", "হযরত শোয়াইব (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত হারুন (আলাইহিস সালাম) ছিলেন হযরত মুসা (আঃ)-এর সহোদর ভাই এবং তাঁর দোয়ায় আল্লাহ তাঁকে নবী মনোনীত করেছিলেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 50,
            category = QuizCategory.PROPHETS,
            question = "বৃদ্ধ বয়সে নিঃসন্তান অবস্থায় দোয়ার পর হযরত জাকারিয়া (আঃ)-কে আল্লাহ কোন নবীর সুসংবাদ দেন?",
            options = listOf("হযরত ইয়াহইয়া (আঃ)", "হযরত ইউনুস (আঃ)", "হযরত ইলিয়াস (আঃ)", "হযরত আইয়ুব (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত জাকারিয়া (আলাইহিস সালাম)-এর গভীর প্রার্থনার প্রেক্ষিতে আল্লাহ তাঁকে হযরত ইয়াহইয়া (আঃ)-এর জন্মের সুসংবাদ দেন।",
            difficulty = QuizDifficulty.MEDIUM
        )
    )
}
