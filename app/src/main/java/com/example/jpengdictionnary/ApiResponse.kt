package com.example.jpengdictionnary

data class ApiResponse(
    val meta: Meta,
    val data: List<WordData>
)

data class Meta(
    val status: Int
)
data class WordData(
    val slug: String,
    val is_common: Boolean,
    val tags: List<String>,
    val jlpt: List<String>,
    val japanese: List<Japanese>,
    val senses: List<Sense>,
    val attribution: Attribution
)

data class Japanese(
    val word: String,
    val reading: String
)

data class Sense(
    val english_definitions: List<String>,
    val parts_of_speech: List<String>,
    val links: List<Any>,
    val tags: List<Any>,
    val restrictions: List<Any>,
    val see_also: List<Any>,
    val antonyms: List<Any>,
    val source: List<Any>,
    val info: List<Any>
)

data class Attribution(
    val jmdict: Boolean,
    val jmnedict: Boolean,
    val dbpedia: Boolean
)
