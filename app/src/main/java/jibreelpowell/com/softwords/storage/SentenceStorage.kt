package jibreelpowell.com.softwords.storage

interface SentenceStorage {
    fun storeSentence(sentence: String)
    fun retrieveAllSentences(): List<String>
}