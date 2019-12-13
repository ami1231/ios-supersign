package com.naoh.iossupersign.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Ami
 *
 */
@Component("redisCache")
public class RedisCache implements Cache{

	private final RedisTemplate<String, Object> redisTemplate;

	public RedisCache(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void putObj(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void putObj(String key, Object value, long liveTime, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, liveTime, unit);
	}

	@Override
	public Object getObj(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}
	
	@Override
	public void del(List<String> keys) {
		redisTemplate.delete(keys);
	}

	@Override
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public Long incr(String key) {
		return redisTemplate.opsForValue().increment(key);
	}


	@Override
	public Map<Object, Object> hgetAll(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	@Override
	public void hset(String key, Object field, Object value) {
		redisTemplate.opsForHash().put(key, field, value);
	}
	
	@Override
	public Boolean setLock(String key,Object value, long liveTime, TimeUnit unit) {
		return this.putIfAbsent(key, value, liveTime, unit);
	}

	/* (non-Javadoc)
	 * @see com.etonghk.killrate.cache.Cache#hdel(java.lang.String, java.lang.Object)
	 */
	@Override
	public Long hdel(String key, Object field) {
		return redisTemplate.opsForHash().delete(key, field);
	}
	
	@Override
	public Long hsize(String key) {
		return redisTemplate.opsForHash().size(key);
	}
	
	@Override
	public Boolean expire(String key, long liveTime, TimeUnit unit) {
		return redisTemplate.expire(key, liveTime, unit);
	}
	
	@Override
	public Long hincr(String key, String hashKey, Long incrValue) {
		return redisTemplate.opsForHash().increment(key, hashKey, incrValue);
	}
	
	@Override
	public Object hget(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	@Override
	public Boolean putIfAbsent(String key, Object value, long liveTime, TimeUnit unit) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, liveTime, unit);
	}
}