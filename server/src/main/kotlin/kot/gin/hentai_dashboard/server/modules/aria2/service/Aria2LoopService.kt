package kot.gin.hentai_dashboard.server.modules.aria2.service

import kot.gin.hentai_dashboard.server.SpringContextHolder
import kot.gin.hentai_dashboard.server.logger
import kot.gin.hentai_dashboard.server.modules.aria2.utils.Aria2Client
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * @author hjg71
 * @since 2026/6/14 15:49
 */
@Service
class Aria2LoopService(
    private val service: Aria2DownloadTaskService,
) {
    /**
     * 轮询任务, 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    fun loop() {
        logger.info("Aria2轮询任务: 开始")
        val tasks = Aria2Client.listAll() ?: return
        val list = service.list()
        logger.info("Aria2轮询任务: 获取到 {} 个任务, 已注册 {} 个任务, 开始处理", tasks.size,list.size)
        SpringContextHolder.context.getBeansOfType(Aria2TaskHandler::class.java).values.forEach { handler ->
            val type = handler.getTaskType()
            val myGid = list.filter { it.type == type }.map { it.gid }
            val registeredTasks = tasks.filter { myGid.contains(it.gid) }
            val unregisteredTasks = tasks.filter { !myGid.contains(it.gid) }
            handler.handle(registeredTasks, unregisteredTasks)
        }
        logger.info("Aria2轮询任务: 结束")
    }
}