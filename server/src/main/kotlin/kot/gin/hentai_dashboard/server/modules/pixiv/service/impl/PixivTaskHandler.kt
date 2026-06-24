package kot.gin.hentai_dashboard.server.modules.pixiv.service.impl

import kot.gin.hentai_dashboard.server.logger
import kot.gin.hentai_dashboard.server.modules.aria2.service.Aria2DownloadTaskService
import kot.gin.hentai_dashboard.server.modules.aria2.service.Aria2TaskHandler
import kot.gin.retrofit.aria2.response.DownloadTask
import org.springframework.stereotype.Service

/**
 * @author hjg71
 * @since 2026/6/14 16:44
 */
@Service
class PixivTaskHandler(
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

            //todo 如果是动图任务，添加到GIF生成队列
        }

        //todo 处理报错任务


        // 根据文件名发现任务
        logger.info(
            "{}: 根据文件名发现任务, {} 个未注册任务: {}",
            TASK_TYPE,
            unregisteredTasks.size,
            unregisteredTasks.map { it.filename}
        )
        aria2DownloadTaskService.addGid(TASK_TYPE, unregisteredTasks.filter { it.completed }
            .filter { isPixivTask(it.filename ?:"") }
            .mapNotNull { it.gid })


    }

    companion object {
        const val TASK_TYPE = "PIXIV"
        val PIXIV_PATTERN_ILLUSTRATION = "\\d+_p\\d+".toPattern()
        val PIXIV_PATTERN_GIF = "(\\d+)_ugoira".toPattern()

        fun isPixivTask(filename: String) = PIXIV_PATTERN_ILLUSTRATION.matcher(filename).find()
                || PIXIV_PATTERN_GIF.matcher(filename).find()
    }
}