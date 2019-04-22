package com.sun.util.jedis;

import com.sun.util.properties.SystemProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 创建一个jedis的连接池
 * @author SUNCHANGQING
 * 2018年4月30日上午9:23:36
 */
public class JedisPoolUtil {
	
	private static JedisPool jedisPool;
	private static void createJedisPool(){
		// 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxActive(SystemProperties.getInt("jedis.max.active", 100));
        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWait(SystemProperties.getInt("jedis.max.wait", 1000));
        // 设置空间连接
        config.setMaxIdle(SystemProperties.getInt("jedis.max.idle", 10));
        // 创建连接池
        jedisPool = new JedisPool(config,SystemProperties.get("jedis.ip","127.0.0.1"),SystemProperties.getInt("jedis.prot", 6379));
	}
	/**
	 * 在多线程环境同步初始化
	 */
    private static synchronized void poolInit() {
        if (jedisPool == null){
        	createJedisPool();
        }    
    }
    /**
     * 获取一个jedis 对象
     * 
     * @return
     */
    public static Jedis getJedis() {
        if (jedisPool == null){
        	poolInit();
        } 
        Jedis jedis = jedisPool.getResource();
        if(SystemProperties.getBoolean("jedis.set.pass", false)){
        	jedis.auth(SystemProperties.get("jedis.pass", "1234"));
        }
        return jedis;
    }
    /**
     * 归还一个连接
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
    	jedisPool.returnResource(jedis);
    }
}
