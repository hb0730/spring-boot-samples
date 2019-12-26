package cn.hb0730.datasource.export.mapper;

import cn.hb0730.datasource.export.entity.TableEntity;
import cn.hb0730.datasource.export.entity.TableInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * get tables and tables info
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Mapper
public interface TableMapper {
    /**
     * <p>
     * get all tables
     * </p>
     *
     * @return {@link List<Map>}
     */
    List<TableEntity> listTable();

    /**
     * <p>
     * get tables info
     * </p>
     *
     * @param tableName table name
     * @return table info
     */
    List<TableInfoEntity> listTableColumn(String tableName);
}
