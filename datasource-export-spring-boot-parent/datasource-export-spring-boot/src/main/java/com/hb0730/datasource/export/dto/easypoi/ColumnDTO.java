package com.hb0730.datasource.export.dto.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * column
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Data
@ExcelTarget("column")
public class ColumnDTO implements Serializable {
    private static final long serialVersionUID = -21464551859527243L;
    /**
     * column name
     */
    @Excel(name = "列名")
    private String column_name;
    /**
     * ordinal position
     */
    private String ordinal_position;
    /**
     * column default
     */
    private String column_default;
    /**
     * is null
     */
    @Excel(name = "是否为空")
    private String is_nullable;
    /**
     * data type
     */
    @Excel(name = "数据类型")
    private String data_type;
    /**
     * character maxiMun length
     */
    @Excel(name = "长度")
    private String character_maximum_length;
    /**
     * character octet length
     */
    private String character_octet_length;
    /**
     * column type
     */
    private String column_type;
    /**
     * column comment
     */
    @Excel(name = "列说明")
    private String column_comment;
}
