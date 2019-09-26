package com.st.dream.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class Config_Client {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("10.201.10.90:2181", 5000, null);
//        zk.create("/config/test01", "aaaaaaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.delete("/config/test01", -1);
//        zk.setData("/config/test01", "bbbb".getBytes(), -1);
    }
}
