package com.hb0730.datasource.export.services.inter;

import com.hb0730.datasource.export.entity.TableEntity;
import com.hb0730.datasource.export.entity.TableInfoEntity;

import java.util.List;

/**
 * <p>
 * table services
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public interface TableServiceInter {

    /**
     * <p>
     * get all tables
     * </p>
     *
     * @return {@link List < TableEntity >}
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
