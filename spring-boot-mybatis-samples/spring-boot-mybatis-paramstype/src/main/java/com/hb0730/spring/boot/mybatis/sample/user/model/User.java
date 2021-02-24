package com.hb0730.spring.boot.mybatis.sample.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bing_huang
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private String type;
    private Integer deleted;
}
