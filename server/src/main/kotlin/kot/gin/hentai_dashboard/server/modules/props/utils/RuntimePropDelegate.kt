package kot.gin.hentai_dashboard.server.modules.props.utils

import kot.gin.hentai_dashboard.server.SpringContextHolder
import kot.gin.hentai_dashboard.server.modules.props.entity.RuntimeProp
import kot.gin.hentai_dashboard.server.modules.props.enums.PropConst
import kot.gin.hentai_dashboard.server.modules.props.service.RuntimePropService
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
 * @param propConst 属性常量，包含 key / description / defaultValue
 * @param transfer 类型转换器，用于 T 与 String 之间的转换
 * @author Gin
 * @since 2026/6/14 00:07
 */
class RuntimePropDelegate<T>(
    private val propConst: PropConst,
    private val transfer: Transfer<T>,
) : ReadWriteProperty<Any?, T> {
    private val service by lazy { SpringContextHolder.context.getBean<RuntimePropService>() }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val record = service.findByKey(propConst.key)
        return record?.let { transfer.decode(it.value) } ?: transfer.decode(propConst.defaultValue)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val encoded = transfer.encode(value)
        val record = service.findByKey(propConst.key)

            if (record != null) {
                service.updateByKey(propConst.key, encoded)
            } else {
                service.save(RuntimeProp().apply {
                    this.key = this@RuntimePropDelegate.propConst.key
                    this.description = this@RuntimePropDelegate.propConst.description
                    this.value = encoded
                })
            }
    }
}