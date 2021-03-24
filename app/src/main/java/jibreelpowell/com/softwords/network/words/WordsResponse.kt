package jibreelpowell.com.softwords.network.words

data class WordsResponse(
    val word: String,
    val results: List<Result>
) {
    data class Result(
        val partOfSpeech: String,
        val synonyms: List<String>?,
        val typeOf: List<String>?
    )
}
