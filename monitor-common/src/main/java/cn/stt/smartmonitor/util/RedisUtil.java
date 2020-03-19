package cn.stt.smartmonitor.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于jedisClient 的Redis帮助工具
 *
 * @author shitongtong 2018年11月12日
 */

public class RedisUtil {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 默认缓存时间：7天
     */
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @param expireTime ms
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 正则获取key集合
     *
     * @param pattern
     * @return
     */
    public Set<String> getKeys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public <T> T get(final String key, Class<T> t) {
        T result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = (T) operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(final String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * hash 批量添加
     *
     * @param key
     * @param map
     */
    public void hashMultiSet(final String key, Map<?, ?> map) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
    }

    /**
     * 先删除key,再批量hash添加
     *
     * @param key
     * @param map
     */
    public void deleteAndHashMultiSet(final String key, Map<?, ?> map) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(key);
                operations.opsForHash().putAll(key, map);
                Object val = operations.exec();
                return val;
            }
        });
    }


    /**
     * 获取hash-value数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(final String key, final Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 获取hash-value数据
     *
     * @param key
     * @param hashKey
     * @return string
     */
    public String hmGet(final String key, final String hashKey) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }


    /**
     * 根据key获取所有哈希数据
     *
     * @param key
     * @return
     */
    public Map<String, String> hmGetAll(final String key) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        Map<String, String> entries = hash.entries(key);
        return entries;
    }

    /**
     * 删除hash字段
     *
     * @param key
     * @param hashKey
     * @return
     */
    public long hmDel(final String key, final String hashKey) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.delete(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表删除
     *
     * @param key
     * @param value
     * @return 被移除元素的数量。 列表不存在时返回 0 。
     */
    public long lRemove(final String key, final Object value) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        return listOperations.remove(key, 0, value);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 添加集合元素
     *
     * @param key
     * @param value
     * @return 添加的数量
     */
    public Long sAdd(final String key, Object... value) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.add(key, value);
    }

    /**
     * 获取集合元素
     *
     * @param key
     * @return
     */
    public Set<Object> sMembers(final String key) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    /**
     * 删除集合元素
     *
     * @param key
     * @param value
     * @return 被删除的数量
     */
    public Long sRemove(final String key, Object... value) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.remove(key, value);
    }

    /**
     * 获取集合数量
     *
     * @param key
     * @return
     */
    public Long sSize(final String key) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        return setOperations.size(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key, Object value, double score) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, score);
    }

    /**
     * 获取min到max分数的集合
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> rangeByScore(String key, double min, double max) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, min, max);
    }

    /**
     * 获取start到end的集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = zset.rangeWithScores(key, start, end);
        return typedTuples;
    }

    /**
     * 删除zset集合元素
     *
     * @param key
     * @param value
     * @return
     */
    public long zrem(String key, String value) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.remove(key, value);
    }

    /**
     * 获取多层hash
     *
     * @param key
     * @param innerKeys
     * @return
     */
    public List<Object> hashMultiGet(String key, Collection<String> innerKeys) {
        List<Object> result = null;
        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
        result = operations.multiGet(key, innerKeys);
        return result;
    }

    /**
     * 获取list start到end的数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> listGet(final String key, long start, long end) {
        List<Object> result = null;
        ListOperations<String, Object> operations = redisTemplate.opsForList();
        result = operations.range(key, start, end);
        return result;
    }


    /**
     * 获取全部list数据
     *
     * @param key
     * @return
     */
    public List<Object> listGet(String key) {
        ListOperations<String, Object> operations = redisTemplate.opsForList();
        return operations.range(key, 0, operations.size(key));
    }


    /**
     * 存list
     *
     * @param key
     * @param values
     */
    public void listSet(String key, Collection<Object> values) {
        ListOperations<String, Object> operations = redisTemplate.opsForList();
        operations.rightPushAll(key, values);
    }

    /**
     * 先删除再存list
     *
     * @param key
     * @param values
     */
    public void deleteAndListSet(String key, Collection<Object> values) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(key);
                operations.opsForList().rightPushAll(key, values);
                return operations.exec();
            }
        });
    }

    /**
     * hashKey增量计数
     *
     * @param key
     * @param hashKey
     * @param increment
     * @return
     */
    public Long hashIncr(final String key, String hashKey, long increment) {
        HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
        Long result = operations.increment(key, hashKey, increment);
        return result;
    }

    /**
     * 设置缓存时间，单位：ms
     *
     * @param key
     * @param expireTime
     * @return
     */
    public Boolean setExpireTime(final String key, long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置缓存时间，默认7天
     *
     * @param key
     * @return
     */
    public Boolean setExpireTime(final String key) {
        return redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 加1
     *
     * @param key
     * @return 加1后数量
     */
    public long incr(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key);
    }

    /**
     * 减1
     *
     * @param key
     * @return 减1后数量
     */
    public long decr(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.decrement(key);
    }
}
