package com.hb0730.datasource.export.dto.easypoi;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * table and column
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Data
@ExcelTarget("tableColumn")
public class TableColumnDTO implements Serializable {
    /**
     * 表名
     */
    private String table_name;
    /**
     * 所有列
     */
    private List<ColumnDTO> columns;
}
