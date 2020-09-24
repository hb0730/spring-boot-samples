package com.hb0730.datasource.export.services;

import com.hb0730.datasource.export.services.inter.TableServiceInter;
import com.hb0730.datasource.export.entity.TableEntity;
import com.hb0730.datasource.export.entity.TableInfoEntity;
import com.hb0730.datasource.export.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * tableService impl
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Service
public class TableServiceImpl implements TableServiceInter {
    @Autowired
    private TableMapper mapper;

    @Override
    public List<TableEntity> listTable() {
        return mapper.listTable();
    }

    @Override
    public List<TableInfoEntity> listTableColumn(@NonNull String tableName) {
        TableEntity e=new TableEntity();
        return mapper.listTableColumn(tableName);
    }

}
