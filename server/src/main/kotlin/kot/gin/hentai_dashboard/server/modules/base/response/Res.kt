package kot.gin.hentai_dashboard.server.modules.base.response

import kot.gin.hentai_dashboard.server.modules.base.enums.ResponseStatus

/**
 * @author hjg71
 * @since 2026/6/14 11:17
 */
data class Res<T>(val status: ResponseStatus, val message: String, val data: T? = null) {
    val statusMessage: String get() = status.msg

    companion object{
        fun <T> success(data: T? = null): Res<T> = Res(ResponseStatus.SUCCESS, "", data)
        fun <T> fail(message: String): Res<T> = Res(ResponseStatus.FAIL, message)
        fun <T> notFound(message: String = ""): Res<T> = Res(ResponseStatus.NOT_FOUND, message,null)
    }
}