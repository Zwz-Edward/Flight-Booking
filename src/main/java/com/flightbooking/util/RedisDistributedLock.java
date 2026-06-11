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
    private StringRedisTemplate stringRedisTemplate;
    private static final Long LOCK_EXPIRE_MS = 30000L;
    private static final Long RETRY_INTERVAL_MS = 100L;

    
    
    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // lockKey 鍊煎緱鏄疪edis鐨刱ey requestId鎸囩殑鏄摢涓偅涓嚎绋嬬敤鐫€閿?
    public boolean tryLock(String lockKey,String requestId, Long expireMs){
        long deadline = System.currentTimeMillis() + LOCK_EXPIRE_MS;  // 鏈€澶氱瓑30绉?
        boolean setIfAbsent = false;
        while(System.currentTimeMillis() < deadline){
            // 濡傛灉lockKey宸茬粡涓嶅瓨鍦紝鍒欒缃紝濡傛灉宸茬粡瀛樺湪锛屽垯涓嶈缃?== NX
            setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireMs, TimeUnit.MILLISECONDS);
            if(setIfAbsent == false){
                try {
                    Thread.sleep(RETRY_INTERVAL_MS);
                } catch (InterruptedException e) {
                    log.warn("鑾峰彇閿佽涓柇", e);
                    Thread.currentThread().interrupt();  // 鎭㈠涓柇鐘舵€?
                    return false; 
                }
            }
            if (setIfAbsent) {
                return true;  // 鎷垮埌閿侊紝绔嬪嵆杩斿洖
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
        log.debug("閲婃斁閿? {}", lockKey);
    }
    
}

