package kot.gin.hentai_dashboard.server.modules.aria2.entity

import com.baomidou.mybatisplus.annotation.FieldStrategy
import com.baomidou.mybatisplus.annotation.TableField
import kot.gin.hentai_dashboard.server.modules.base.entity.BaseEntity
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnComment
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.enums.IndexTypeEnum

/**
 * @author hjg71
 * @since 2026/6/14 16:07
 */
@AutoTable(comment = "本系统已管理的Aria2任务")  // 激活表自动维护
class Aria2DownloadTask : BaseEntity() {
    @ColumnComment("任务唯一标识")
    @ColumnType("varchar", length = 20)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    @Index(type = IndexTypeEnum.UNIQUE)
    var gid = ""

    @ColumnComment("任务类型")
    @ColumnType("varchar", length = 20)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    var type = ""
}