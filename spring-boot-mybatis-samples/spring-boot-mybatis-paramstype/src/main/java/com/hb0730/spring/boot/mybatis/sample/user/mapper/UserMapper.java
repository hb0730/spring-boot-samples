package com.hb0730.spring.boot.mybatis.sample.user.mapper;

import com.hb0730.spring.boot.mybatis.sample.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author bing_huang
 */
public interface UserMapper {

    List<User> findUser(Map<String, Object> params);

    List<User> findUserByStringArray(String ids);
}
