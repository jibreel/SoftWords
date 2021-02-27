package jibreelpowell.com.softwords.utils

import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object Converters {
    private  val formatter = DateTimeFormatter.ISO_OFFSET_DATE

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime.FROM)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(odt: OffsetDateTime?): String? {
        return odt?.format(formatter)
    }
}