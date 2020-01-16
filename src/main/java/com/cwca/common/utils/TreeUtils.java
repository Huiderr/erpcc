package com.cwca.common.utils;

import com.cwca.worship.manager.entity.LotRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：wongs.
 * @ Date       ：Created in 14:17 2018/12/28
 * @ Description：生成树
 */
public class TreeUtils {

    public static List<LotRoute> switchNodeListToTree(List<LotRoute> nodes) {
        List<LotRoute> rootNodes = getRoot(nodes);
        rootNodes.forEach(nodes::remove);
        return recursive(rootNodes, nodes);
    }

    private static List<LotRoute> recursive(List<LotRoute> rootNodes, List<LotRoute> nodes) {
        for (LotRoute treeNode : nodes) {
            for (LotRoute rootNode : rootNodes) {
                if (treeNode.getPid() == rootNode.getId()) {
                    rootNode.getNodes().add(treeNode);
                }
            }
        }
        return rootNodes;
    }

    private static List<LotRoute> getRoot(List<LotRoute> nodes) {
        List<LotRoute> root = new ArrayList<>();
        if (nodes != null && nodes.size() > 0) {
            for (LotRoute treeNode : nodes) {
                if (treeNode.getPid() == 0) {
                    root.add(treeNode);
                }
            }
        }
        return root;
    }
}
