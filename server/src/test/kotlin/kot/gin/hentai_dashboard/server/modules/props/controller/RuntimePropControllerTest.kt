package kot.gin.hentai_dashboard.server.modules.props.controller

import kot.gin.hentai_dashboard.server.modules.props.entity.RuntimeProp
import kot.gin.hentai_dashboard.server.modules.props.enums.PropConst
import kot.gin.hentai_dashboard.server.modules.props.service.RuntimePropService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RuntimePropControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var runtimePropService: RuntimePropService

    private val restTemplate = RestTemplate()
    private val baseUrl: String get() = "http://localhost:$port/api/props"

    @BeforeEach
    fun setup() {
        val testKeys = runtimePropService.list()
            .map { it.key }
            .filter { it != PropConst.WORK_SPACE.key }
        testKeys.forEach { runtimePropService.deleteByKey(it) }
    }

    // ---------------------------------------------------------------------------
    // GET /api/props
    // ---------------------------------------------------------------------------

    @Test
    fun `分页查询 - 默认参数`() {
        val result = restTemplate.getForEntity(baseUrl, Map::class.java)

        assertEquals(200, result.statusCode.value())
        val body = result.body!!
        assertEquals(0, body["status"])
        assertNotNull(body["data"])
        val data = body["data"] as Map<*, *>
        assertNotNull(data["records"])
    }

    @Test
    fun `分页查询 - 带关键词`() {
        runtimePropService.save(RuntimeProp().apply {
            key = "TEST_KEY"
            description = "测试属性"
            value = "test_value"
        })

        val result = restTemplate.getForEntity("$baseUrl?keyword=TEST", Map::class.java)

        assertEquals(200, result.statusCode.value())
        val body = result.body!!
        assertEquals(0, body["status"])
        val records = (body["data"] as Map<*, *>)["records"] as List<*>
        assertTrue(records.isNotEmpty())
        assertEquals("TEST_KEY", (records[0] as Map<*, *>)["key"])
    }

    @Test
    fun `分页查询 - 关键词无匹配`() {
        val result = restTemplate.getForEntity("$baseUrl?keyword=NOT_EXIST_12345", Map::class.java)

        assertEquals(200, result.statusCode.value())
        val body = result.body!!
        assertEquals(0, body["status"])
        val records = (body["data"] as Map<*, *>)["records"] as List<*>
        assertTrue(records.isEmpty())
    }

    // ---------------------------------------------------------------------------
    // GET /api/props/{key}
    // ---------------------------------------------------------------------------

    @Test
    fun `按key查询 - 存在`() {
        val result = restTemplate.getForEntity("$baseUrl/{key}", Map::class.java, PropConst.WORK_SPACE.key)

        assertEquals(200, result.statusCode.value())
        val body = result.body!!
        assertEquals(0, body["status"])
        assertEquals(PropConst.WORK_SPACE.key, (body["data"] as Map<*, *>)["key"])
    }

    @Test
    fun `按key查询 - 不存在`() {
        val result = restTemplate.getForEntity("$baseUrl/{key}", Map::class.java, "NON_EXISTENT_KEY")

        assertEquals(200, result.statusCode.value())
        val body = result.body!!
        assertEquals(3, body["status"])
        assertEquals("未找到运行时属性 NON_EXISTENT_KEY", body["message"])
    }

    // ---------------------------------------------------------------------------
    // PUT /api/props/{key}?value=...
    // ---------------------------------------------------------------------------

    @Test
    fun `按key修改`() {
        val newValue = "updated_value"
        val url = "$baseUrl/{key}?value=$newValue"

        val result = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            null,
            Map::class.java,
            PropConst.WORK_SPACE.key
        )

        assertEquals(200, result.statusCode.value())
        val body = result.body!!
        assertEquals(0, body["status"])
        assertEquals(PropConst.WORK_SPACE.key, (body["data"] as Map<*, *>)["key"])
        assertEquals(newValue, (body["data"] as Map<*, *>)["value"])
    }

    @Test
    fun `按key修改 - 可重复执行`() {
        val url = "$baseUrl/{key}?value=same_value"

        // 第一次
        restTemplate.exchange(url, HttpMethod.PUT, null, Map::class.java, PropConst.WORK_SPACE.key)

        // 第二次（幂等）
        val result = restTemplate.exchange(url, HttpMethod.PUT, null, Map::class.java, PropConst.WORK_SPACE.key)

        assertEquals(200, result.statusCode.value())
        assertEquals(0, (result.body!!)["status"])
    }
}