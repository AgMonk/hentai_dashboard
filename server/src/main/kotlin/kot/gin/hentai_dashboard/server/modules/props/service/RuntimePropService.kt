package kot.gin.hentai_dashboard.server.modules.props.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import kot.gin.hentai_dashboard.server.modules.props.entity.RuntimeProp

/**
 * @author hjg71
 * @since 2026/6/14 09:29
 */
interface RuntimePropService : IService<RuntimeProp> {
    fun findByKey(key: String): RuntimeProp?

    fun listByKeys(keys: Collection<String>): List<RuntimeProp>

    fun updateByKey(key: String, value: String): RuntimeProp

    fun deleteByKey(key: String): Boolean

    fun pageWithKeyword(page: Page<RuntimeProp>, keyword: String?): Page<RuntimeProp>
}