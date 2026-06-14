package kot.gin.hentai_dashboard.server.modules.props.utils

import kot.gin.hentai_dashboard.server.modules.props.enums.PropConst

/**
 * @author Gin
 * @since 2026/6/14 00:27
 */
class PropsHolder {
    companion object {
        var workSpace: String by RuntimePropDelegate(PropConst.WORK_SPACE, StringTransfer())
    }
}