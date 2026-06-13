package kot.gin.hentai_dashboard.server

import org.dromara.autotable.springboot.EnableAutoTable
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoTable
@MapperScan
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
