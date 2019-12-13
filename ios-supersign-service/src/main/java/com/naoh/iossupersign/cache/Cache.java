package com.naoh.iossupersign.cache;

import org.springframework.data.redis.core.RedisCallback;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Cache 基礎類別
 * @author Ami
 *
 */
public interface Cache {
	
	void del(String key);
	
	void del(List<String> keys);

	void putObj(String key, Object value);
	
	Object getObj(String key);
	
	void putObj(String key, Object value, long liveTime, TimeUnit unit);

	Set<String> keys(final String pattern);

	boolean exists(final String key);

	Long incr(String key);

	void hset(String key, Object field, Object value);

	Long hdel(String key, Object field);
	
	/**
	 * @param key
	 * @param value
	 * @return
	 */
	Boolean setLock(String key, Object value, long liveTime, TimeUnit unit);

	/**
	 * @param key
	 * @return
	 */
	Map<Object, Object> hgetAll(String key);

	/**
	 * @param key
	 * @return
	 */
	Long hsize(String key);
	
	Boolean expire(String key, long liveTime, TimeUnit unit);
	
	Long hincr(String key, String hashKey, Long incrValue);
	
	Object hget(String key, String hashKey);

	Boolean putIfAbsent(String key, Object value, long liveTime, TimeUnit unit);
}