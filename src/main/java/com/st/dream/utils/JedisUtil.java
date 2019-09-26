package com.st.dream.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException("set异常: key: " + key + ", cause: " + e.getMessage());
        } finally {
            close(jedis);
        }
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @param expireTime 过期时间, 单位: s
     * @return
     */
    public String set(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String result = jedis.set(key, value);
//            if (Constant.Redis.OK.equals(result)) {
                jedis.expire(key, expireTime);
//            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("set-expireTime异常: key: " + key + ", cause: " + e.getMessage());
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("del异常: key: " + key + ", cause: " + e.getMessage());
        } finally {
            close(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("exists异常: key: " + key + ", cause: " + e.getMessage());
        } finally {
            close(jedis);
        }
    }

    public void close(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

}
