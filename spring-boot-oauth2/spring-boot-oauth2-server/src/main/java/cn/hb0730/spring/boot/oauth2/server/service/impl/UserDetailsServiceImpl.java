package cn.hb0730.spring.boot.oauth2.server.service.impl;

import cn.hb0730.spring.boot.oauth2.server.mapper.PermissionMapper;
import cn.hb0730.spring.boot.oauth2.server.mapper.UserMapper;
import cn.hb0730.spring.boot.oauth2.server.model.Permission;
import cn.hb0730.spring.boot.oauth2.server.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserMapper userService;
    private PermissionMapper permissionService;

    public UserDetailsServiceImpl(UserMapper userService, PermissionMapper permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if (user != null) {
            List<Permission> permissions = permissionService.findPermissionByUserId(user.getUserId());
            user.setPermissions(permissions);
            return user;
        }
        return null;
    }
}
