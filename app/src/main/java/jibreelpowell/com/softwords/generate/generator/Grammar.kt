package jibreelpowell.com.softwords.generate.generator


enum class GrammaticalNumber {
    SINGULAR,
    PLURAL;

    companion object {
        fun get(isSingular: Boolean) =
                when (isSingular) {
                    true -> SINGULAR
                    false -> PLURAL
                }
    }

}

enum class GrammaticalPerson {
    FIRST,
    SECOND,
    THIRD
}

enum class ArticleType {
    DEFINITE,
    INDEFINITE;

    companion object {
        fun get(isDefinite: Boolean) =
        when (isDefinite) {
            true -> DEFINITE
            false -> INDEFINITE
        }
    }
}

abstract class Word {
    abstract fun asString(): String
    override fun toString() = asString()

}
