package kot.gin.hentai_dashboard.server

import org.apache.ibatis.annotations.Mapper
import org.dromara.autotable.springboot.EnableAutoTable
import org.mybatis.spring.annotation.MapperScan
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAutoTable
@MapperScan(annotationClass = Mapper::class)
@EnableScheduling
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}

// 扩展函数，为所有类添加日志对象
val Any.logger: org.slf4j.Logger
    get() = LoggerFactory.getLogger(this::class.java)
