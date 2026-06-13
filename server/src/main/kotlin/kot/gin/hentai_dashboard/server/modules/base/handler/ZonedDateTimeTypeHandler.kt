package kot.gin.hentai_dashboard.server.modules.base.handler

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * SQLite 不支持 ZonedDateTime，通过 String 中转
 * @author Gin
 * @since 2026/6/14 00:07
 */
class ZonedDateTimeTypeHandler : BaseTypeHandler<ZonedDateTime>() {

    override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: ZonedDateTime, jdbcType: JdbcType?) {
        ps.setString(i, parameter.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
    }

    override fun getNullableResult(rs: ResultSet, columnName: String): ZonedDateTime? {
        val value = rs.getString(columnName)
        return value?.let { ZonedDateTime.parse(it, DateTimeFormatter.ISO_ZONED_DATE_TIME) }
    }

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): ZonedDateTime? {
        val value = rs.getString(columnIndex)
        return value?.let { ZonedDateTime.parse(it, DateTimeFormatter.ISO_ZONED_DATE_TIME) }
    }

    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): ZonedDateTime? {
        val value = cs.getString(columnIndex)
        return value?.let { ZonedDateTime.parse(it, DateTimeFormatter.ISO_ZONED_DATE_TIME) }
    }
}