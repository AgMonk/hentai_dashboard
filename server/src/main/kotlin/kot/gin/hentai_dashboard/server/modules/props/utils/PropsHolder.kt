package kot.gin.hentai_dashboard.server.modules.props.utils

/**
 * @author Gin
 * @since 2026/6/14 00:27
 */
class PropsHolder {
    companion object {
        var workSpace: String by RuntimePropDelegate(
            "WORK_SPACE",
            "工作目录, 所有文件都存储在这个目录下",
            "d:/workspace/hentai_dashboard",
            StringTransfer()
        )
    }
}