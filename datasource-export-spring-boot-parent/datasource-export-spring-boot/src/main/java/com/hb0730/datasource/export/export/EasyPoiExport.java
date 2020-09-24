package com.hb0730.datasource.export.export;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.hb0730.datasource.export.dto.easypoi.ColumnDTO;
import com.hb0730.datasource.export.dto.easypoi.TableDTO;
import com.hb0730.datasource.export.entity.TableEntity;
import com.hb0730.datasource.export.services.inter.TableServiceInter;
import com.hb0730.datasource.export.utils.SpringContextUtil;
import com.hb0730.datasource.export.entity.TableInfoEntity;
import com.hb0730.datasource.export.style.ExcelExportStylerImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * easyPoi Export
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class EasyPoiExport {

    public static void export() throws IOException {
        List<Map<String, Object>> data = getData();
        Workbook workbook = ExcelExportUtil.exportExcel(data, ExcelType.HSSF);
        String path = System.getProperty("user.dir");
        FileOutputStream fos = new FileOutputStream(path + "/tesxt.xls");
        workbook.write(fos);
        fos.close();
    }


    public static List<Map<String, Object>> getData() {
        TableServiceInter services = SpringContextUtil.getBean(TableServiceInter.class);
        List<TableEntity> tables = services.listTable();
        List<Map<String, Object>> maps = new ArrayList<>();
        //表说明
        List<TableDTO> dtos = new ArrayList<>();
        tables.stream().forEach(table -> {
            TableDTO dto = new TableDTO();
            BeanUtils.copyProperties(table, dto);
            dtos.add(dto);
        });
        Map<String, Object> tableMap = new HashMap();
        ExportParams exportParams = new ExportParams();
        exportParams.setTitle("表说明");
        exportParams.setStyle(ExcelExportStylerImpl.class);
        tableMap.put("title", exportParams);
        tableMap.put("entity", TableDTO.class);
        tableMap.put("data", dtos);
        maps.add(tableMap);
        tables.stream().forEach(info -> {
            Map map = new HashMap<>();
            String table_name = info.getTable_name();
            List<TableInfoEntity> tableInfos = services.listTableColumn(table_name);
            List<ColumnDTO> columns = new ArrayList<>();
            tableInfos.stream().forEach(column -> {
                ColumnDTO columnDTO = new ColumnDTO();
                BeanUtils.copyProperties(column, columnDTO);
                columns.add(columnDTO);
            });
            ExportParams params = new ExportParams();
            params.setTitle(info.getTable_comment());
            params.setSheetName(table_name);
            params.setStyle(ExcelExportStylerImpl.class);
            map.put("title", params);
            map.put("entity", ColumnDTO.class);
            map.put("data", columns);
            maps.add(map);
        });

        return maps;
    }
}
