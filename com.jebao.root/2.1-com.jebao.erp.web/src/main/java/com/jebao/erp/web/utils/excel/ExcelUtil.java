package com.jebao.erp.web.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jack on 2016/11/28.
 */
public class ExcelUtil {

    /**
     * 读取Excel内容到一个列表
     *
     * @param filePath 文件全路径
     * @return List格式的数据表格
     */
    public List<Object[]> readToList(String filePath) {
        List<Object[]> rowList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            return readToList(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowList;
    }
    public List<Object[]> readToList(InputStream fileInputStream) {
        List<Object[]> rowList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            //region 读取sheet数据填充 list
            int columnCount = 0;//设置列数
            for (Row row : sheet) {
                if (row == null) continue;
                if (columnCount == 0) {
                    columnCount = row.getLastCellNum();
                }
                Object[] rowObj = new Object[columnCount];//保证每行所存储的单元格数量相同

                //row.iterator 会跳过空单元格，为保证每行的列数相同，循环列数量次
                for (int i = 0; i < columnCount; i++) {
                    Cell cell = row.getCell(i);
                    Object value = getCellValue(cell, formulaEvaluator);
                    rowObj[i] = value;
                }
                rowList.add(rowObj);
            }
            //endregion

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowList;
    }
    /**
     * 读取Excel内容到一个key,value列表
     *
     * @param filePath 文件全路径
     * @return 带列名的key，value格式的数据表格
     */
    public List<HashMap<String, Object>> readFileToKv(String filePath) {

        List<HashMap<String, Object>> rowList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            return readFileToKv(fileInputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowList;
    }
    public List<HashMap<String, Object>> readFileToKv(InputStream fileInputStream) {

        List<HashMap<String, Object>> rowList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

            List<String> columnNameList = new ArrayList<>();
            int columnNameRowIndex = 0;
            for (Row row : sheet) {
                if (row == null) continue;
                columnNameRowIndex = row.getRowNum();
                for (Cell cell : row) {
                    columnNameList.add(cell.getStringCellValue());
                }
                break;
            }

            //region 读取sheet数据填充 list
            for (Row row : sheet) {
                if (row == null || row.getRowNum() == columnNameRowIndex) continue;
                HashMap<String, Object> rowMap = new HashMap<>();

                for (int i = 0; i < columnNameList.size(); i++) {
                    String key = columnNameList.get(i);
                    Cell cell = row.getCell(i);
                    Object value = getCellValue(cell, formulaEvaluator);
                    rowMap.put(key, value);
                }
                rowList.add(rowMap);
            }
            //endregion

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowList;
    }
    /**
     * 获取单元格值
     */
    private Object getCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
        Object value = null;
        if (cell == null) {
            value = "";
        } else {
            //region 根据单元格类型取值
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    value = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    value = cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    value = formulaEvaluator.evaluate(cell).formatAsString();
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                default:
                    value = "";
                    break;
            }
        }
        return value;
    }

    /**
     * 导出Excel
     * @param response HttpServletResponse
     * @param fileName 导出文件名
     * @param dataList 导出数据list
     * @throws Exception
     */
    public void outputFile(HttpServletResponse response, String fileName, List dataList) throws Exception {
        if (dataList == null || dataList.size() == 0) return;

        //创建XSSFWorkbook对象(excel的文档对象)
        XSSFWorkbook workbook = new XSSFWorkbook();
//建立新的sheet对象（excel的表单）
        XSSFSheet sheet = workbook.createSheet("sheet1");

        List<String> columnNameList = new ArrayList<>(); //excel 列名
        List<String> columnCodeList = new ArrayList<>(); //对应列名的字段名
        //region 设置 列头
        Field[] fields = dataList.get(0).getClass().getDeclaredFields(); // 获取字段
        for (int i=0;i<fields.length;i++) {
            Field field =fields[i];
           /* if (field.isAnnotationPresent(Description.class)) {
                Description desc = field.getAnnotation(Description.class);
                columnNameList.add(desc.value());
                columnCodeList.add(field.getName());
            }*/
        }
        //创建列头
        XSSFRow firstRow = sheet.createRow(0);
        for (int i=0;i<columnNameList.size();i++){
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(columnNameList.get(i));
        }
        //endregion

        //region 设置 数据行
        for (int i=0;i<dataList.size();i++){
            Object dataItem = dataList.get(i);
            XSSFRow row = sheet.createRow(i + 1); //列名了已经占据了第一行

            for (int j=0;j<columnCodeList.size();j++){
                XSSFCell cell = row.createCell(j);
                Method[] methods = dataItem.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    String methodName = method.getName().toLowerCase();
                    if (methodName.startsWith("get") && methodName.endsWith(columnCodeList.get(j).toLowerCase())){
                        Object cellValue = method.invoke(dataItem,null);
                        cell.setCellValue(cellValue == null ? "":cellValue.toString());
                        break;
                    }
                }

            }
        }
        //endregion

        // 输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.flush();
        output.close();
    }


}
