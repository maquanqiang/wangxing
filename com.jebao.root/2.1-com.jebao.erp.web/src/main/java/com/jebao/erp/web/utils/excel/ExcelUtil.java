package com.jebao.erp.web.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jack on 2016/11/28.
 */
public class ExcelUtil {

    /**
     * 读取Excel内容到一个列表
     * @param filePath 文件全路径
     * @return List格式的数据表格
     */
    public List<Object[]> readFile(String filePath){
        List<Object[]> rowList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            //region 读取sheet数据填充 list
            int columnCount = 0;//设置列数
            for (Row row : sheet){
                if (row==null)continue;
                if (columnCount==0){columnCount = row.getLastCellNum();}
                Object[] rowObj = new Object[columnCount];//保证每行所存储的单元格数量相同

                //row.iterator 会跳过空单元格，为保证每行的列数相同，循环列数量次
                for (int i=0;i<columnCount;i++){
                    Cell cell = row.getCell(i);
                    Object value = getCellValue(cell,formulaEvaluator);
                    rowObj[i]=value;
                }
                rowList.add(rowObj);
            }
            //endregion

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowList;
    }

    /**
     * 读取Excel内容到一个key,value列表
     * @param filePath 文件全路径
     * @return 带列名的key，value格式的数据表格
     */
    public List<HashMap<String,Object>> readFileToKv(String filePath){

        List<HashMap<String,Object>> rowList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

            List<String> columnNameList = new ArrayList<>();
            int columnNameRowIndex=0;
            for (Row row : sheet){
                if (row==null)continue;
                columnNameRowIndex = row.getRowNum();
                for (Cell cell : row){
                    columnNameList.add(cell.getStringCellValue());
                }
                break;
            }

            //region 读取sheet数据填充 list
            for (Row row : sheet){
                if (row==null || row.getRowNum()==columnNameRowIndex)continue;
                HashMap<String,Object> rowMap = new HashMap<>();

                for (int i=0;i<columnNameList.size();i++){
                    String key = columnNameList.get(i);
                    Cell cell = row.getCell(i);
                    Object value = getCellValue(cell,formulaEvaluator);
                    rowMap.put(key,value);
                }
                rowList.add(rowMap);
            }
            //endregion

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowList;
    }

    /**
     * 获取单元格值
     */
    private Object getCellValue(Cell cell,FormulaEvaluator formulaEvaluator) {
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



}
