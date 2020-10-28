package com.hb0730.spring.boot.dynamic.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台资源表
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UmsResource implements Serializable {
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源url
     */
    private String url;
    /**
     * 资源描述
     */
    private String description;
    /**
     * 资源分组
     */
    private Long categoryId;
}
