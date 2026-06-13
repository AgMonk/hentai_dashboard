package kot.gin.hentai_dashboard.server.modules.props.utils

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import kot.gin.hentai_dashboard.server.SpringContextHolder
import kot.gin.hentai_dashboard.server.modules.base.lock.WriteLock
import kot.gin.hentai_dashboard.server.modules.props.entity.RuntimeProp
import kot.gin.hentai_dashboard.server.modules.props.mapper.RuntimePropMapper
import org.springframework.beans.factory.getBean
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * RuntimeProp 属性委托（可读写）
 *
 * 将属性绑定到 RuntimeProp 表中对应 key 的记录上，
 * 如果未查询到记录则按照给定的 defaultValue 返回。
 * Mapper 从 Spring 上下文中自动获取。
 *
 * @param key 属性键，对应 RuntimeProp.key
 * @param description 属性描述
 * @param defaultValue 默认值，当数据库无记录时返回
 * @param transfer 类型转换器，用于 T 与 String 之间的转换
 * @author Gin
 * @since 2026/6/14 00:07
 */
class RuntimePropDelegate<T>(
    private val key: String,
    private val description: String,
    private val defaultValue: String,
    private val transfer: Transfer<T>,
) : ReadWriteProperty<Any?, T> {
    private val writeLock: WriteLock by lazy { SpringContextHolder.context.getBean<WriteLock>() }

    private val mapper: RuntimePropMapper by lazy { SpringContextHolder.context.getBean<RuntimePropMapper>() }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val record = mapper.selectOne(KtQueryWrapper(RuntimeProp::class.java).eq(RuntimeProp::key, key))
        return record?.let { transfer.decode(it.value) } ?: transfer.decode(defaultValue)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val encoded = transfer.encode(value)
        val record = mapper.selectOne(KtQueryWrapper(RuntimeProp::class.java).eq(RuntimeProp::key, key))

        synchronized(writeLock) {
            if (record != null) {
                mapper.updateById(RuntimeProp().apply { this.id = record.id; this.value = encoded })
            } else {
                val newRecord = RuntimeProp().apply {
                    this.key = this@RuntimePropDelegate.key
                    this.description = this@RuntimePropDelegate.description
                    this.value = encoded
                }
                mapper.insert(newRecord)
            }
        }
    }
}