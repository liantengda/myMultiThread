package com.lian.mycollection.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 8:48
 */
@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplateString;

    public static RedisTemplate redistemplate;
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public static final String SPLIT = ":";
    public static final String BOTTOM_LINE = "_";
    public static final String PREFIX_ENTITY_LIKE = "like:entity";
    public static final String PREFIX_ENTITY_MORE_LIKE_USER = "like_entity_user";
    public static final String PREFIX_ENTITY_MORE_LIKE = "like_entity";
    public static final String PREFIX_USER_LIKE = "like:user";

    @PostConstruct
    public void init() {
        redistemplate = redisTemplate;
    }


    public void setString(String key, Object value, long expire){
        ValueOperations<String, String> valueOperations = redisTemplateString.opsForValue();
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplateString.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void setString(String key,Object value) {
        setString(key, value, DEFAULT_EXPIRE);
    }


    public void setObject(String key, Object value){
        redisTemplate.opsForValue().set(key,value,DEFAULT_EXPIRE);
    }



    public <T> T get(String key, Class<T> clazz, long expire) {
        ValueOperations<String, String> valueOperations = redistemplate.opsForValue();
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redistemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        ValueOperations<String, String> valueOperations = redistemplate.opsForValue();
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redistemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String getString(String key) {
        return get(key, NOT_EXPIRE);
    }

    public Object getObject(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {

        redistemplate.delete(key);
    }

    public void expire(String key) {

        expire(key, DEFAULT_EXPIRE * 3);
    }

    public void thirtyDays(String key){
        expire(key,DEFAULT_EXPIRE * 30);
    }

    public void expire(String key, long expire) {
        if(expire != NOT_EXPIRE){
            redistemplate.expire(key, expire, TimeUnit.SECONDS);
        }else {
            redistemplate.expire(key, DEFAULT_EXPIRE * 3,TimeUnit.SECONDS);
        }
    }

    public void deleteKeys(String key){
        Set<String> keys = redisTemplate.keys(key+"*");
        redisTemplate.delete(keys);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public ZSetOperations<String, String> getzSetOperations() {
        return redistemplate.opsForZSet();
    }

    public ListOperations<String, String> getListOperations() {
        return redistemplate.opsForList();
    }

    public boolean setNx(String key, String value, long expire) {
        boolean res = redistemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), value.getBytes());
        if(res){
            redistemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return res;
    }

    /**
     * redis值自增
     * @param key
     * @param delta
     * @auth xiawenjun
     * @return
     */
    public static Long incr(String key, long delta){
        ValueOperations<String, String> operations = redistemplate.opsForValue();
        redistemplate.setKeySerializer(new StringRedisSerializer());
        redistemplate.setValueSerializer(new StringRedisSerializer());
        return operations.increment(key, delta);
    }

    // 某个实体的赞
    // oneLikeAndCancelAgain:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, Long entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
    // 某个用户的赞
    // oneLikeAndCancelAgain:user:userId -> int
    public static String getUserLikeKey(Long userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户对于某一实体的多个赞
     * @param bussType
     * @param userId
     * @return
     */
    public static String getEntityMoreLikeUserKey(int bussType,Long userId){
        return PREFIX_ENTITY_MORE_LIKE_USER+BOTTOM_LINE+bussType+BOTTOM_LINE+userId;
    }

    /**
     * 某一实体的获得的赞
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityMoreLikeKey(int entityType, Long entityId) {
        return PREFIX_ENTITY_MORE_LIKE + BOTTOM_LINE + entityType + BOTTOM_LINE + entityId;
    }

    public static void setList(String key,List list){

    }
}
