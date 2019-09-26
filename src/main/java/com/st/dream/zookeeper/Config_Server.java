package com.st.dream.zookeeper;

import org.apache.zookeeper.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Config_Server {

    static String PARENT_PATH ="/config";
    static ZooKeeper zk = null;
    static List<String> oldChildren = null;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        zk = new ZooKeeper("10.201.10.90:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                String path = watchedEvent.getPath();
                Event.EventType type = watchedEvent.getType();
                //nodecreated
                if (Event.EventType.NodeChildrenChanged.equals(type) && path.equals(PARENT_PATH)) {
                    try {
                        List<String> newChildren = zk.getChildren(PARENT_PATH, true);
                        if (newChildren.size() > oldChildren.size()) {
                            //可以判断出nodecreated事件  并且告诉客户端哪一个节点
                            List<String> collect = newChildren.parallelStream().filter(e -> !oldChildren.contains(e)).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(collect) && collect.size() ==1 ) {
                                String s = collect.get(0);
                                String s1 = new String(zk.getData(PARENT_PATH + "/" + s, true, null));
                                printResult("nodecreated", PARENT_PATH + "/" + s, s1);
                                oldChildren = newChildren;
                            }
                        }
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //nodedeleted
                if (Event.EventType.NodeDeleted.equals(type)) {
                    printResult("nodedeleted", path, "");
                    try {
                        oldChildren = zk.getChildren(path.substring(0, path.lastIndexOf("/")), true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //nodedatachanged
                if (Event.EventType.NodeDataChanged.equals(type)) {
                    try {
                        String value = new String(zk.getData(path, true, null));
                        printResult("nodedatachanged", path, value);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        if (zk.exists(PARENT_PATH, null) == null) {
            zk.create(PARENT_PATH, "hehe".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }


        //注册监听
        oldChildren = zk.getChildren(PARENT_PATH, true, null);

        //nodedeleted nodedatachanged要注册在子节点
        for (String child: oldChildren) {
            String newPath = PARENT_PATH + "/" +child;
            zk.getData(newPath, true, null);
        }

        Thread.sleep(Long.MAX_VALUE);

    }

    static void printResult(String caozuo, String path, String data) {
        System.out.println(caozuo + "了一个节点,节点路径 " + path + " ,节点内容: " +data);
    }
}
