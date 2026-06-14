package kot.gin.hentai_dashboard.server.modules.props.vo

import kot.gin.hentai_dashboard.server.modules.props.entity.RuntimeProp
import java.time.ZonedDateTime

/**
 * 运行时属性 视图对象
 * @author Gin
 * @since 2026/6/14
 */
data class RuntimePropVO(
    val id: Long?,
    val key: String,
    val description: String,
    val value: String,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?,
) {
    companion object {
        fun from(entity: RuntimeProp): RuntimePropVO = RuntimePropVO(
            id = entity.id,
            key = entity.key,
            description = entity.description,
            value = entity.value,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }
}