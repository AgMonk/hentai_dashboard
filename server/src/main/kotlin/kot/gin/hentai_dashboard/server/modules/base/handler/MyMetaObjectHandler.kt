package kot.gin.hentai_dashboard.server.modules.base.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

/**
 * MyBatis-Plus 元对象字段填充处理器
 *
 * - INSERT → [createdAt]、[updatedAt] 写入当前时间
 * - UPDATE → [updatedAt] 更新为当前时间
 *
 * @author Gin
 * @since 2026/6/14
 */
@Component
class MyMetaObjectHandler : MetaObjectHandler {

    override fun insertFill(metaObject: MetaObject) {
        // ZonedDateTime 通过 ZonedDateTimeTypeHandler 转为 String 存入 SQLite
        this.setFieldValByName("createdAt", ZonedDateTime.now(), metaObject)
        this.setFieldValByName("updatedAt", ZonedDateTime.now(), metaObject)
    }

    override fun updateFill(metaObject: MetaObject) {
        this.setFieldValByName("updatedAt", ZonedDateTime.now(), metaObject)
    }
}