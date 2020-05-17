package cn.htwlgs.guanwang.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>描述</pre>
 * 逻辑分页实体类
 */
@Data
public class PageList<T> {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long page;
    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private long totalCount;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long totalPages;
    /**
     * 页面记录数
     */
    @ApiModelProperty(value = "页面记录数/分页大小")
    private long size;



    @ApiModelProperty(value = "当前页相关数据")
    private List<T> items;





    /**
     * 带着分页信息的列表
     *
     * @param list       数据列表
     * @param current    当前页
     * @param size       分页大小
     * @param totalCount 数据总量
     */
    public PageList(List<T> list, long current, long size, long totalCount) {
        this.page = current;
        this.size = size;
        this.totalCount = totalCount;
        // 计算总页数
        boolean isFullPages = totalCount % size == 0;
        if (!isFullPages) {
            // 若当前数据总数与页面数据大小相除不为整数，则增加一页显示剩余的数据
            this.totalPages = totalCount / size + 1;
        } else {
            this.totalPages = totalCount / size;
        }

        if (CollectionUtils.isEmpty(list)) {
            this.items = new ArrayList<>();
        } else {
//            this.list = list.subList(startIndex, endIndex);
            this.items = list;
        }
    }

}