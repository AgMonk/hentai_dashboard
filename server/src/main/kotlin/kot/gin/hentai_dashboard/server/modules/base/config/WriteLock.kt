package kot.gin.hentai_dashboard.server.modules.base.config

import org.springframework.stereotype.Component

/**
 * - 数据库写锁，由于 SQLite 只支持单线程写入，所以需要手动实现写锁
 * - 所有数据库写操作均需使用 synchronized(writeLock) 进行加锁
 * @author Gin
 * @since 2026/6/14 01:04
 */
@Component
class WriteLock