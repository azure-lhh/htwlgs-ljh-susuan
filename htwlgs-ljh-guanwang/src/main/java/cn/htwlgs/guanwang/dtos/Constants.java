package cn.htwlgs.guanwang.dtos;

/**
 * @Description: 常量池
 *
 * @author lixin
 * @date 2020-04-22 17:47:40
 **/
public interface Constants {

    /**
     * 文件上传下载的桶
     */
    public static final String FILE_KEY = "files";
    /**
     * 化粪池 运行状态
     */
    public static final String SEPTIC_TANK_STATE = "运行";
    public static final String SEPTIC_TANK_STATE_ON = "未运行";

    /**
     * 泵站 开关状态
     */
    public static final String PUMPING_STATION_SIGNAL = "开";
    public static final String PUMPING_STATION_SIGNAL_ON = "关";

    /**
     * 泵站统计
     */
    public static final String PUMPING_STATION_STATISTICS_SIGNAL_OFF = "泵站开启数量";
    public static final String PUMPING_STATION_STATISTICS_SIGNAL_ON = "泵站关闭数量";
    public static final String PUMPING_STATION_STATISTICS_WL_MAX = "水位最大值";
    public static final String PUMPING_STATION_STATISTICS_WL_MIN = "水位最小值";
    public static final String PUMPING_STATION_STATISTICS_TRAFFIC_MAX = "流量最大值";
    public static final String PUMPING_STATION_STATISTICS_TRAFFIC_MIN = "流量最小值";

    /**
     * 化粪池统计
     */
    public static final String SEPTIC_TANK_STATISTICS_STATE_OFF = "化粪池运行数量";
    public static final String SEPTIC_TANK_STATISTICS_STATE_ON = "化粪池未运行数量";
    public static final String SEPTIC_TANK_STATISTICS_HANDLE_MAX = "最大处理量";
    public static final String SEPTIC_TANK_STATISTICS_HANDLE_MIN = "最小处理量";
    public static final String SEPTIC_TANK_STATISTICS_MARK_MAX = "标高最大值";
    public static final String SEPTIC_TANK_STATISTICS_MARK_MIN = "标高最小值";

    /**
     * 雨水管段统计
     */
    public static final String RAINWATER_PART_STATISTICS_DEPTH_MAX = "管道埋深最大值";
    public static final String RAINWATER_PART_STATISTICS_DEPTH_MIN = "管道埋深最小值";
    /**
     * 截流井统计
     */

    public static final String INTERCEPTING_WELL_INSOLE_MAX = "内底标高最大值";
    public static final String INTERCEPTING_WELL_INSOLE_MIN = "内底标高最小值";
    public static final String INTERCEPTING_WELL_FLOW_MAX = "进流量最大值";
    public static final String INTERCEPTING_WELL_FLOW_MIN = "进流量最小值";
    public static final String INTERCEPTING_WELL_AREA_MAX = "积水面积最大值";
    public static final String INTERCEPTING_WELL_AREA_MIN = "积水面积最小值";
    /**
     * 统计年限
     */
    public static final String STATISTICS_YEAR_A = "1-2年";
    public static final String STATISTICS_YEAR_B = "2-3年";
    public static final String STATISTICS_YEAR_C = "3-6年";
    public static final String STATISTICS_YEAR_D = "6-10年";
    public static final String STATISTICS_YEAR_E = "10-20年";
    public static final String STATISTICS_YEAR_F = "20年以上";


    /**
     * 错误提示
     */
    public static final String UPDATE_EXIST_ERROR = "该数据不存在或已被删除！";
    public static final String DATA_NULL_ERROR = "数据为空！";
    public static final String EXPORT_DATA_NULL_ERROR = "未选中导出数据！";
    public static final String SERIAL_NO_EXIST_ERROR = "这个编号已经存在！";


}