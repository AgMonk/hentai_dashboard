package kot.gin.hentai_dashboard.server.modules.twitter.service.impl

import kot.gin.hentai_dashboard.server.logger
import kot.gin.hentai_dashboard.server.modules.aria2.service.Aria2DownloadTaskService
import kot.gin.hentai_dashboard.server.modules.aria2.service.Aria2TaskHandler
import kot.gin.retrofit.aria2.response.DownloadTask
import org.springframework.stereotype.Service

/**
 * @author hjg71
 * @since 2026/6/25
 */
@Service
class TwitterTaskHandler(
    private val aria2DownloadTaskService: Aria2DownloadTaskService
) : Aria2TaskHandler {

    override fun getTaskType(): String = TASK_TYPE

    override fun handle(
        registeredTasks: List<DownloadTask>,
        unregisteredTasks: List<DownloadTask>
    ) {
        // 处理已完成任务
        registeredTasks.filter { it.completed }.takeIf { it.isNotEmpty() }?.also { tasks ->
            aria2DownloadTaskService.removeByGid(tasks.mapNotNull { it.gid })
        }

        //todo 处理报错任务

        // 根据文件名发现任务
        logger.info(
            "{}: 根据文件名发现任务, {} 个未注册任务: {}",
            TASK_TYPE,
            unregisteredTasks.size,
            unregisteredTasks.map { it.filename }
        )
        aria2DownloadTaskService.addGid(TASK_TYPE, unregisteredTasks.filter { it.completed }
            .filter { isTwitterTask(it.filename ?: "") }
            .mapNotNull { it.gid })
    }

    companion object {
        const val TASK_TYPE = "TWITTER"
        val TWITTER_PATTERN = "[a-zA-Z0-9]+[-_]\\d{16,19}".toPattern()

        fun isTwitterTask(filename: String) = TWITTER_PATTERN.matcher(filename).find()
    }
}