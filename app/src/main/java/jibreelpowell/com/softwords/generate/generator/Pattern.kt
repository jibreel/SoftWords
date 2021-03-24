package jibreelpowell.com.softwords.generate.generator

import jibreelpowell.com.softwords.generate.generator.Word.PartOfSpeech.*

enum class Pattern(val parts: List<Word.PartOfSpeech>) {
    NounVerb(arrayListOf(NOUN, VERB)),
    ArticleNounVerb(arrayListOf(ARTICLE, NOUN, VERB)),
    ArticleNounPrepositionArticleNoun(arrayListOf(ARTICLE, NOUN, PREPOSITION, ARTICLE, NOUN)),
    ArticleNounVerbArticleNoun(arrayListOf(ARTICLE, NOUN, VERB, ARTICLE, NOUN));

    companion object Factory{
        fun random(): Pattern = values().random()
    }
}
