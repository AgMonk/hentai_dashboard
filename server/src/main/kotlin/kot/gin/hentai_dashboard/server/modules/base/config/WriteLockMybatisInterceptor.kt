package kot.gin.hentai_dashboard.server.modules.base.config

import kot.gin.hentai_dashboard.server.logger
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.mapping.SqlCommandType
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.springframework.stereotype.Component

/**
 * MyBatis 写操作拦截器
 *
 * 在 Executor.update() 层面拦截所有 INSERT / UPDATE / DELETE，
 * 自动使用 [WriteLock] 加锁，确保 SQLite 单线程写入。
 *
 * Service / Mapper 层无需再手动 synchronized。
 *
 * @author Gin
 * @since 2026/6/14
 */
@Intercepts(
    Signature(
        type = Executor::class,
        method = "update",
        args = [MappedStatement::class, Any::class]
    )
)
@Component
class WriteLockMybatisInterceptor(
    private val writeLock: WriteLock
) : Interceptor {

    override fun intercept(invocation: Invocation): Any? {
        val ms = invocation.args[0] as MappedStatement
        // 只对写操作（INSERT / UPDATE / DELETE）加锁，查询不受影响
        if (ms.sqlCommandType in WRITE_COMMANDS) {
            logger.debug("等待获取写锁")
            synchronized(writeLock) {
                return invocation.proceed()
            }
        }
        return invocation.proceed()
    }

    override fun plugin(target: Any): Any = super.plugin(target)

    companion object {
        private val WRITE_COMMANDS = setOf(
            SqlCommandType.INSERT,
            SqlCommandType.UPDATE,
            SqlCommandType.DELETE
        )
    }
}