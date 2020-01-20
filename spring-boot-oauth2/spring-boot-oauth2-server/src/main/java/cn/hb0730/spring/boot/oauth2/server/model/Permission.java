package cn.hb0730.spring.boot.oauth2.server.model;

import java.io.Serializable;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class Permission implements Serializable {
    // 权限id
    private int permissionId;
    // 权限名称
    private String permissionName;
    //权限缩写
    private String permissionAbbr;

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionAbbr() {
        return permissionAbbr;
    }

    public void setPermissionAbbr(String permissionAbbr) {
        this.permissionAbbr = permissionAbbr;
    }
}
