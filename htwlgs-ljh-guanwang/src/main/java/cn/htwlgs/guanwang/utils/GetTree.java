package cn.htwlgs.guanwang.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Adminstrators
 * Date: 2019/10/16
 * Time: 9:08
 * Description: No Description
 */
public class GetTree {


    /**
     * 根据条件获取树状结构
     * @param list
     * @return
     */
    public static List<? extends TreeNode>  getTree(List<? extends TreeNode> list) {
        Map<Long, TreeNode> dtoMap = new HashMap<>();
        for (TreeNode node : list) {
            dtoMap.put(node.getBM(), node);
        }
        List<TreeNode> resultList = new ArrayList<>();
        for (Map.Entry<Long, TreeNode> entry : dtoMap.entrySet()) {
            TreeNode node = entry.getValue();
             if (node.getSJID() == null){
                // 如果是顶层节点，直接添加到结果集合中
                resultList.add(node);
            } else {
                // 如果不是顶层节点，找其父节点，并且添加到父节点的子节点集合中
                if (dtoMap.get(node.getBM()) != null) {
                    dtoMap.get(node.getSJID()).getChildren().add(node);
                }
            }
        }
        return resultList;
    }






}