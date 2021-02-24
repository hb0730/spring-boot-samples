package com.hb0730.spring.boot.mybatis.sample.user.service;

import com.hb0730.spring.boot.mybatis.sample.user.mapper.UserMapper;
import com.hb0730.spring.boot.mybatis.sample.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<User> findUser(Map<String, Object> params) {
        return userMapper.findUser(params);
    }

    public List<User> findUserByStringArray(String ids) {
        return userMapper.findUserByStringArray(ids);
    }
}
