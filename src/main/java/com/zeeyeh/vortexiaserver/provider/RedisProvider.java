package com.zeeyeh.vortexiaserver.provider;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisProvider {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String RELEASE_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('del', KEYS[1]) " +
            "else " +
            "return 0 " +
            "end";

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String getString(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return obj == null ? null : obj.toString();
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Boolean has(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean tryLock(String lockKey, String requestId, long seconds) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, seconds, TimeUnit.SECONDS);
    }

    public Boolean tryLock(String lockKey, String requestId, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, timeout, timeUnit);
    }

    public Boolean releaseLock(String lockKey, String requestId) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(RELEASE_SCRIPT);
        script.setResultType(Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(lockKey),
                Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }
}
