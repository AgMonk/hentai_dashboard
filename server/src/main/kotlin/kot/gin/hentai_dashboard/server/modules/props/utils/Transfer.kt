package kot.gin.hentai_dashboard.server.modules.props.utils

/**
 * 类型转换器
 * @author Gin
 * @since 2026/6/13 23:59
 */
interface Transfer<T> {

    fun encode(t: T): String

    fun decode(s: String): T
}

class StringTransfer : Transfer<String> {
    override fun encode(t: String): String = t
    override fun decode(s: String): String = s
}