package kot.gin.hentai_dashboard.server

import kot.gin.hentai_dashboard.server.modules.props.utils.PropsHolder
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ServerApplicationTests {

    @Test
    fun contextLoads() {
        println(PropsHolder.workSpace)

        PropsHolder.workSpace = "d:/workspace/hentai_dashboard"

        println(PropsHolder.workSpace)

    }

}
