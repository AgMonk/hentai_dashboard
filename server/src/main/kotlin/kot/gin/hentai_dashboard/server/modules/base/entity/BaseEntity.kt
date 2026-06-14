package kot.gin.hentai_dashboard.server.modules.base.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import org.dromara.autotable.annotation.*
import java.time.ZonedDateTime

/**
 * @author Gin
 * @since 2026/6/13 22:59
 */
open class BaseEntity {
    @PrimaryKey(autoIncrement = true)
    var id : Long? = null

    @TableField(updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    @ColumnComment("创建时间")
    @ColumnType("varchar", length = 100)
    @ColumnNotNull
    @Index
    var createdAt: ZonedDateTime? = null

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ColumnComment("更新时间")
    @ColumnType("varchar", length = 100)
    @ColumnNotNull
    @Index
    var updatedAt: ZonedDateTime? = null
}