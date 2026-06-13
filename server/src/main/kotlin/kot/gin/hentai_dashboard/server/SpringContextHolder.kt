package kot.gin.hentai_dashboard.server

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * Spring 上下文持有器，提供静态方法获取 Bean
 * @author Gin
 * @since 2026/6/14 00:07
 */
@Component
class SpringContextHolder : ApplicationContextAware {

    companion object {
        lateinit var context: ApplicationContext
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}