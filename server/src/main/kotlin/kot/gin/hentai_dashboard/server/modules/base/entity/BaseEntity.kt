package kot.gin.hentai_dashboard.server.modules.base.entity

import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import org.dromara.autotable.annotation.ColumnComment
import org.dromara.autotable.annotation.ColumnNotNull
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import java.time.ZonedDateTime

/**
 * @author Gin
 * @since 2026/6/13 22:59
 */
open class BaseEntity {
    @PrimaryKey(autoIncrement = true)
    var id : Long? = null

    @TableField(updateStrategy = FieldStrategy.NEVER)
    @ColumnComment("创建时间")
    @ColumnNotNull
    @Index
    var createdAt = ZonedDateTime.now()

//    @TableField(typeHandler = ZonedDateTimeTypeHandler::class)

    @TableField()
    @ColumnComment("更新时间")
    @ColumnNotNull
    @Index
    var updatedAt = ZonedDateTime.now()
}