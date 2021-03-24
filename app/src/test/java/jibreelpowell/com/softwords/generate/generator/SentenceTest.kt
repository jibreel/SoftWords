package jibreelpowell.com.softwords.generate.generator

import org.junit.Assert.assertEquals
import org.junit.Test

class SentenceTest {

    @Test
    fun testGet() {
        val sentence = Sentence(Noun("tree"))
        assertEquals(sentence[0].toString(), "tree")
    }

    @Test
    fun testAsList() {
        val tree = Noun("tree")

        val sentenceList = arrayListOf(
            tree.article(ArticleType.DEFINITE),
            tree,
            Verb("stand")
        )

        val sentence = Sentence(sentenceList)
        assertEquals(sentenceList, sentence.asList())
    }

    @Test
    fun testToString() {
        val galaxy = Noun("galaxy", "galaxies")
        galaxy.number = GrammaticalNumber.PLURAL

        val sentence = Sentence(
            galaxy.article(ArticleType.INDEFINITE),
            galaxy,
            Verb("rise")
        )
        assertEquals(
            "galaxies rise.", sentence.toString()
        )
    }
}