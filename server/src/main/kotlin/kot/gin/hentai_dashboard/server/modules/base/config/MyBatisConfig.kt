package kot.gin.hentai_dashboard.server.modules.base.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import kot.gin.hentai_dashboard.server.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * MyBatis-Plus 全局配置
 * - 注册分页插件，支持 MyBatis-Plus 分页查询
 * @author Gin
 * @since 2026/6/14
 */
@Configuration
class MyBatisConfig {

    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        logger.info("注册分页插件，自动适配 SQLite（使用 LIMIT / OFFSET 语法）")
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.SQLITE))
        return interceptor
    }
}