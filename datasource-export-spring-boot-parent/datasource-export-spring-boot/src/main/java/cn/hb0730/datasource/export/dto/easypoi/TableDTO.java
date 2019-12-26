package cn.hb0730.datasource.export.dto.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * table
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Data
@ExcelTarget("tables")
public class TableDTO implements Serializable {
    private static final long serialVersionUID = 718236427385275713L;
    @Excel(name = "表名")
    private String table_name;
    @Excel(name = "表说明")
    private String table_comment;
}
