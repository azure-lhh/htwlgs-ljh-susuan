package cn.htwlgs.guanwang.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class EasyExcelListener {

    /**
     * 指定阀值
     * @param consumer
     * @param threshold
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer,int threshold){
        return new AnalysisEventListener<T>() {
            private LinkedList<T> linkedList = new LinkedList<T>();
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                linkedList.add(t);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
                log.info("解析到一条数据:{}", JSON.toJSONString(t));
                if (linkedList.size() == threshold) {
                    consumer.accept(linkedList);
                    linkedList.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (linkedList.size() > 0) {
                    consumer.accept(linkedList);
                }
                log.info("所有数据解析完成！");
            }
        };
    }


    /**
     * 不指定阀值
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer){
        return getListener(consumer,10);
    }
}
