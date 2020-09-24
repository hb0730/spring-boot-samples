package com.hb0730.spring.boot.oauth2.server.model;

import java.io.Serializable;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class Role implements Serializable {
    // 角色id
    private int roleId;
    // 角色名称
    private String roleName;
    // 角色缩写英文
    private String roleAbbr;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleAbbr() {
        return roleAbbr;
    }

    public void setRoleAbbr(String roleAbbr) {
        this.roleAbbr = roleAbbr;
    }
}
