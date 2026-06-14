package kot.gin.hentai_dashboard.server.modules.props.controller

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import kot.gin.hentai_dashboard.server.modules.base.response.Res
import kot.gin.hentai_dashboard.server.modules.props.service.RuntimePropService
import kot.gin.hentai_dashboard.server.modules.props.vo.RuntimePropVO
import org.springframework.web.bind.annotation.*

/**
 * 运行时属性 RESTful 接口
 * @author Gin
 * @since 2026/6/14
 */
@RestController
@RequestMapping("/api/props")
class RuntimePropController(
    private val runtimePropService: RuntimePropService,
) {

    /**
     * 分页查询
     */
    @GetMapping
    fun page(
        @RequestParam(defaultValue = "1") page: Long,
        @RequestParam(defaultValue = "10") size: Long,
        @RequestParam(required = false) keyword: String?,
    ) = Res.success(
        runtimePropService
            .pageWithKeyword(Page(page, size), keyword).convert { RuntimePropVO.from(it) })

    /**
     * 按 key 查询单个
     */
    @GetMapping("/{key}")
    fun getByKey(@PathVariable key: String): Res<RuntimePropVO> {
        val entity = runtimePropService.findByKey(key)
            ?: return Res.notFound("未找到运行时属性 $key")
        return Res.success(RuntimePropVO.from(entity))
    }

    /**
     * 按 key 修改
     */
    @PutMapping("/{key}")
    fun updateByKey(
        @PathVariable key: String,
        @RequestParam value: String,
    ): Res<RuntimePropVO> {
        val entity = runtimePropService.updateByKey(key, value)
        return Res.success(RuntimePropVO.from(entity))
    }
}