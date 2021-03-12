package com.hb0730.spring.boot.redis.stream.sample.stream;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author bing_huang
 * @see <a href="https://github.com/spring-projects/spring-data-redis/blob/master/src/test/java/org/springframework/data/redis/core/DefaultStreamOperationsIntegrationTests.java">spring data redis stream junit</a>
 */
@Component
public class RedisStreamService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisStreamService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * XADD 将新条目追加到流,
     *
     * @param key  redis key
     * @param maps 流数据
     * @see <a href="https://redis.io/commands/xadd">XADD</a>
     */
    public void add(String key, Map<?, ?> maps) {
        RecordId recordId = redisTemplate.opsForStream().add(key, maps);
        Long sequence = recordId.getSequence();
    }

    /**
     * XADD，将新条目追加到流,
     *
     * @param key      key
     * @param recordId record id  format  'millisecondsTime-sequenceNumber'
     * @param value    value (Json)
     * @see <a href="https://redis.io/commands/xadd">XADD</a>
     */
    public void add(String key, String recordId, Map<?, ?> value) {
        MapRecord<String, ?, ?> record = StreamRecords.newRecord()
                .in(key)
                .withId(RecordId.of(recordId))
                .ofMap(value);
        redisTemplate.opsForStream().add(record);
    }


    /**
     * XLEN,返回流中的条目数,如果指定的键不存在，该命令将返回零，
     *
     * @param key key
     * @return
     * @see <a href="https://redis.io/commands/xlen">XLEN</a>
     */
    public Long len(String key) {
        return redisTemplate.opsForStream().size(key);
    }

    /**
     * 返回与给定ID范围匹配的流条目。该范围由最小和最大ID指定
     *
     * @param key
     * @param from
     * @param to
     * @return
     * @see <a href="https://redis.io/commands/xrange">XRANGE</a>
     */
    public List<MapRecord<String, Object, Object>> rangeRightOpen(String key, String from, String to) {
        return redisTemplate.opsForStream().range(key, Range.rightOpen(from, to));
    }

    /**
     * 返回与给定ID范围匹配的流条目。该范围由最小和最大ID指定
     *
     * @param key
     * @param from
     * @param to
     * @return
     * @see <a href="https://redis.io/commands/xrange">XRANGE</a>
     */
    public List<MapRecord<String, Object, Object>> rangeLeftOpen(String key, String from, String to) {
        return redisTemplate.opsForStream().range(key, Range.leftOpen(from, to));
    }

    /**
     * read
     *
     * @param key
     * @return
     * @see <a href="https://redis.io/commands/xread">XREAD</a>
     */
    public List<MapRecord<String, Object, Object>> read(String key) {
        return redisTemplate.opsForStream().read(StreamOffset.create(key, ReadOffset.from("0-0")));
    }
}
