package com.hb0730.spring.boot.oauth2.server.mapper;

import com.hb0730.spring.boot.oauth2.server.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Mapper
@Repository
public interface UserMapper {
    @Select("select * from xb_user where username = #{userName}")
    User findUserByUserName(String userName);


    @Select("select * from xb_user")
    List<User> findAllUser();

    @Update("update xb_user set session_id = #{sessionId} where user_id = #{userId}")
    void updateUserSessionIdByUserId(@Param("userId") int userId, @Param("sessionId") String sessionId);

    @Select("select session_id from xb_user where user_id=#{userId}")
    String getSesssionIdByUserId(int userId);
}
