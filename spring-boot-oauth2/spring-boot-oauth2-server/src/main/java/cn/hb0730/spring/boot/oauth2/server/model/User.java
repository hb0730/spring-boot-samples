package cn.hb0730.spring.boot.oauth2.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class User implements Serializable, UserDetails {
    private int userId;
    private String username;
    private String password;
    private int mobile;
    private int sex;
    private boolean enabled;
    private List<Permission> permissions;

    // 帐户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    // 帐户是否被冻结
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }
    // 帐户密码是否过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }
    // 帐号是否可用
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(permissions != null) {
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionAbbr()));
            }
        }
        return authorities;
    }

    public User() {
    }

    public User(String username, String password, List<Permission> permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getUserId() {
        return userId;
    }

    @Override public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
