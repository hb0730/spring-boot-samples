package com.hb0730.spring.boot.redis.stream.sample.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class RedisStreamServiceTest {
    @Autowired
    private RedisStreamService service;

    @Test
    public void add() {
        Map<String, String> params = new HashMap<>();
        params.put("test1", "value1");
        params.put("test2", "value2");
        service.add("stream-1", params);
    }

    @Test
    public void testAdd() {
        Map<String, String> params = new HashMap<>();
        params.put("test1", "value1");
        params.put("test2", "value2");
        String id = String.valueOf(new Date().getTime());
        service.add("stream-1", id + "-" + 10, params);
    }

    /**
     * 测试数据
     */
    @Test
    public void testAdd1() {
        Map<String, String> params = new HashMap<>();
        params.put("test1", "value1");
        params.put("test2", "value2");
        for (int i = 0; i < 10; i++) {
            service.add("stream-2", "1001011-" + i, params);
        }
    }

    /**
     * 测试数据
     */
    @Test
    public void testAdd2() {
        Map<String, String> params = new HashMap<>();
        params.put("test1", "value1");
        params.put("test2", "value2");
        for (int i = 0; i < 10; i++) {
            service.add("stream-2", "1001012-" + i, params);
        }
    }


    @Test
    public void len() {
        Long len = service.len("stream-1");
        log.info(len + "");
    }


    @Test
    public void rangeRightOpen() {
        //all
        List<MapRecord<String, Object, Object>> mapRecords = service.rangeRightOpen("stream-2", "-", "+");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeRightOpen("stream-2", "1001011-3", "1001011-6");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeRightOpen("stream-2", "1001011", "1001011-6");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeRightOpen("stream-2", "1001011", "1001012-6");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeRightOpen("stream-2", "1001011-3", "1001012");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeRightOpen("stream-2", "1001011-5", "1001011-3");
        log.info(mapRecords.size() + "");
    }

    @Test
    public void rangeLeftOpenTest() {
        //all
        List<MapRecord<String, Object, Object>> mapRecords = service.rangeLeftOpen("stream-2", "-", "+");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeLeftOpen("stream-2", "1001011-3", "1001011-6");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeLeftOpen("stream-2", "101011-3", "1001011");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeLeftOpen("stream-2", "101011-3", "1001012");
        log.info(mapRecords.size() + "");
        mapRecords = service.rangeLeftOpen("stream-2", "1001011-5", "1001011-3");
        log.info(mapRecords.size() + "");
    }

    @Test
    public void readTest() {
        List<MapRecord<String, Object, Object>> read = service.read("stream-2");
        log.info(read.size() + "");
    }
}
