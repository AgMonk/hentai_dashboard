package kot.gin.hentai_dashboard.server.modules.aria2.service

import kot.gin.retrofit.aria2.response.DownloadTask

/**
 * @author hjg71
 * @since 2026/6/14 15:51
 */
interface Aria2TaskHandler {
    /**
     * 获取当前处理器处理的任务类型
     */
    fun getTaskType(): String

    /**
     * 处理已分组好的任务
     * @param registeredTasks 已注册的实时任务列表
     * @param unregisteredTasks 未注册的实时任务列表
     */
    fun handle(registeredTasks: List<DownloadTask>, unregisteredTasks: List<DownloadTask>)
}