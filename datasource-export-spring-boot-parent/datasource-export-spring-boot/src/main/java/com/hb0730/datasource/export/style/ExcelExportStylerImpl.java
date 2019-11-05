package com.hb0730.datasource.export.style;

import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.*;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class ExcelExportStylerImpl extends ExcelExportStylerDefaultImpl {

    public ExcelExportStylerImpl(Workbook workbook) {
        super(workbook);
    }

    /**
     * <p>
     * 列表头样式
     * </p>
     *
     * @param color
     * @return
     */
    @Override
    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setFillBackgroundColor(IndexedColors.TEAL.getIndex());
        return titleStyle;
    }
}
