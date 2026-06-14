package kot.gin.hentai_dashboard.server.modules.props.entity

import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import kot.gin.hentai_dashboard.server.modules.base.entity.BaseEntity
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnComment
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.enums.IndexTypeEnum

/**
 * 运行时属性
 * @author Gin
 * @since 2026/6/13 23:04
 */
@AutoTable(comment = "运行时属性表")  // 激活表自动维护
class RuntimeProp : BaseEntity() {
    @ColumnComment("属性键")
    @ColumnType("varchar", length = 100)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    @Index(type = IndexTypeEnum.UNIQUE)
    var key = ""

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    @ColumnType("varchar", length = 200)
    @ColumnComment("属性描述")
    var description = ""

    @ColumnComment("属性值")
    var value = ""
}