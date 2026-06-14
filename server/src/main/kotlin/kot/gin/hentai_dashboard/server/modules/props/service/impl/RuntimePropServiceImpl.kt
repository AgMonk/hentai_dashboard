package kot.gin.hentai_dashboard.server.modules.props.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import kot.gin.hentai_dashboard.server.logger
import kot.gin.hentai_dashboard.server.modules.props.entity.RuntimeProp
import kot.gin.hentai_dashboard.server.modules.props.enums.PropConst
import kot.gin.hentai_dashboard.server.modules.props.mapper.RuntimePropMapper
import kot.gin.hentai_dashboard.server.modules.props.service.RuntimePropService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class RuntimePropServiceImpl : ServiceImpl<RuntimePropMapper, RuntimeProp>(), RuntimePropService, ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val allKeys = PropConst.entries.map { it.key }
        val existingKeys = listByKeys(allKeys).map { it.key }.toSet()
        val missing = PropConst.entries.filter { it.key !in existingKeys }

        if (missing.isEmpty()) return

        saveBatch(missing.map { propConst ->
            RuntimeProp().apply {
                key = propConst.key
                description = propConst.description
                value = propConst.defaultValue
            }
        })
        missing.forEach { logger.info("初始化运行时属性: {} = {}", it.key, it.defaultValue) }
    }

    private fun wrapperByKey(key: String): KtQueryWrapper<RuntimeProp>? =
        KtQueryWrapper(RuntimeProp::class.java).eq(RuntimeProp::key, key)

    override fun findByKey(key: String): RuntimeProp? {
        return getOne(wrapperByKey(key))
    }

    override fun listByKeys(keys: Collection<String>): List<RuntimeProp> {
        if (keys.isEmpty()) return emptyList()
        return list(KtQueryWrapper(RuntimeProp::class.java).`in`(RuntimeProp::key, keys))
    }

    override fun updateByKey(key: String, value: String): RuntimeProp {
        val entity = RuntimeProp().apply {
            this.value = value
        }
        update(entity, wrapperByKey(key))
        return findByKey(key)!!
    }

    override fun deleteByKey(key: String): Boolean {
        return remove(wrapperByKey(key))
    }

    override fun pageWithKeyword(page: Page<RuntimeProp>, keyword: String?): Page<RuntimeProp> {
        val wrapper = KtQueryWrapper(RuntimeProp::class.java)
        if (!keyword.isNullOrBlank()) {
            wrapper.like(RuntimeProp::key, keyword)
                .or()
                .like(RuntimeProp::description, keyword)
                .or()
                .like(RuntimeProp::value, keyword)
        }
        wrapper.orderByAsc(RuntimeProp::key)
        return page(page, wrapper)
    }
}