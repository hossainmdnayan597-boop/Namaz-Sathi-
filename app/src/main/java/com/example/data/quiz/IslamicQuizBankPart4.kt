package com.example.data.quiz

object IslamicQuizBankPart4 {

    val questions: List<QuizQuestion> = listOf(
        // ==================== সাধারণ জ্ঞান, আকীদা ও শিষ্টাচার (১৫১ - ২০০) ====================
        QuizQuestion(
            id = 151,
            category = QuizCategory.GENERAL,
            question = "ঈমানের মৌলিক রুকন বা ভিত্তি সর্বমোট কয়টি বিষয়ের ওপর বিশ্বাস স্থাপন করা?",
            options = listOf("৪টি", "৫টি", "৬টি", "৭টি"),
            correctOptionIndex = 2,
            explanation = "হাদিসে জিবরাইল অনুসারে ঈমানের রুকন ৬টি: ১. আল্লাহ, ২. ফেরেশতাগণ, ৩. আসমানী কিতাবসমূহ, ৪. রাসূলগণ, ৫. শেষ দিবস (পরকাল), এবং ৬. তাকদীর বা ভাগ্যের ভালো-মন্দের ওপর বিশ্বাস।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 152,
            category = QuizCategory.GENERAL,
            question = "আল্লাহ তায়ালার পক্ষ থেকে নবীদের কাছে ওহী বা আসমানী বাণী পৌঁছে দেওয়ার দায়িত্বে নিয়োজিত ছিলেন কোন প্রধান ফেরেশতা?",
            options = listOf("হযরত জিবরাইল (আঃ)", "হযরত মিকাইল (আঃ)", "হযরত ইসরাফিল (আঃ)", "হযরত আজরাইল (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত জিবরাইল (আলাইহিস সালাম) হলেন প্রধান ফেরেশতা এবং ওহীর বার্তাবাহক (রুহুল কুদুস ও রুহুল আমিন)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 153,
            category = QuizCategory.GENERAL,
            question = "বৃষ্টিবর্ষণ, মেঘমালা পরিচালনা এবং সৃষ্টির জীবিকা (রিযিক) বণ্টনের দায়িত্বে নিয়োজিত সম্মানিত ফেরেশতা কে?",
            options = listOf("হযরত মিকাইল (আঃ)", "হযরত ইসরাফিল (আঃ)", "হযরত জিবরাইল (আঃ)", "হযরত মালিক (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত মিকাইল (আলাইহিস সালাম) আল্লাহর নির্দেশে প্রকৃতি, বৃষ্টিপাত এবং মাখলুকের রিযিক বণ্টনের কার্যক্রমে দায়িত্বশীল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 154,
            category = QuizCategory.GENERAL,
            question = "কেয়ামত সংঘটিত করতে এবং পুনরায় জীবিত করতে শিঙ্গায় ফুৎকার দেওয়ার দায়িত্বে কোন ফেরেশতা নিয়োজিত আছেন?",
            options = listOf("হযরত ইসরাফিল (আঃ)", "হযরত আজরাইল (আঃ)", "হযরত মিকাইল (আঃ)", "হযরত রিদওয়ান (আঃ)"),
            correctOptionIndex = 0,
            explanation = "হযরত ইসরাফিল (আলাইহিস সালাম) মুখে শিঙ্গা নিয়ে আল্লাহর আদেশের অপেক্ষায় প্রস্তুত রয়েছেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 155,
            category = QuizCategory.GENERAL,
            question = "জীবকুলের রূহ বা প্রাণ কবজ করার দায়িত্বপ্রাপ্ত ফেরেশতাকে কুরআনে কী নামে উল্লেখ করা হয়েছে?",
            options = listOf("মালাকুল মাউত (মৃত্যুর ফেরেশতা)", "হযরত হারুত", "হযরত মারুত", "হযরত রিদওয়ান"),
            correctOptionIndex = 0,
            explanation = "পবিত্র কুরআনে তাঁকে 'মালাকুল মাউত' (মৃত্যুর দূত) হিসেবে সম্বোধন করা হয়েছে, যিনি আল্লাহর আদেশে রূহ কবজ করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 156,
            category = QuizCategory.GENERAL,
            question = "কবরে মৃত ব্যক্তিকে তিনটি মৌলিক প্রশ্ন করার জন্য কোন দুজন ফেরেশতা আগমন করেন?",
            options = listOf("মুনকার ও নাকীর", "হারুত ও মারুত", "রাকিব ও আতিদ", "মালিক ও রিদওয়ান"),
            correctOptionIndex = 0,
            explanation = "কবরে বান্দাকে 'তোমার রব কে?', 'তোমার দীন কি?' এবং 'নবী সম্পর্কে কি ধারণা?' প্রশ্ন করার জন্য মুনকার ও নাকীর আগমন করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 157,
            category = QuizCategory.GENERAL,
            question = "মানুষের ভালো ও মন্দ আমল সর্বদা লিপিবদ্ধ করার দায়িত্বে নিয়োজিত দুই ফেরেশতাকে কী বলা হয়?",
            options = listOf("কিরামান কাতিবিন (সম্মানিত লেখকবৃন্দ)", "মুনকার-নাকীর", "হামালাতুল আরশ", "মুকাররাবুন"),
            correctOptionIndex = 0,
            explanation = "সূরা ক্বাফ ও সূরা ইনফিতারে মানুষের দুই কাঁধে আমল সংরক্ষণকারী ফেরেশতাদের 'কিরামান কাতিবিন' বলা হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 158,
            category = QuizCategory.GENERAL,
            question = "জান্নাতের প্রধান প্রহরী ও তত্ত্বাবধায়ক ফেরেশতার নাম কী?",
            options = listOf("হযরত রিদওয়ান (আঃ)", "হযরত মালিক (আঃ)", "হযরত ইসরাফিল (আঃ)", "হযরত জিবরাইল (আঃ)"),
            correctOptionIndex = 0,
            explanation = "জান্নাতের দ্বারে দায়িত্বশীল প্রধান ফেরেশতার নাম হযরত রিদওয়ান (আলাইহিস সালাম)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 159,
            category = QuizCategory.GENERAL,
            question = "জাহান্নামের প্রধান প্রহরী ও পরিচালক ফেরেশতার নাম কুরআনে কী এসেছে?",
            options = listOf("মালিক", "রিদওয়ান", "আজরাইল", "মিকাইল"),
            correctOptionIndex = 0,
            explanation = "পবিত্র কুরআনে (সূরা যুখরুফ ৭৭) জাহান্নামের দারোগা প্রধান ফেরেশতাকে 'মালিক' নামে সম্বোধন করা হয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 160,
            category = QuizCategory.GENERAL,
            question = "ইসলামে ক্ষমার অযোগ্য এবং সবচেয়ে ভয়াবহ কবিরা গুনাহ কোনটি, যার ওপর তওবা ছাড়া মৃত্যু হলে চিরস্থায়ী জাহান্নামী হতে হয়?",
            options = listOf("শিরক (আল্লাহর সাথে শরিক করা)", "চুরি করা", "মিথ্যা বলা", "অপচয় করা"),
            correctOptionIndex = 0,
            explanation = "আল্লাহ ইরশাদ করেছেন: নিশ্চয় আল্লাহ তাঁর সাথে শরিক করার অপরাধ ক্ষমা করেন না, এ ছাড়া অন্য যে কোনো গুনাহ যাকে ইচ্ছা ক্ষমা করেন (সূরা নিসা ৪৮)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 161,
            category = QuizCategory.GENERAL,
            question = "জান্নাতের মোট কয়টি দরজা রয়েছে?",
            options = listOf("৫টি", "৭টি", "৮টি", "১২টি"),
            correctOptionIndex = 2,
            explanation = "সহিহ হাদিস অনুযায়ী জান্নাতের মোট ৮টি দরজা রয়েছে (যার মধ্যে অন্যতম 'বাবুর্ রাইয়ান' শুধু রোজাদারদের জন্য)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 162,
            category = QuizCategory.GENERAL,
            question = "জান্নাতের কোন বিশেষ দরজা দিয়ে কেয়ামতের দিন শুধুমাত্র রোজাদার ব্যক্তিরা প্রবেশ করার সম্মান পাবে?",
            options = listOf("বাবুর্ রাইয়ান", "বাবুস সালাত", "বাবুল জিহাদ", "বাবুস সদকা"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: জান্নাতে একটি দরজা আছে যার নাম 'আর-রাইয়ান', কিয়ামতের দিন সেখান দিয়ে কেবল রোজাদাররাই প্রবেশ করবে (সহিহ বুখারী ১৮৯৬)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 163,
            category = QuizCategory.GENERAL,
            question = "জাহান্নামের মোট কয়টি স্তর বা দরজা রয়েছে বলে পবিত্র কুরআনে বর্ণিত হয়েছে?",
            options = listOf("৫টি", "৬টি", "৭টি", "৮টি"),
            correctOptionIndex = 2,
            explanation = "সূরা আল-হিজরের ৪৪ নম্বর আয়াতে উল্লেখ রয়েছে যে, জাহান্নামের মোট ৭টি স্তর ও দরজা রয়েছে।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 164,
            category = QuizCategory.GENERAL,
            question = "কেয়ামতের দিন জাহান্নামের ওপর স্থাপিত অত্যন্ত সূক্ষ্ম ও ধারালো সেতুর নাম কী যার ওপর দিয়ে সবাইকে পার হতে হবে?",
            options = listOf("সিরাত (পুলসিরাত)", "মীযান", "কাউসার", "বারযাখ"),
            correctOptionIndex = 0,
            explanation = "পুলসিরাত হলো জাহান্নামের ওপর পাতা সেতু, যা চুলের চেয়েও সূক্ষ্ম ও তলোয়ারের চেয়েও ধারালো হবে। নেককাররা বিদ্যুতের গতিতে তা পার হয়ে জান্নাতে যাবে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 165,
            category = QuizCategory.GENERAL,
            question = "কেয়ামতের দিন মানুষের নেক ও বদ আমল ওজনের জন্য যে ন্যায়দণ্ড স্থাপিত হবে তাকে কী বলে?",
            options = listOf("মীযান (আমলের পাল্লা)", "লওহে মাহফুজ", "কুরসী", "আরশ"),
            correctOptionIndex = 0,
            explanation = "মীযান হলো কেয়ামতের ময়দানে আমল পরিমাপের পরম ন্যায়বিচারের দাঁড়িপাল্লা।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 166,
            category = QuizCategory.GENERAL,
            question = "কেয়ামতের ভয়াবহ তৃষ্ণার সময় রাসূলুল্লাহ (সাঃ) তাঁর উম্মতকে যে হাউজের সুমিষ্ট পানি পান করাবেন তার নাম কী?",
            options = listOf("হাউজে কাউসার", "জমজম কূপ", "সালসাবিল", "তাসনীম"),
            correctOptionIndex = 0,
            explanation = "হাউজে কাউসারের পানি দুধের চেয়েও সাদা এবং মধুর চেয়েও মিষ্টি, যা একবার পান করলে আর কখনো পিপাসা লাগবে না।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 167,
            category = QuizCategory.GENERAL,
            question = "ইসলামিক হিজরি বর্ষে সর্বমোট কতটি সম্মানিত ও পবিত্র মাস (আশহুরুল হুরুম) রয়েছে যাতে যুদ্ধবিগ্রহ নিষিদ্ধ?",
            options = listOf("২টি", "৩টি", "৪টি", "৬টি"),
            correctOptionIndex = 2,
            explanation = "পবিত্র মাস ৪টি: জিলকদ, জিলহজ, মহররম এবং রজব (সূরা আত-তাওবা ৩৬)।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 168,
            category = QuizCategory.GENERAL,
            question = "হিজরি সনের প্রথম বা প্রারম্ভিক মাসের নাম কী?",
            options = listOf("রমজান", "মহররম", "রবিউল আউয়াল", "শাবান"),
            correctOptionIndex = 1,
            explanation = "ইসলামিক চান্দ্র হিজরি বর্ষের প্রথম মাস হলো 'মহররম'।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 169,
            category = QuizCategory.GENERAL,
            question = "ইসলামিক অভিবাদন বা সালামের পূর্ণাঙ্গ বাক্যটির অর্থ কী?",
            options = listOf("আপনার ওপর শান্তি, আল্লাহর রহমত ও তাঁর বরকত বর্ষিত হোক", "সুপ্রভাত ও শুভকামনা", "দীর্ঘজীবী হোন", "আল্লাহ আপনাকে ক্ষমা করুন"),
            correctOptionIndex = 0,
            explanation = "'আস-সালামু আলাইকুম ওয়া রাহমাতুল্লাহি ওয়া বারাকাতুহু' অর্থ আপনার ওপর আল্লাহর শান্তি, রহমত ও বরকত বর্ষিত হোক।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 170,
            category = QuizCategory.GENERAL,
            question = "হাঁচি দেওয়ার পর সুন্নাহ অনুযায়ী হাঁচিদাতা কী বাক্য পাঠ করবে?",
            options = listOf("আলহামদুলিল্লাহ (সকল প্রশংসা আল্লাহর)", "সুবহানাল্লাহ", "আল্লাহু আকবার", "আসতাগফিরুল্লাহ"),
            correctOptionIndex = 0,
            explanation = "হাঁচি দিলে 'আলহামদুলিল্লাহ' বলা সুন্নত, আর শ্রবণকারী জবাবে 'ইয়ারহামুকাল্লাহ' বলবে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 171,
            category = QuizCategory.GENERAL,
            question = "ভবিষ্যতে কোনো কাজ করার অঙ্গীকার বা সংকল্প প্রকাশে কোন বাক্যটি উচ্চারণ করা ঈমানী শিষ্টাচার?",
            options = listOf("ইনশাআল্লাহ (যদি আল্লাহ চান)", "মাশাআল্লাহ", "সুবহানাল্লাহ", "জাযাকাল্লাহ"),
            correctOptionIndex = 0,
            explanation = "ভবিষ্যতের যেকোনো কাজের ক্ষেত্রে 'ইনশাআল্লাহ' (আল্লাহ যদি ইচ্ছা করেন) বলা পবিত্র কুরআনের স্পষ্ট নির্দেশ (সূরা কাহাফ ২৩-২৪)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 172,
            category = QuizCategory.GENERAL,
            question = "কারো সুন্দর কিছু দেখে বা নিজের কোনো অর্জনে কুদৃষ্টি থেকে হেফাজতের জন্য কী বাক্য বলা হয়?",
            options = listOf("মাশাআল্লাহ তাবারাকাল্লাহ", "নাউযুবিল্লাহ", "হাসবুনাল্লাহ", "ইন্না লিল্লাহ"),
            correctOptionIndex = 0,
            explanation = "'মাশাআল্লাহ' (আল্লাহ যা চেয়েছেন) বাক্যটি প্রশংসনীয় দৃশ্য দেখে বললে নজর লাগা থেকে রক্ষা পাওয়া যায়।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 173,
            category = QuizCategory.GENERAL,
            question = "কারো কোনো উপকার বা অনুগ্রহের প্রতিদানস্বরূপ কৃতজ্ঞতা জানাতে সুন্নাহসম্মত শ্রেষ্ঠ দোয়া কোনটি?",
            options = listOf("জাযাকাল্লাহু খাইরান (আল্লাহ আপনাকে উত্তম প্রতিদান দিন)", "ধন্যবাদ বন্ধু", "সালামু আলাইকুম", "মারহাবা"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: কেউ উপকারীর জন্য 'জাযাকাল্লাহু খাইরান' বললে সে প্রশংসার চরম পরাকাষ্ঠা প্রদর্শন করল (তিরমিযী ২০৩৫)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 174,
            category = QuizCategory.GENERAL,
            question = "কোনো বিপদ-আপদ বা মৃত্যুর সংবাদ শ্রবণ করলে কুরআন অনুযায়ী সর্বপ্রথম কী পাঠ করতে হয়?",
            options = listOf("ইন্না লিল্লাহি ওয়া ইন্না ইলাইহি রাজিঊন", "লা হাওলা ওয়ালা কুওয়াতা", "হাসবিয়াল্লাহ", "সুবহানাল্লাহ"),
            correctOptionIndex = 0,
            explanation = "'নিশ্চয় আমরা আল্লাহর জন্য এবং আমরা তাঁরই দিকে প্রত্যাবর্তনকারী' (সূরা বাকারা ১৫৬)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 175,
            category = QuizCategory.GENERAL,
            question = "রাসূলুল্লাহ (সাঃ) বলেছেন: 'যে ব্যক্তি ইচ্ছা করে মানুষের পেছনে তার অপছন্দনীয় কথা আলোচনা করে', এই ঘৃণ্য পাপকে কী বলে?",
            options = listOf("গীবত (পরনিন্দা)", "বুহতান (অপবাদ)", "নামিমাহ (চোগলখুরি)", "কিবর (অহংকার)"),
            correctOptionIndex = 0,
            explanation = "কুরআনে গীবতকে আপন মৃত ভাইয়ের গোশত ভক্ষণ করার সাথে তুলনা করে চরমভাবে ধিক্কার জানানো হয়েছে (সূরা হুজুরাত ১২)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 176,
            category = QuizCategory.GENERAL,
            question = "রাসূলুল্লাহ (সাঃ) কাকে 'জাহান্নামের আগুন যেমন কাঠ পুড়িয়ে ছাই করে দেয়, তেমনি নেক আমল ধ্বংসকারী' বলে আখ্যা দিয়েছেন?",
            options = listOf("হাসাদ (হিংসা-বিদ্বেষ)", "রাগ", "অহংকার", "কৃপণতা"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) সতর্ক করেছেন: তোমরা হিংসা থেকে দূরে থাকো, কেননা হিংসা নেক আমলসমূহকে এমনভাবে গ্রাস করে যেমন আগুন শুকনো কাঠকে গ্রাস করে (আবু দাউদ ৪৯০৩)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 177,
            category = QuizCategory.GENERAL,
            question = "রাসূলুল্লাহ (সাঃ)-এর হাদিস অনুসারে পিতা-মাতার প্রতি সদাচরণের গুরুত্বে মায়ের স্থান বাবার চেয়ে কত গুণ বেশি অগ্রাধিকারপ্রাপ্ত?",
            options = listOf("সমান সমান", "দুই গুণ", "তিন গুণ", "দশ গুণ"),
            correctOptionIndex = 2,
            explanation = "সাহাবী জিজ্ঞেস করলেন: কার সাথে সবচেয়ে উত্তম ব্যবহার করব? নবীজী তিনবার বললেন 'তোমার মা', চতুর্থবারে বললেন 'তোমার বাবা' (সহিহ বুখারী ৫৯৭১)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 178,
            category = QuizCategory.GENERAL,
            question = "ইসলামে খাদ্য ও পানীয়ের ক্ষেত্রে কোন প্রাণীর গোশত সম্পূর্ণ চিরতরে হারাম ও অপবিত্র ঘোষণা করা হয়েছে?",
            options = listOf("শূকর বা বন্য বরাহ", "উট", "হরিণ", "দুম্বা"),
            correctOptionIndex = 0,
            explanation = "পবিত্র কুরআনে আল্লাহ শূকরের গোশত (লাহমুল খিনযির) খাওয়া সুস্পষ্টভাবে হারাম করেছেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 179,
            category = QuizCategory.GENERAL,
            question = "মদ ও যাবতীয় মাদকদ্রব্য সম্পর্কে ইসলামের চূড়ান্ত বিধান কী?",
            options = listOf("সম্পূর্ণ হারাম ও শয়তানের অপবিত্র কাজ", "অল্প পান করা বৈধ", "কেবল দিনে হারাম", "মাকরুহ"),
            correctOptionIndex = 0,
            explanation = "সূরা মায়িদার ৯০ নম্বর আয়াতে মদ ও জুয়াকে শয়তানের চরম অপবিত্র কর্ম বলে তা থেকে সম্পূর্ণ বিরত থাকার নির্দেশ দেওয়া হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 180,
            category = QuizCategory.GENERAL,
            question = "অর্থনৈতিক লেনদেনে অতিরিক্ত ধার্যকৃত 'সুদ' (রিবা) গ্রহণের বিষয়ে কুরআনের ঘোষণা কী?",
            options = listOf("আল্লাহ ও তাঁর রাসূলের বিরুদ্ধে সরাসরি যুদ্ধ ঘোষণার সমতুল্য", "ছোট গুনাহ", "মাকরুহ তানজিহি", "সাধারণ অন্যায়"),
            correctOptionIndex = 0,
            explanation = "সূরা বাকারার ২৭৯ নম্বর আয়াতে সুদী কারবারীদের বিরুদ্ধে আল্লাহ ও তাঁর রাসূলের পক্ষ থেকে যুদ্ধের চরম হুঁশিয়ারি দেওয়া হয়েছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 181,
            category = QuizCategory.GENERAL,
            question = "মুসলমানদের সাপ্তাহিক শ্রেষ্ঠ দিন বা 'সাইয়্যিদুল আইয়াম' (দিনসমূহের সর্দার) কোন দিনটিকে বলা হয়?",
            options = listOf("শুক্রবার (জুমু'আবার)", "সোমবার", "বৃহস্পতিবার", "রবিবার"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: দিনসমূহের মধ্যে সর্বোত্তম দিন হলো জুমার দিন, এই দিনে আদম (আঃ) সৃষ্টি হন এবং এই দিনেই কেয়ামত অনুষ্ঠিত হবে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 182,
            category = QuizCategory.GENERAL,
            question = "পবিত্র কাবা শরীফ কোন শহরে অবস্থিত এবং এটি কোন দেশের অন্তর্ভুক্ত?",
            options = listOf("মক্কাতুল মুকাররমা, সৌদি আরব", "মদিনা মুনাওয়ারা", "জেরুজালেম", "দামেস্ক"),
            correctOptionIndex = 0,
            explanation = "পবিত্র কাবা বাইতুল্লাহ সৌদি আরবের পবিত্র মক্কা নগরীতে অবস্থিত এবং এটি বিশ্ব মুসলিমের ঐক্যের কিবলা।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 183,
            category = QuizCategory.GENERAL,
            question = "ইসলামের দ্বিতীয় পবিত্রতম শহর ও মসজিদে নববী কোন পুণ্যভূমিতে অবস্থিত?",
            options = listOf("মদিনা মুনাওয়ারা", "মক্কা", "রিয়াদ", "জিদ্দা"),
            correctOptionIndex = 0,
            explanation = "মদিনা মুনাওয়ারা হলো রাসূলুল্লাহ (সাঃ)-এর হিজরতের শহর যেখানে মসজিদে নববী ও রওজা মোবারক বিদ্যমান।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 184,
            category = QuizCategory.GENERAL,
            question = "ইসলামের তৃতীয় পবিত্রতম মসজিদ 'মসজিদুল আকসা' (বায়তুল মুকাদ্দাস) কোন শহরে অবস্থিত?",
            options = listOf("জেরুজালেম (আল-কুদস), ফিলিস্তিন", "মদিনা", "কায়রো", "বাগদাদ"),
            correctOptionIndex = 0,
            explanation = "মসজিদুল আকসা ফিলিস্তিনের প্রাচীন জেরুজালেম শহরে অবস্থিত এবং এটি ইসলামের আদি প্রথম কিবলা ছিল।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 185,
            category = QuizCategory.GENERAL,
            question = "মসজিদে নববীতে রাসূল (সাঃ)-এর রওজা মোবারক এবং মিম্বরের মধ্যবর্তী বরকতময় স্থানকে কী বলা হয়?",
            options = listOf("রিয়াদুল জান্নাহ (জান্নাতের বাগান)", "মাকামে ইব্রাহিম", "হিজরে ইসমাইল", "মুলতাযাম"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) বলেছেন: আমার ঘর ও আমার মিম্বারের মধ্যবর্তী স্থানটি জান্নাতের বাগানসমূহের একটি বাগান (সহিহ বুখারী ১১৯৫)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 186,
            category = QuizCategory.GENERAL,
            question = "পবিত্র কাবা শরীফের দেওয়ালে স্থাপিত বেহেশতি পাথরটির নাম কী যা জান্নাত থেকে অবতীর্ণ হয়েছিল?",
            options = listOf("হাজরে আসওয়াদ (কালো পাথর)", "মাকামে ইব্রাহিম", "যমযম পাথর", "মারওয়া"),
            correctOptionIndex = 0,
            explanation = "হাজরে আসওয়াদ মূলত দুধের চেয়েও সাদা ছিল, কিন্তু মানবজাতির পাপের স্পর্শে তা কালো বর্ণ ধারণ করেছে।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 187,
            category = QuizCategory.GENERAL,
            question = "হযরত ইসমাইল (আঃ)-এর পায়ের গোড়ালির অলৌকিক আঘাতে মরুভূমিতে প্রবাহিত বরকতময় কূপটির নাম কী?",
            options = listOf("যমযম কূপ", "কাউসার কূপ", "আইনে রুমাহ", "বিরে আলী"),
            correctOptionIndex = 0,
            explanation = "হযরত হাজেরা (আঃ) ও শিশু ইসমাইল (আঃ)-এর জন্য আল্লাহ মরুভূমিতে সুমিষ্ট ও শেফাময় যমযম কূপের ধারা প্রবাহিত করেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 188,
            category = QuizCategory.GENERAL,
            question = "রাসূলুল্লাহ (সাঃ) জবান ও জিহ্বার হেফাজতের বিষয়ে কী প্রতিশ্রুতি দিয়েছেন?",
            options = listOf("যে দুই চোয়ালের মধ্যবর্তী বস্তু ও লজ্জাস্থান রক্ষা করবে, আমি তার জান্নাতের জামিন হব", "তাকে সম্পদশালী করব", "তাকে দীর্ঘায়ু দেব", "তাকে শাসক বানাব"),
            correctOptionIndex = 0,
            explanation = "সহিহ বুখারীর হাদিসে রাসূলুল্লাহ (সাঃ) মুখ ও লজ্জাস্থান হেফাজতকারীর জন্য জান্নাতের গ্যারান্টি দিয়েছেন।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 189,
            category = QuizCategory.GENERAL,
            question = "পবিত্র কুরআনে বর্ণিত কোন রাতের ইবাদত এক হাজার মাসের (৮৩ বছর ৪ মাস) ইবাদতের চেয়েও শ্রেষ্ঠ?",
            options = listOf("লাইলাতুল কদর", "শবে মেরাজ", "শবে বরাত", "আশুরার রাত"),
            correctOptionIndex = 0,
            explanation = "সূরা আল-কদরে আল্লাহ ঘোষণা করেছেন: 'লাইলাতুল কাদরি খাইরুম মিন আলফি শাহর' অর্থাৎ কদরের রাত হাজার মাসের চেয়েও শ্রেষ্ঠ।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 190,
            category = QuizCategory.GENERAL,
            question = "একজন মুসলমানের ওপর অপর মুসলমানের কয়টি আবশ্যিক হক বা অধিকার রয়েছে?",
            options = listOf("৩টি", "৫টি", "৬টি", "১০টি"),
            correctOptionIndex = 2,
            explanation = "সহিহ মুসলিমের হাদিস অনুসারে হক ৬টি: সালামের জবাব দেওয়া, দাওয়াত কবুল করা, উপদেশ চাইলে সদুপদেশ দেওয়া, হাঁচির জবাব দেওয়া, অসুস্থ হলে দেখতে যাওয়া এবং মৃত্যুবরণ করলে জানাযায় শরিক হওয়া।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 191,
            category = QuizCategory.GENERAL,
            question = "রাসূলুল্লাহ (সাঃ) বলেছেন: 'যে ব্যক্তি পথ থেকে কষ্টদায়ক বস্তু অপসারণ করে', এটি কিসের অংশ?",
            options = listOf("ঈমানের একটি গুরুত্বপূর্ণ শাখা", "হজের অংশ", "কেবল সমাজসেবা", "ফরজ কাজ"),
            correctOptionIndex = 0,
            explanation = "ঈমানের ৭০টির বেশি শাখা রয়েছে; সর্বোচ্চ শাখা 'লা ইলাহা ইল্লাল্লাহ' এবং সর্বনিম্ন শাখা হলো রাস্তা থেকে ক্ষতিকর বস্তু সরানো (সহিহ মুসলিম ৩৫)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 192,
            category = QuizCategory.GENERAL,
            question = "ইসলামে লজ্জা বা লজ্জাশীলতা (হায়া) সম্পর্কে রাসূলুল্লাহ (সাঃ) কী মন্তব্য করেছেন?",
            options = listOf("লজ্জাশীলতা ঈমানের বিশেষ অঙ্গ", "এটি দুর্বলতা", "এটি অপ্রয়োজনীয়", "এটি নারীসুলভ গুণ কেবল"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: 'আল-হায়াউ মিনাল ঈমান' — লজ্জাশীলতা ঈমানেরই অপরিহার্য অঙ্গ (সহিহ বুখারী ৯)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 193,
            category = QuizCategory.GENERAL,
            question = "মৃত ব্যক্তির সম্পদ জীবিত ওয়ারিশদের মাঝে বণ্টন করার বিজ্ঞানসম্মত ইসলামিক শাস্ত্রকে কী বলা হয়?",
            options = listOf("ইলমুল ফারায়েজ (উত্তরাধিকার আইন)", "ইলমুল কালাম", "ইলমুত তাফসির", "ইলমুল বালাগাত"),
            correctOptionIndex = 0,
            explanation = "পবিত্র কুরআন ও সুন্নাহর আলোকে আত্মীয়-স্বজনদের মাঝে উত্তরাধিকার সম্পত্তি বণ্টনের নির্ধারিত জ্ঞানকে ইলমুল ফারায়েজ বলা হয়।",
            difficulty = QuizDifficulty.MEDIUM
        ),
        QuizQuestion(
            id = 194,
            category = QuizCategory.GENERAL,
            question = "মানুষ মারা যাওয়ার পরও তিনটি আমলের সওয়াব কবরে অবিরত পৌঁছাতে থাকে, তার মধ্যে কোনটি অন্তর্ভুক্ত?",
            options = listOf("সদকায়ে জারিয়া, উপকারী ইলম ও নেক সন্তানের দোয়া", "ব্যবসায়ী সুনাম", "বংশের আভিজাত্য", "ব্যাংকের জমানো টাকা"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) বলেন: মানুষ মারা গেলে তার সমস্ত আমল বন্ধ হয়ে যায় তিনটি ব্যতীত — সদকায়ে জারিয়া, ফলপ্রসূ জ্ঞান ও নেক সন্তান যে তার জন্য দোয়া করে (সহিহ মুসলিম ১৬৩১)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 195,
            category = QuizCategory.GENERAL,
            question = "রাসূলুল্লাহ (সাঃ)-এর ওপর একবার দরূদ পাঠ করলে আল্লাহ তায়ালা বান্দার প্রতি কয়টি রহমত বর্ষণ করেন?",
            options = listOf("১টি", "৫টি", "১০টি রহমত এবং ১০টি গুনাহ মাফ হয়", "৭০টি"),
            correctOptionIndex = 2,
            explanation = "হাদিসে এসেছে: যে ব্যক্তি আমার ওপর একবার দরূদ পাঠ করে, আল্লাহ তার ওপর ১০টি রহমত বর্ষণ করেন, তার ১০টি পাপ মোচন করেন এবং ১০টি মর্যাদা বৃদ্ধি করেন (নাসাঈ ১২৯৭)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 196,
            category = QuizCategory.GENERAL,
            question = "আল্লাহ তায়ালার সুন্দর ও গুণবাচক মোট কতটি নাম (আসমাউল হুসনা) হাদিসে সুসংবাদসহ বিশেষভাবে বর্ণিত হয়েছে?",
            options = listOf("৪০টি", "৭০টি", "৯৯টি", "১০১টি"),
            correctOptionIndex = 2,
            explanation = "রাসূলুল্লাহ (সাঃ) বলেছেন: আল্লাহর ৯৯টি গুণবাচক নাম রয়েছে, যে ব্যক্তি তা মুখস্থ করবে ও অনুধাবন করবে সে জান্নাতে প্রবেশ করবে (সহিহ বুখারী ২৭৩৬)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 197,
            category = QuizCategory.GENERAL,
            question = "খাবারের পাত্রে ফুঁ দেওয়া এবং দাঁড়িয়ে এক নিঃশ্বাসে পানি পান করার বিষয়ে সুন্নাহর নির্দেশনা কী?",
            options = listOf("নিষেধ ও নিরুৎসাহিত করা হয়েছে (বসে তিন নিঃশ্বাসে পান করা সুন্নত)", "উৎসাহিত করা হয়েছে", "ফরজ করা হয়েছে", "ওয়াজিব"),
            correctOptionIndex = 0,
            explanation = "খাবার ও পানীয়তে ফুঁ দেওয়া নিষেধ এবং বসে ডান হাতে তিন শ্বাসে বিসমিল্লাহ বলে পানি পান করা সুন্নাত।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 198,
            category = QuizCategory.GENERAL,
            question = "প্রতিবেশীর অধিকার সম্পর্কে জিবরাইল (আঃ) রাসূলুল্লাহ (সাঃ)-কে এত বেশি তাগিদ দিয়েছিলেন যে তিনি কী মনে করেছিলেন?",
            options = listOf("প্রতিবেশীকে হয়তো উত্তরাধিকারী বা ওয়ারিশ বানিয়ে দেওয়া হবে", "প্রতিবেশীর সব কাজ করে দিতে হবে", "প্রতিবেশীকে ঘরে স্থান দিতে হবে", "প্রতিবেশীর কাছে ক্ষমা চাইতে হবে"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) বলেন: জিবরাইল আমাকে প্রতিবেশীর হকের ব্যাপারে এত তাগিদ দিচ্ছিলেন যে আমার ধারণা হচ্ছিল হয়তো তাকে ওয়ারিশ বানিয়ে দেওয়া হবে (সহিহ বুখারী ৬০১৪)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 199,
            category = QuizCategory.GENERAL,
            question = "মিথ্যা বলা এবং প্রতিশ্রুতি ভঙ্গ করা কার সুস্পষ্ট চারিত্রিক আলামত বা বৈশিষ্ট্য?",
            options = listOf("মুনাফিকের আলামত", "মুমিনের বৈশিষ্ট্য", "আলেমের লক্ষণ", "মুত্তাকির স্বভাব"),
            correctOptionIndex = 0,
            explanation = "রাসূলুল্লাহ (সাঃ) ইরশাদ করেছেন: মুনাফিকের নিদর্শন তিনটি — কথা বললে মিথ্যা বলে, অঙ্গীকার করলে ভঙ্গ করে এবং আমানত রাখলে খিয়ানত করে (সহিহ বুখারী ৩৩)।",
            difficulty = QuizDifficulty.EASY
        ),
        QuizQuestion(
            id = 200,
            category = QuizCategory.GENERAL,
            question = "মহান আল্লাহ তায়ালার কাছে বান্দার পক্ষ থেকে সবচেয়ে প্রিয় দুটি বাক্য কী যা উচ্চারণে হালকা কিন্তু মীযানের পাল্লায় অনেক ভারী?",
            options = listOf("সুবহানাল্লাহি ওয়া বিহামদিহী, সুবহানাল্লাহিল আজীম", "লা ইলাহা ইল্লাল্লাহ মুহাম্মাদুর রাসুলুল্লাহ", "আল্লাহু আকবার কাবীরা", "লা হাওলা ওয়ালা কুওয়াতা ইল্লা বিল্লাহ"),
            correctOptionIndex = 0,
            explanation = "সহিহ বুখারীর সর্বশেষ হাদিসে এসেছে: দুটি বাক্য জবানে অত্যন্ত সহজ কিন্তু পাল্লায় অতি ভারী এবং পরম করুণাময়ের কাছে অতি প্রিয় — 'সুবহানাল্লাহি ওয়া বিহামদিহী, সুবহানাল্লাহিল আজীম' (বুখারী ৭৫৬৩)।",
            difficulty = QuizDifficulty.EASY
        )
    )
}
