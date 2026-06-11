package com.flightbooking.util;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Component
public class RedisDistributedLock {
    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    private final StringRedisTemplate stringRedisTemplate;
    private static final Long LOCK_EXPIRE_MS = 30000L;
    private static final Long RETRY_INTERVAL_MS = 100L;

    
    
    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // lockKey 是 Redis 的 key，requestId 标识哪个线程持有锁
    public boolean tryLock(String lockKey, String requestId, Long expireMs) {
        long deadline = System.currentTimeMillis() + LOCK_EXPIRE_MS;  // 最多等30秒
        boolean setIfAbsent = false;
        while(System.currentTimeMillis() < deadline){
            // SETNX: key不存在才设置成功
            setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireMs, TimeUnit.MILLISECONDS);
            if(setIfAbsent == false){
                try {
                    Thread.sleep(RETRY_INTERVAL_MS);
                } catch (InterruptedException e) {
                    log.warn("获取锁被中断", e);
                    Thread.currentThread().interrupt();  // 恢复中断状态
                    return false; 
                }
            }
            if (setIfAbsent) {
                return true;  // 拿到锁，立即返回
            }
        }
       return setIfAbsent; 
    }

    public void unlock(String lockKey,String requestId){
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Long.class);
        stringRedisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        log.debug("释放锁: {}", lockKey);
    }
    
}

