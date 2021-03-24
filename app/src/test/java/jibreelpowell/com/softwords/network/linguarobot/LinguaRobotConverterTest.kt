package jibreelpowell.com.softwords.network.linguarobot

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LinguaRobotConverterTest {

    lateinit var linguaRobotConverter: LinguaRobotConverter

    @Before
    fun setup() {
        linguaRobotConverter = LinguaRobotConverter()
    }

    @Test
    fun testConvertResponseToNoun() {
        val tableResponse = Gson().fromJson(LinguaRobotSampleJson.TABLE, LinguaRobotResponse::class.java)
        val table = linguaRobotConverter.convertResponseToNoun(tableResponse)
        assertEquals("tables", table.plural)
        assertEquals("table", table.singular)
    }

    @Test
    fun testConvertResponseToVerb() {
        val haveResponse = Gson().fromJson(LinguaRobotSampleJson.HAVE, LinguaRobotResponse::class.java)
        val table = linguaRobotConverter.convertResponseToVerb(haveResponse)
        assertEquals("have", table.firstPersonSingular)
        assertEquals("have", table.firstPersonPlural)
        assertEquals("have", table.secondPerson)
        assertEquals("has", table.thirdPersonSingular)
        assertEquals("have", table.thirdPersonPlural)
    }

    @Test
    fun testConvertResponseToIrregularVerb() {
        val doResponse = Gson().fromJson(LinguaRobotSampleJson.DO, LinguaRobotResponse::class.java)
        val doVerb = linguaRobotConverter.convertResponseToVerb(doResponse)
        assertEquals("do", doVerb.firstPersonSingular)
        assertEquals("do", doVerb.firstPersonPlural)
        assertEquals("do", doVerb.secondPerson)
        assertEquals("does", doVerb.thirdPersonSingular)
        assertEquals("do", doVerb.thirdPersonPlural)
    }
}