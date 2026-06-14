package kot.gin.hentai_dashboard.server.modules.base.enums

import com.fasterxml.jackson.annotation.JsonValue

/**
 * 响应状态
 * @author hjg71
 * @since 2026/6/14 11:13
 * @description 响应状态枚举
 * @param code 响应状态码
 * @param msg 响应状态描述(简略)
 */
enum class ResponseStatus(@JsonValue val code: Int, val msg: String) {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    PARAM_ERROR(2, "参数错误"),
    NOT_FOUND(3, "未找到"),
}