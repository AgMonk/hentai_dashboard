package kot.gin.hentai_dashboard.server.modules.pixiv.service.impl

import kot.gin.hentai_dashboard.server.logger
import kot.gin.hentai_dashboard.server.modules.aria2.entity.Aria2DownloadTask
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


    override fun handle(
        tasks: List<DownloadTask>,
        list: List<Aria2DownloadTask>
    ) {
        val pixivAria2Tasks = list.filter { it.type == TASK_TYPE }.toMutableList()
        val myGid = pixivAria2Tasks.map { it.gid }
        val pixivTasks = tasks.filter { myGid.contains(it.gid) }

        // 处理已完成任务
        pixivTasks.filter { it.completed }.takeIf { it.isNotEmpty() }?.also { tasks ->
            aria2DownloadTaskService.removeByGid(tasks.mapNotNull { it.gid })
            logger.info("{}, 移除 {} 个已完成任务", TASK_TYPE, tasks.size)

            //todo 如果是动图任务，添加到GIF生成队列
        }

        //todo 处理报错任务



        // 根据文件名发现任务


    }

    companion object{
        const val TASK_TYPE = "PIXIV"
        val PIXIV_PATTERN_ILLUSTRATION = "\\d+_p\\d+".toPattern()
        val PIXIV_PATTERN_GIF = "\\d+_ugoira".toPattern()
    }
}