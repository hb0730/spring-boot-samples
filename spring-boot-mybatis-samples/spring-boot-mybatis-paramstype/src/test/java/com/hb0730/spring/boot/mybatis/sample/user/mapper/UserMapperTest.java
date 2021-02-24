package com.hb0730.spring.boot.mybatis.sample.user.mapper;

import com.hb0730.spring.boot.mybatis.sample.MybatisParamsTypeApplication;
import com.hb0730.spring.boot.mybatis.sample.user.model.User;
import com.hb0730.spring.boot.mybatis.sample.user.service.UserService;
import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisParamsTypeApplication.class)
@ActiveProfiles("dev")
public class UserMapperTest {
    @Autowired
    private UserService service;

    @Test
    public void findUserTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", Arrays.asList(Arrays.array(1, 2, 3)));
        params.put("deleted", 0);
        List<User> user = service.findUser(params);
    }

    @Test
    public void findUserTest2() {
        Map<String, Object> params = new HashMap<>();
        params.put("types", Arrays.asList(Arrays.array(1, 2, 3)));
        params.put("deleted", 0);
        List<User> user = service.findUser(params);
    }

    @Test
    public void findUserTest3() {
        Map<String, Object> params = new HashMap<>();
        params.put("names", Arrays.asList(Arrays.array("张三2", "张三3", "张三4")));
        params.put("deleted", 0);
        List<User> user = service.findUser(params);
    }

    @Test
    public void findUserByStringArrayTest() {
        String ids = String.join(",", Lists.newArrayList("1", "2", "3", "4"));
        List<User> userByStringArray = service.findUserByStringArray(ids);
        Assert.assertNotNull(userByStringArray);
    }
}
