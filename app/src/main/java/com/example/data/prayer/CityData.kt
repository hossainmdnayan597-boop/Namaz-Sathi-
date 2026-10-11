package com.example.data.prayer

data class City(
    val id: String,
    val nameBangla: String,
    val nameEnglish: String,
    val regionBangla: String,
    val regionEnglish: String,
    val latitude: Double,
    val longitude: Double,
    val timeZoneId: String = "Asia/Dhaka",
    val isInternational: Boolean = false
)

object CityData {

    val DEFAULT_CITY = City(
        id = "dhaka",
        nameBangla = "ঢাকা",
        nameEnglish = "Dhaka",
        regionBangla = "ঢাকা বিভাগ",
        regionEnglish = "Dhaka Division",
        latitude = 23.8103,
        longitude = 90.4125,
        timeZoneId = "Asia/Dhaka"
    )

    // Preloaded 64 districts of Bangladesh + major world cities
    val ALL_CITIES: List<City> = listOf(
        // Dhaka Division
        DEFAULT_CITY,
        City("gazipur", "গাজীপুর", "Gazipur", "ঢাকা বিভাগ", "Dhaka Division", 24.0023, 90.4264),
        City("narayanganj", "নারায়ণগঞ্জ", "Narayanganj", "ঢাকা বিভাগ", "Dhaka Division", 23.6238, 90.5000),
        City("tangail", "টাঙ্গাইল", "Tangail", "ঢাকা বিভাগ", "Dhaka Division", 24.2513, 89.9167),
        City("faridpur", "ফরিদপুর", "Faridpur", "ঢাকা বিভাগ", "Dhaka Division", 23.6071, 89.8429),
        City("gopalganj", "গোপালগঞ্জ", "Gopalganj", "ঢাকা বিভাগ", "Dhaka Division", 23.0051, 89.8266),
        City("kishoreganj", "কিশোরগঞ্জ", "Kishoreganj", "ঢাকা বিভাগ", "Dhaka Division", 24.4449, 90.7766),
        City("manikganj", "মানিকগঞ্জ", "Manikganj", "ঢাকা বিভাগ", "Dhaka Division", 23.8644, 90.0047),
        City("munshiganj", "মুন্সীগঞ্জ", "Munshiganj", "ঢাকা বিভাগ", "Dhaka Division", 23.5422, 90.5305),
        City("narsingdi", "নরসিংদী", "Narsingdi", "ঢাকা বিভাগ", "Dhaka Division", 23.9322, 90.7154),
        City("rajbari", "রাজবাড়ী", "Rajbari", "ঢাকা বিভাগ", "Dhaka Division", 23.7574, 89.6445),
        City("shariatpur", "শরীয়তপুর", "Shariatpur", "ঢাকা বিভাগ", "Dhaka Division", 23.2423, 90.3540),
        City("madaripur", "মাদারীপুর", "Madaripur", "ঢাকা বিভাগ", "Dhaka Division", 23.1641, 90.1897),

        // Chittagong Division
        City("chittagong", "চট্টগ্রাম", "Chittagong", "চট্টগ্রাম বিভাগ", "Chittagong Division", 22.3569, 91.7832),
        City("coxsbazar", "কক্সবাজার", "Cox's Bazar", "চট্টগ্রাম বিভাগ", "Chittagong Division", 21.4272, 92.0058),
        City("comilla", "কুমিল্লা", "Comilla", "চট্টগ্রাম বিভাগ", "Chittagong Division", 23.4607, 91.1809),
        City("brahmanbaria", "ব্রাহ্মণবাড়িয়া", "Brahmanbaria", "চট্টগ্রাম বিভাগ", "Chittagong Division", 23.9571, 91.1119),
        City("chandpur", "চাঁদপুর", "Chandpur", "চট্টগ্রাম বিভাগ", "Chittagong Division", 23.2333, 90.6667),
        City("feni", "ফেনী", "Feni", "চট্টগ্রাম বিভাগ", "Chittagong Division", 23.0159, 91.3976),
        City("lakshmipur", "লক্ষ্মীপুর", "Lakshmipur", "চট্টগ্রাম বিভাগ", "Chittagong Division", 22.9425, 90.8412),
        City("noakhali", "নোয়াখালী", "Noakhali", "চট্টগ্রাম বিভাগ", "Chittagong Division", 22.8696, 91.0994),
        City("bandarban", "বান্দরবান", "Bandarban", "চট্টগ্রাম বিভাগ", "Chittagong Division", 22.1953, 92.2184),
        City("khagrachhari", "খাগড়াছড়ি", "Khagrachhari", "চট্টগ্রাম বিভাগ", "Chittagong Division", 23.1193, 91.9847),
        City("rangamati", "রাঙ্গামাটি", "Rangamati", "চট্টগ্রাম বিভাগ", "Chittagong Division", 22.7324, 92.2985),

        // Sylhet Division
        City("sylhet", "সিলেট", "Sylhet", "সিলেট বিভাগ", "Sylhet Division", 24.8949, 91.8687),
        City("moulvibazar", "মৌলভীবাজার", "Moulvibazar", "সিলেট বিভাগ", "Sylhet Division", 24.4829, 91.7774),
        City("habiganj", "হবিগঞ্জ", "Habiganj", "সিলেট বিভাগ", "Sylhet Division", 24.3749, 91.4155),
        City("sunamganj", "সুনামগঞ্জ", "Sunamganj", "সিলেট বিভাগ", "Sylhet Division", 25.0658, 91.3950),

        // Rajshahi Division
        City("rajshahi", "রাজশাহী", "Rajshahi", "রাজশাহী বিভাগ", "Rajshahi Division", 24.3636, 88.6241),
        City("bogra", "বগুড়া", "Bogra", "রাজশাহী বিভাগ", "Rajshahi Division", 24.8465, 89.3777),
        City("pabna", "পাবনা", "Pabna", "রাজশাহী বিভাগ", "Rajshahi Division", 24.0123, 89.2557),
        City("sirajganj", "সিরাজগঞ্জ", "Sirajganj", "রাজশাহী বিভাগ", "Rajshahi Division", 24.4534, 89.7008),
        City("naogaon", "নওগাঁ", "Naogaon", "রাজশাহী বিভাগ", "Rajshahi Division", 24.7936, 88.9318),
        City("natore", "নাটোর", "Natore", "রাজশাহী বিভাগ", "Rajshahi Division", 24.4206, 89.0003),
        City("chapainawabganj", "চাঁপাইনবাবগঞ্জ", "Chapai Nawabganj", "রাজশাহী বিভাগ", "Rajshahi Division", 24.5965, 88.2775),
        City("joypurhat", "জয়পুরহাট", "Joypurhat", "রাজশাহী বিভাগ", "Rajshahi Division", 25.1015, 89.0277),

        // Khulna Division
        City("khulna", "খুলনা", "Khulna", "খুলনা বিভাগ", "Khulna Division", 22.8456, 89.5403),
        City("jessore", "যশোর", "Jessore", "খুলনা বিভাগ", "Khulna Division", 23.1664, 89.2081),
        City("kushtia", "কুষ্টিয়া", "Kushtia", "খুলনা বিভাগ", "Khulna Division", 23.9013, 89.1204),
        City("satkhira", "সাতক্ষীরা", "Satkhira", "খুলনা বিভাগ", "Khulna Division", 22.7185, 89.0705),
        City("bagerhat", "বাগেরহাট", "Bagerhat", "খুলনা বিভাগ", "Khulna Division", 22.6516, 89.7859),
        City("chuadanga", "চুয়াডাঙ্গা", "Chuadanga", "খুলনা বিভাগ", "Khulna Division", 23.6402, 88.8418),
        City("jhenaidah", "ঝিনাইদহ", "Jhenaidah", "খুলনা বিভাগ", "Khulna Division", 23.5448, 89.1539),
        City("magura", "মাগুরা", "Magura", "খুলনা বিভাগ", "Khulna Division", 23.4873, 89.4198),
        City("meherpur", "মেহেরপুর", "Meherpur", "খুলনা বিভাগ", "Khulna Division", 23.7622, 88.6318),
        City("narail", "নড়াইল", "Narail", "খুলনা বিভাগ", "Khulna Division", 23.1725, 89.5127),

        // Barisal Division
        City("barisal", "বরিশাল", "Barisal", "বরিশাল বিভাগ", "Barisal Division", 22.7010, 90.3535),
        City("bhola", "ভোলা", "Bhola", "বরিশাল বিভাগ", "Barisal Division", 22.6859, 90.6481),
        City("jhalokathi", "ঝালকাঠি", "Jhalokathi", "বরিশাল বিভাগ", "Barisal Division", 22.6406, 90.1987),
        City("patuakhali", "পটুয়াখালী", "Patuakhali", "বরিশাল বিভাগ", "Barisal Division", 22.3596, 90.3299),
        City("pirojpur", "পিরোজপুর", "Pirojpur", "বরিশাল বিভাগ", "Barisal Division", 22.5841, 89.9720),
        City("barguna", "বরগুনা", "Barguna", "বরিশাল বিভাগ", "Barisal Division", 22.0953, 90.0768),

        // Rangpur Division
        City("rangpur", "রংপুর", "Rangpur", "রংপুর বিভাগ", "Rangpur Division", 25.7439, 89.2752),
        City("dinajpur", "দিনাজপুর", "Dinajpur", "রংপুর বিভাগ", "Rangpur Division", 25.6217, 88.6355),
        City("gaibandha", "গাইবান্ধা", "Gaibandha", "রংপুর বিভাগ", "Rangpur Division", 25.3288, 89.5408),
        City("kurigram", "কুড়িগ্রাম", "Kurigram", "রংপুর বিভাগ", "Rangpur Division", 25.8054, 89.6362),
        City("lalmonirhat", "লালমনিরহাট", "Lalmonirhat", "রংপুর বিভাগ", "Rangpur Division", 25.9923, 89.2847),
        City("nilphamari", "নীলফামারী", "Nilphamari", "রংপুর বিভাগ", "Rangpur Division", 25.9318, 88.8560),
        City("panchagarh", "পঞ্চগড়", "Panchagarh", "রংপুর বিভাগ", "Rangpur Division", 26.3411, 88.5542),
        City("thakurgaon", "ঠাকুরগাঁও", "Thakurgaon", "রংপুর বিভাগ", "Rangpur Division", 26.0337, 88.4617),

        // Mymensingh Division
        City("mymensingh", "ময়মনসিংহ", "Mymensingh", "ময়মনসিংহ বিভাগ", "Mymensingh Division", 24.7471, 90.4203),
        City("jamalpur", "জামালপুর", "Jamalpur", "ময়মনসিংহ বিভাগ", "Mymensingh Division", 24.9375, 89.9378),
        City("netrokona", "নেত্রকোণা", "Netrokona", "ময়মনসিংহ বিভাগ", "Mymensingh Division", 24.8709, 90.7279),
        City("sherpur", "শেরপুর", "Sherpur", "ময়মনসিংহ বিভাগ", "Mymensingh Division", 25.0205, 90.0153),

        // International Holy & Major Cities
        City("makkah", "মক্কা মুকাররমা", "Makkah", "সৌদি আরব", "Saudi Arabia", 21.4225, 39.8262, "Asia/Riyadh", true),
        City("madinah", "মদিনা মুনাওয়ারা", "Madinah", "সৌদি আরব", "Saudi Arabia", 24.4672, 39.6111, "Asia/Riyadh", true),
        City("jerusalem", "আল-কুদস (জেরুসালেম)", "Jerusalem", "ফিলিস্তিন", "Palestine", 31.7683, 35.2137, "Asia/Jerusalem", true),
        City("dubai", "দুবাই", "Dubai", "সংযুক্ত আরব আমিরাত", "UAE", 25.2048, 55.2708, "Asia/Dubai", true),
        City("kualalumpur", "কুয়ালালামপুর", "Kuala Lumpur", "মালয়েশিয়া", "Malaysia", 3.1390, 101.6869, "Asia/Kuala_Lumpur", true),
        City("jakarta", "জাকার্তা", "Jakarta", "ইন্দোনেশিয়া", "Indonesia", -6.2088, 106.8456, "Asia/Jakarta", true),
        City("london", "লন্ডন", "London", "যুক্তরাজ্য", "United Kingdom", 51.5074, -0.1278, "Europe/London", true),
        City("newyork", "নিউ ইয়র্ক", "New York", "যুক্তরাষ্ট্র", "USA", 40.7128, -74.0060, "America/New_York", true),
        City("toronto", "টরন্টো", "Toronto", "কানাডা", "Canada", 43.6532, -79.3832, "America/Toronto", true),
        City("sydney", "সিডনি", "Sydney", "অস্ট্রেলিয়া", "Australia", -33.8688, 151.2093, "Australia/Sydney", true),
        City("karachi", "করাচি", "Karachi", "পাকিস্তান", "Pakistan", 24.8607, 67.0011, "Asia/Karachi", true),
        City("delhi", "দিল্লি", "Delhi", "ভারত", "India", 28.6139, 77.2090, "Asia/Kolkata", true),
        City("kolkata", "কলকাতা", "Kolkata", "ভারত", "India", 22.5726, 88.3639, "Asia/Kolkata", true)
    )

    fun searchCities(query: String): List<City> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return ALL_CITIES
        return ALL_CITIES.filter {
            it.nameBangla.contains(q) ||
            it.nameEnglish.lowercase().contains(q) ||
            it.regionBangla.contains(q) ||
            it.regionEnglish.lowercase().contains(q)
        }
    }
}
