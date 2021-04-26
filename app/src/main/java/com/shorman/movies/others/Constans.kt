package com.shorman.movies.others

object Constans {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "0a4426fba341b4da97d9db1e0442993a"
    const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/original"
    const val FOOTER_VIEW_TYPE = 2
    const val MOVIE_VIEW_TYPE = 1
    const val DB_NAME = "joflix.db"
    const val MOVIES_TABLE_NAME = "movies_table"
    const val TV_SHOWS_TABLE_NAME = "tv_shows_table"
    const val REMOTE_KEYS_TABLE_MOVIE = "remote_keys_table_movie"
    const val REMOTE_KEYS_TABLE_SHOWS = "remote_keys_table_shows"
    const val LANGUAGE_CODES = " [\n" +
            "      {\"code\":\"ab\",\"name\":\"Abkhaz\",\"nativeName\":\"аҧсуа\"},\n" +
            "      {\"code\":\"aa\",\"name\":\"Afar\",\"nativeName\":\"Afaraf\"},\n" +
            "      {\"code\":\"af\",\"name\":\"Afrikaans\",\"nativeName\":\"Afrikaans\"},\n" +
            "      {\"code\":\"ak\",\"name\":\"Akan\",\"nativeName\":\"Akan\"},\n" +
            "      {\"code\":\"sq\",\"name\":\"Albanian\",\"nativeName\":\"Shqip\"},\n" +
            "      {\"code\":\"am\",\"name\":\"Amharic\",\"nativeName\":\"አማርኛ\"},\n" +
            "      {\"code\":\"ar\",\"name\":\"Arabic\",\"nativeName\":\"العربية\"},\n" +
            "      {\"code\":\"an\",\"name\":\"Aragonese\",\"nativeName\":\"Aragonés\"},\n" +
            "      {\"code\":\"hy\",\"name\":\"Armenian\",\"nativeName\":\"Հայերեն\"},\n" +
            "      {\"code\":\"as\",\"name\":\"Assamese\",\"nativeName\":\"অসমীয়া\"},\n" +
            "      {\"code\":\"av\",\"name\":\"Avaric\",\"nativeName\":\"авар мацӀ, магӀарул мацӀ\"},\n" +
            "      {\"code\":\"ae\",\"name\":\"Avestan\",\"nativeName\":\"avesta\"},\n" +
            "      {\"code\":\"ay\",\"name\":\"Aymara\",\"nativeName\":\"aymar aru\"},\n" +
            "      {\"code\":\"az\",\"name\":\"Azerbaijani\",\"nativeName\":\"azərbaycan dili\"},\n" +
            "      {\"code\":\"bm\",\"name\":\"Bambara\",\"nativeName\":\"bamanankan\"},\n" +
            "      {\"code\":\"ba\",\"name\":\"Bashkir\",\"nativeName\":\"башҡорт теле\"},\n" +
            "      {\"code\":\"eu\",\"name\":\"Basque\",\"nativeName\":\"euskara, euskera\"},\n" +
            "      {\"code\":\"be\",\"name\":\"Belarusian\",\"nativeName\":\"Беларуская\"},\n" +
            "      {\"code\":\"bn\",\"name\":\"Bengali\",\"nativeName\":\"বাংলা\"},\n" +
            "      {\"code\":\"bh\",\"name\":\"Bihari\",\"nativeName\":\"भोजपुरी\"},\n" +
            "      {\"code\":\"bi\",\"name\":\"Bislama\",\"nativeName\":\"Bislama\"},\n" +
            "      {\"code\":\"bs\",\"name\":\"Bosnian\",\"nativeName\":\"bosanski jezik\"},\n" +
            "      {\"code\":\"br\",\"name\":\"Breton\",\"nativeName\":\"brezhoneg\"},\n" +
            "      {\"code\":\"bg\",\"name\":\"Bulgarian\",\"nativeName\":\"български език\"},\n" +
            "      {\"code\":\"my\",\"name\":\"Burmese\",\"nativeName\":\"ဗမာစာ\"},\n" +
            "      {\"code\":\"ca\",\"name\":\"Catalan; Valencian\",\"nativeName\":\"Català\"},\n" +
            "      {\"code\":\"ch\",\"name\":\"Chamorro\",\"nativeName\":\"Chamoru\"},\n" +
            "      {\"code\":\"ce\",\"name\":\"Chechen\",\"nativeName\":\"нохчийн мотт\"},\n" +
            "      {\"code\":\"ny\",\"name\":\"Chichewa; Chewa; Nyanja\",\"nativeName\":\"chiCheŵa, chinyanja\"},\n" +
            "      {\"code\":\"zh\",\"name\":\"Chinese\",\"nativeName\":\"中文 (Zhōngwén), 汉语, 漢語\"},\n" +
            "      {\"code\":\"cv\",\"name\":\"Chuvash\",\"nativeName\":\"чӑваш чӗлхи\"},\n" +
            "      {\"code\":\"kw\",\"name\":\"Cornish\",\"nativeName\":\"Kernewek\"},\n" +
            "      {\"code\":\"co\",\"name\":\"Corsican\",\"nativeName\":\"corsu, lingua corsa\"},\n" +
            "      {\"code\":\"cr\",\"name\":\"Cree\",\"nativeName\":\"ᓀᐦᐃᔭᐍᐏᐣ\"},\n" +
            "      {\"code\":\"hr\",\"name\":\"Croatian\",\"nativeName\":\"hrvatski\"},\n" +
            "      {\"code\":\"cs\",\"name\":\"Czech\",\"nativeName\":\"česky, čeština\"},\n" +
            "      {\"code\":\"da\",\"name\":\"Danish\",\"nativeName\":\"dansk\"},\n" +
            "      {\"code\":\"dv\",\"name\":\"Divehi; Dhivehi; Maldivian;\",\"nativeName\":\"ދިވެހި\"},\n" +
            "      {\"code\":\"nl\",\"name\":\"Dutch\",\"nativeName\":\"Nederlands, Vlaams\"},\n" +
            "      {\"code\":\"en\",\"name\":\"English\",\"nativeName\":\"English\"},\n" +
            "      {\"code\":\"eo\",\"name\":\"Esperanto\",\"nativeName\":\"Esperanto\"},\n" +
            "      {\"code\":\"et\",\"name\":\"Estonian\",\"nativeName\":\"eesti, eesti keel\"},\n" +
            "      {\"code\":\"ee\",\"name\":\"Ewe\",\"nativeName\":\"Eʋegbe\"},\n" +
            "      {\"code\":\"fo\",\"name\":\"Faroese\",\"nativeName\":\"føroyskt\"},\n" +
            "      {\"code\":\"fj\",\"name\":\"Fijian\",\"nativeName\":\"vosa Vakaviti\"},\n" +
            "      {\"code\":\"fi\",\"name\":\"Finnish\",\"nativeName\":\"suomi, suomen kieli\"},\n" +
            "      {\"code\":\"fr\",\"name\":\"French\",\"nativeName\":\"français, langue française\"},\n" +
            "      {\"code\":\"ff\",\"name\":\"Fula; Fulah; Pulaar; Pular\",\"nativeName\":\"Fulfulde, Pulaar, Pular\"},\n" +
            "      {\"code\":\"gl\",\"name\":\"Galician\",\"nativeName\":\"Galego\"},\n" +
            "      {\"code\":\"ka\",\"name\":\"Georgian\",\"nativeName\":\"ქართული\"},\n" +
            "      {\"code\":\"de\",\"name\":\"German\",\"nativeName\":\"Deutsch\"},\n" +
            "      {\"code\":\"el\",\"name\":\"Greek, Modern\",\"nativeName\":\"Ελληνικά\"},\n" +
            "      {\"code\":\"gn\",\"name\":\"Guaraní\",\"nativeName\":\"Avañeẽ\"},\n" +
            "      {\"code\":\"gu\",\"name\":\"Gujarati\",\"nativeName\":\"ગુજરાતી\"},\n" +
            "      {\"code\":\"ht\",\"name\":\"Haitian; Haitian Creole\",\"nativeName\":\"Kreyòl ayisyen\"},\n" +
            "      {\"code\":\"ha\",\"name\":\"Hausa\",\"nativeName\":\"Hausa, هَوُسَ\"},\n" +
            "      {\"code\":\"he\",\"name\":\"Hebrew (modern)\",\"nativeName\":\"עברית\"},\n" +
            "      {\"code\":\"hz\",\"name\":\"Herero\",\"nativeName\":\"Otjiherero\"},\n" +
            "      {\"code\":\"hi\",\"name\":\"Hindi\",\"nativeName\":\"हिन्दी, हिंदी\"},\n" +
            "      {\"code\":\"ho\",\"name\":\"Hiri Motu\",\"nativeName\":\"Hiri Motu\"},\n" +
            "      {\"code\":\"hu\",\"name\":\"Hungarian\",\"nativeName\":\"Magyar\"},\n" +
            "      {\"code\":\"ia\",\"name\":\"Interlingua\",\"nativeName\":\"Interlingua\"},\n" +
            "      {\"code\":\"id\",\"name\":\"Indonesian\",\"nativeName\":\"Bahasa Indonesia\"},\n" +
            "      {\"code\":\"ie\",\"name\":\"Interlingue\",\"nativeName\":\"Originally called Occidental; then Interlingue after WWII\"},\n" +
            "      {\"code\":\"ga\",\"name\":\"Irish\",\"nativeName\":\"Gaeilge\"},\n" +
            "      {\"code\":\"ig\",\"name\":\"Igbo\",\"nativeName\":\"Asụsụ Igbo\"},\n" +
            "      {\"code\":\"ik\",\"name\":\"Inupiaq\",\"nativeName\":\"Iñupiaq, Iñupiatun\"},\n" +
            "      {\"code\":\"io\",\"name\":\"Ido\",\"nativeName\":\"Ido\"},\n" +
            "      {\"code\":\"is\",\"name\":\"Icelandic\",\"nativeName\":\"Íslenska\"},\n" +
            "      {\"code\":\"it\",\"name\":\"Italian\",\"nativeName\":\"Italiano\"},\n" +
            "      {\"code\":\"iu\",\"name\":\"Inuktitut\",\"nativeName\":\"ᐃᓄᒃᑎᑐᑦ\"},\n" +
            "      {\"code\":\"ja\",\"name\":\"Japanese\",\"nativeName\":\"日本語 (にほんご／にっぽんご)\"},\n" +
            "      {\"code\":\"jv\",\"name\":\"Javanese\",\"nativeName\":\"basa Jawa\"},\n" +
            "      {\"code\":\"kl\",\"name\":\"Kalaallisut, Greenlandic\",\"nativeName\":\"kalaallisut, kalaallit oqaasii\"},\n" +
            "      {\"code\":\"kn\",\"name\":\"Kannada\",\"nativeName\":\"ಕನ್ನಡ\"},\n" +
            "      {\"code\":\"kr\",\"name\":\"Kanuri\",\"nativeName\":\"Kanuri\"},\n" +
            "      {\"code\":\"ks\",\"name\":\"Kashmiri\",\"nativeName\":\"कश्मीरी, كشميري\u200E\"},\n" +
            "      {\"code\":\"kk\",\"name\":\"Kazakh\",\"nativeName\":\"Қазақ тілі\"},\n" +
            "      {\"code\":\"km\",\"name\":\"Khmer\",\"nativeName\":\"ភាសាខ្មែរ\"},\n" +
            "      {\"code\":\"ki\",\"name\":\"Kikuyu, Gikuyu\",\"nativeName\":\"Gĩkũyũ\"},\n" +
            "      {\"code\":\"rw\",\"name\":\"Kinyarwanda\",\"nativeName\":\"Ikinyarwanda\"},\n" +
            "      {\"code\":\"ky\",\"name\":\"Kirghiz, Kyrgyz\",\"nativeName\":\"кыргыз тили\"},\n" +
            "      {\"code\":\"kv\",\"name\":\"Komi\",\"nativeName\":\"коми кыв\"},\n" +
            "      {\"code\":\"kg\",\"name\":\"Kongo\",\"nativeName\":\"KiKongo\"},\n" +
            "      {\"code\":\"ko\",\"name\":\"Korean\",\"nativeName\":\"한국어 (韓國語), 조선말 (朝鮮語)\"},\n" +
            "      {\"code\":\"ku\",\"name\":\"Kurdish\",\"nativeName\":\"Kurdî, كوردی\u200E\"},\n" +
            "      {\"code\":\"kj\",\"name\":\"Kwanyama, Kuanyama\",\"nativeName\":\"Kuanyama\"},\n" +
            "      {\"code\":\"la\",\"name\":\"Latin\",\"nativeName\":\"latine, lingua latina\"},\n" +
            "      {\"code\":\"lb\",\"name\":\"Luxembourgish, Letzeburgesch\",\"nativeName\":\"Lëtzebuergesch\"},\n" +
            "      {\"code\":\"lg\",\"name\":\"Luganda\",\"nativeName\":\"Luganda\"},\n" +
            "      {\"code\":\"li\",\"name\":\"Limburgish, Limburgan, Limburger\",\"nativeName\":\"Limburgs\"},\n" +
            "      {\"code\":\"ln\",\"name\":\"Lingala\",\"nativeName\":\"Lingála\"},\n" +
            "      {\"code\":\"lo\",\"name\":\"Lao\",\"nativeName\":\"ພາສາລາວ\"},\n" +
            "      {\"code\":\"lt\",\"name\":\"Lithuanian\",\"nativeName\":\"lietuvių kalba\"},\n" +
            "      {\"code\":\"lu\",\"name\":\"Luba-Katanga\",\"nativeName\":\"\"},\n" +
            "      {\"code\":\"lv\",\"name\":\"Latvian\",\"nativeName\":\"latviešu valoda\"},\n" +
            "      {\"code\":\"gv\",\"name\":\"Manx\",\"nativeName\":\"Gaelg, Gailck\"},\n" +
            "      {\"code\":\"mk\",\"name\":\"Macedonian\",\"nativeName\":\"македонски јазик\"},\n" +
            "      {\"code\":\"mg\",\"name\":\"Malagasy\",\"nativeName\":\"Malagasy fiteny\"},\n" +
            "      {\"code\":\"ms\",\"name\":\"Malay\",\"nativeName\":\"bahasa Melayu, بهاس ملايو\u200E\"},\n" +
            "      {\"code\":\"ml\",\"name\":\"Malayalam\",\"nativeName\":\"മലയാളം\"},\n" +
            "      {\"code\":\"mt\",\"name\":\"Maltese\",\"nativeName\":\"Malti\"},\n" +
            "      {\"code\":\"mi\",\"name\":\"Māori\",\"nativeName\":\"te reo Māori\"},\n" +
            "      {\"code\":\"mr\",\"name\":\"Marathi (Marāṭhī)\",\"nativeName\":\"मराठी\"},\n" +
            "      {\"code\":\"mh\",\"name\":\"Marshallese\",\"nativeName\":\"Kajin M̧ajeļ\"},\n" +
            "      {\"code\":\"mn\",\"name\":\"Mongolian\",\"nativeName\":\"монгол\"},\n" +
            "      {\"code\":\"na\",\"name\":\"Nauru\",\"nativeName\":\"Ekakairũ Naoero\"},\n" +
            "      {\"code\":\"nv\",\"name\":\"Navajo, Navaho\",\"nativeName\":\"Diné bizaad, Dinékʼehǰí\"},\n" +
            "      {\"code\":\"nb\",\"name\":\"Norwegian Bokmål\",\"nativeName\":\"Norsk bokmål\"},\n" +
            "      {\"code\":\"nd\",\"name\":\"North Ndebele\",\"nativeName\":\"isiNdebele\"},\n" +
            "      {\"code\":\"ne\",\"name\":\"Nepali\",\"nativeName\":\"नेपाली\"},\n" +
            "      {\"code\":\"ng\",\"name\":\"Ndonga\",\"nativeName\":\"Owambo\"},\n" +
            "      {\"code\":\"nn\",\"name\":\"Norwegian Nynorsk\",\"nativeName\":\"Norsk nynorsk\"},\n" +
            "      {\"code\":\"no\",\"name\":\"Norwegian\",\"nativeName\":\"Norsk\"},\n" +
            "      {\"code\":\"ii\",\"name\":\"Nuosu\",\"nativeName\":\"ꆈꌠ꒿ Nuosuhxop\"},\n" +
            "      {\"code\":\"nr\",\"name\":\"South Ndebele\",\"nativeName\":\"isiNdebele\"},\n" +
            "      {\"code\":\"oc\",\"name\":\"Occitan\",\"nativeName\":\"Occitan\"},\n" +
            "      {\"code\":\"oj\",\"name\":\"Ojibwe, Ojibwa\",\"nativeName\":\"ᐊᓂᔑᓈᐯᒧᐎᓐ\"},\n" +
            "      {\"code\":\"cu\",\"name\":\"Old Church Slavonic, Church Slavic, Church Slavonic, Old Bulgarian, Old Slavonic\",\"nativeName\":\"ѩзыкъ словѣньскъ\"},\n" +
            "      {\"code\":\"om\",\"name\":\"Oromo\",\"nativeName\":\"Afaan Oromoo\"},\n" +
            "      {\"code\":\"or\",\"name\":\"Oriya\",\"nativeName\":\"ଓଡ଼ିଆ\"},\n" +
            "      {\"code\":\"os\",\"name\":\"Ossetian, Ossetic\",\"nativeName\":\"ирон æвзаг\"},\n" +
            "      {\"code\":\"pa\",\"name\":\"Panjabi, Punjabi\",\"nativeName\":\"ਪੰਜਾਬੀ, پنجابی\u200E\"},\n" +
            "      {\"code\":\"pi\",\"name\":\"Pāli\",\"nativeName\":\"पाऴि\"},\n" +
            "      {\"code\":\"fa\",\"name\":\"Persian\",\"nativeName\":\"فارسی\"},\n" +
            "      {\"code\":\"pl\",\"name\":\"Polish\",\"nativeName\":\"polski\"},\n" +
            "      {\"code\":\"ps\",\"name\":\"Pashto, Pushto\",\"nativeName\":\"پښتو\"},\n" +
            "      {\"code\":\"pt\",\"name\":\"Portuguese\",\"nativeName\":\"Português\"},\n" +
            "      {\"code\":\"qu\",\"name\":\"Quechua\",\"nativeName\":\"Runa Simi, Kichwa\"},\n" +
            "      {\"code\":\"rm\",\"name\":\"Romansh\",\"nativeName\":\"rumantsch grischun\"},\n" +
            "      {\"code\":\"rn\",\"name\":\"Kirundi\",\"nativeName\":\"kiRundi\"},\n" +
            "      {\"code\":\"ro\",\"name\":\"Romanian, Moldavian, Moldovan\",\"nativeName\":\"română\"},\n" +
            "      {\"code\":\"ru\",\"name\":\"Russian\",\"nativeName\":\"русский язык\"},\n" +
            "      {\"code\":\"sa\",\"name\":\"Sanskrit (Saṁskṛta)\",\"nativeName\":\"संस्कृतम्\"},\n" +
            "      {\"code\":\"sc\",\"name\":\"Sardinian\",\"nativeName\":\"sardu\"},\n" +
            "      {\"code\":\"sd\",\"name\":\"Sindhi\",\"nativeName\":\"सिन्धी, سنڌي، سندھی\u200E\"},\n" +
            "      {\"code\":\"se\",\"name\":\"Northern Sami\",\"nativeName\":\"Davvisámegiella\"},\n" +
            "      {\"code\":\"sm\",\"name\":\"Samoan\",\"nativeName\":\"gagana faa Samoa\"},\n" +
            "      {\"code\":\"sg\",\"name\":\"Sango\",\"nativeName\":\"yângâ tî sängö\"},\n" +
            "      {\"code\":\"sr\",\"name\":\"Serbian\",\"nativeName\":\"српски језик\"},\n" +
            "      {\"code\":\"gd\",\"name\":\"Scottish Gaelic; Gaelic\",\"nativeName\":\"Gàidhlig\"},\n" +
            "      {\"code\":\"sn\",\"name\":\"Shona\",\"nativeName\":\"chiShona\"},\n" +
            "      {\"code\":\"si\",\"name\":\"Sinhala, Sinhalese\",\"nativeName\":\"සිංහල\"},\n" +
            "      {\"code\":\"sk\",\"name\":\"Slovak\",\"nativeName\":\"slovenčina\"},\n" +
            "      {\"code\":\"sl\",\"name\":\"Slovene\",\"nativeName\":\"slovenščina\"},\n" +
            "      {\"code\":\"so\",\"name\":\"Somali\",\"nativeName\":\"Soomaaliga, af Soomaali\"},\n" +
            "      {\"code\":\"st\",\"name\":\"Southern Sotho\",\"nativeName\":\"Sesotho\"},\n" +
            "      {\"code\":\"es\",\"name\":\"Spanish; Castilian\",\"nativeName\":\"español, castellano\"},\n" +
            "      {\"code\":\"su\",\"name\":\"Sundanese\",\"nativeName\":\"Basa Sunda\"},\n" +
            "      {\"code\":\"sw\",\"name\":\"Swahili\",\"nativeName\":\"Kiswahili\"},\n" +
            "      {\"code\":\"ss\",\"name\":\"Swati\",\"nativeName\":\"SiSwati\"},\n" +
            "      {\"code\":\"sv\",\"name\":\"Swedish\",\"nativeName\":\"svenska\"},\n" +
            "      {\"code\":\"ta\",\"name\":\"Tamil\",\"nativeName\":\"தமிழ்\"},\n" +
            "      {\"code\":\"te\",\"name\":\"Telugu\",\"nativeName\":\"తెలుగు\"},\n" +
            "      {\"code\":\"tg\",\"name\":\"Tajik\",\"nativeName\":\"тоҷикӣ, toğikī, تاجیکی\u200E\"},\n" +
            "      {\"code\":\"th\",\"name\":\"Thai\",\"nativeName\":\"ไทย\"},\n" +
            "      {\"code\":\"ti\",\"name\":\"Tigrinya\",\"nativeName\":\"ትግርኛ\"},\n" +
            "      {\"code\":\"bo\",\"name\":\"Tibetan Standard, Tibetan, Central\",\"nativeName\":\"བོད་ཡིག\"},\n" +
            "      {\"code\":\"tk\",\"name\":\"Turkmen\",\"nativeName\":\"Türkmen, Түркмен\"},\n" +
            "      {\"code\":\"tl\",\"name\":\"Tagalog\",\"nativeName\":\"Wikang Tagalog, ᜏᜒᜃᜅ᜔ ᜆᜄᜎᜓᜄ᜔\"},\n" +
            "      {\"code\":\"tn\",\"name\":\"Tswana\",\"nativeName\":\"Setswana\"},\n" +
            "      {\"code\":\"to\",\"name\":\"Tonga (Tonga Islands)\",\"nativeName\":\"faka Tonga\"},\n" +
            "      {\"code\":\"tr\",\"name\":\"Turkish\",\"nativeName\":\"Türkçe\"},\n" +
            "      {\"code\":\"ts\",\"name\":\"Tsonga\",\"nativeName\":\"Xitsonga\"},\n" +
            "      {\"code\":\"tt\",\"name\":\"Tatar\",\"nativeName\":\"татарча, tatarça, تاتارچا\u200E\"},\n" +
            "      {\"code\":\"tw\",\"name\":\"Twi\",\"nativeName\":\"Twi\"},\n" +
            "      {\"code\":\"ty\",\"name\":\"Tahitian\",\"nativeName\":\"Reo Tahiti\"},\n" +
            "      {\"code\":\"ug\",\"name\":\"Uighur, Uyghur\",\"nativeName\":\"Uyƣurqə, ئۇيغۇرچە\u200E\"},\n" +
            "      {\"code\":\"uk\",\"name\":\"Ukrainian\",\"nativeName\":\"українська\"},\n" +
            "      {\"code\":\"ur\",\"name\":\"Urdu\",\"nativeName\":\"اردو\"},\n" +
            "      {\"code\":\"uz\",\"name\":\"Uzbek\",\"nativeName\":\"zbek, Ўзбек, أۇزبېك\u200E\"},\n" +
            "      {\"code\":\"ve\",\"name\":\"Venda\",\"nativeName\":\"Tshivenḓa\"},\n" +
            "      {\"code\":\"vi\",\"name\":\"Vietnamese\",\"nativeName\":\"Tiếng Việt\"},\n" +
            "      {\"code\":\"vo\",\"name\":\"Volapük\",\"nativeName\":\"Volapük\"},\n" +
            "      {\"code\":\"wa\",\"name\":\"Walloon\",\"nativeName\":\"Walon\"},\n" +
            "      {\"code\":\"cy\",\"name\":\"Welsh\",\"nativeName\":\"Cymraeg\"},\n" +
            "      {\"code\":\"wo\",\"name\":\"Wolof\",\"nativeName\":\"Wollof\"},\n" +
            "      {\"code\":\"fy\",\"name\":\"Western Frisian\",\"nativeName\":\"Frysk\"},\n" +
            "      {\"code\":\"xh\",\"name\":\"Xhosa\",\"nativeName\":\"isiXhosa\"},\n" +
            "      {\"code\":\"yi\",\"name\":\"Yiddish\",\"nativeName\":\"ייִדיש\"},\n" +
            "      {\"code\":\"yo\",\"name\":\"Yoruba\",\"nativeName\":\"Yorùbá\"},\n" +
            "      {\"code\":\"za\",\"name\":\"Zhuang, Chuang\",\"nativeName\":\"Saɯ cueŋƅ, Saw cuengh\"}\n" +
            "    ]"
}