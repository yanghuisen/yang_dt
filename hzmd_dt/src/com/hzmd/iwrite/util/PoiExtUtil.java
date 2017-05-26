package com.hzmd.iwrite.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hzmd.common.util.DateUtil;
import com.hzmd.common.util.FileUtil;
import com.hzmd.common.util.StringUtil;
import com.hzmd.iwrite.global.ITestCC;
import com.hzmd.iwrite.web.config.G;

public class PoiExtUtil {

  /**
   * 读取excel
   * @param filename 导入的文件名
   * @param dataFromRow  从第几行开始导入
   * @param newArs 行头信息 （名称，编码，无用列[code,name,'']）
   * @return
   */
  public static List<Map<String, Object>> ReadExcel(String filename, int dataFromRow, String[] newArs) {
      String suffix = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
      return readExcelBySuffix(filename, dataFromRow, newArs, suffix);
  }

  /**
   * 根据后缀判断版本，读取excel表格
   * @param filename
   * @param dataFromRow
   * @param newArs
   * @param suffix
   * @return
   */
  private static List<Map<String, Object>> readExcelBySuffix(String filename, int dataFromRow, String[] newArs, String suffix) {
    //定义一个list，保存解析后的数据
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    Map<String, Object> rowMap = null;
    try {
      filename = G.uc().getUploadTempDirPhsicalPath()+File.separator+filename;
      Workbook wookbook = null;
      if (ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2003.equals(suffix)) {
          wookbook = new HSSFWorkbook(new FileInputStream(filename));
      }else if (ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2007.equals(suffix)) {
          wookbook = new XSSFWorkbook(new FileInputStream(filename));
      }
      Sheet sheet = wookbook.getSheetAt(0);
      int rows = sheet.getPhysicalNumberOfRows(); //获取总的行数
      for (int i = dataFromRow - 1; i < rows; i++) {
        Row row = sheet.getRow(i); //获取每一行
        //每一行是一个map
        rowMap = new ConcurrentHashMap<String, Object>();
        if (row != null) {
          int forlen = newArs.length;
          for (int j = 0; j < forlen; j++) {
            String headName = newArs[j];
            Cell cell = row.getCell(j);
            if (cell != null) {
              switch (cell.getCellType()) {
              case HSSFCell.CELL_TYPE_FORMULA:
                FormulaEvaluator evaluator = wookbook.getCreationHelper().createFormulaEvaluator();
                Integer types = null;
                try {
                  types = evaluator.evaluateFormulaCell(cell);
                } catch (Exception e) {
                  rowMap.put(headName, "");
                  break;
                }
                switch (types) {
                  case HSSFCell.CELL_TYPE_NUMERIC:
                    String cellValueStr = "";
                    short cellValueStrFormat = cell.getCellStyle().getDataFormat();
                    if (cellValueStrFormat == 14 || cellValueStrFormat == 31 || cellValueStrFormat == 57 || cellValueStrFormat == 58) { //日期格式
                      cellValueStr = DateUtil.formatQuietly(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), "yyyy-MM-dd");
                    } else {
                      cellValueStr = getRightStr(cell.getNumericCellValue() + "");
                    }
                    rowMap.put(headName, cellValueStr);
                    break;
                  case HSSFCell.CELL_TYPE_STRING:
                    rowMap.put(headName, StringUtil.isNotEmpty(cell.getStringCellValue())?cell.getStringCellValue().trim():"");
                    break;
                  default:
                    break;
                }
                break;
              case HSSFCell.CELL_TYPE_NUMERIC:
                String value = "";
                short format = cell.getCellStyle().getDataFormat();
                if (format == 14 || format == 31 || format == 57 || format == 58 || HSSFDateUtil.isCellDateFormatted(cell)) { //日期格式
                  value = DateUtil.formatQuietly(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), "yyyy-MM-dd");
                } else {
                  value = getRightStr(cell.getNumericCellValue() + "");
                }
                rowMap.put(headName, value);
                break;
              case HSSFCell.CELL_TYPE_STRING:
                rowMap.put(headName, StringUtil.isNotEmpty(cell.getStringCellValue())?cell.getStringCellValue().trim():"");
                break;
              default:
                break;
              }
            } else {
              rowMap.put(headName, "");
            }
          }
          if(rowMap.size()>0){
            dataList.add(rowMap);
          }
        }
      }
      return dataList;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 读取文本域textArea
   * @param dataContent textArea字符串
   * @param newArs 行头信息 （名称，编码，无用列[code,name,'']）
   * @param flag  判断是账户导入还是班级导入  0 --账户导入  1 --班级导入
   * @return
   */
  public static List<Map<String, Object>> ReadTextArea(String dataContent, String[] newArs,int flag) {
    //文本域字符串
    List<Map<String, Object>> dataList = null;
    //判断页面是否有传值
    if (null == dataContent || "".equals(dataContent) || dataContent.length() <= 0) {

    } else {
      dataList = new ArrayList<Map<String, Object>>();
      String[] rowLine = dataContent.split("\r\n");
      for (int i = 0; i < rowLine.length; i++) {
        Map<String, Object> rowMap = new ConcurrentHashMap<String, Object>();
        String zbf = " ";
        if(0==flag){  //账户导入，不判断逗号的情况
          if (rowLine[i].contains("\t")) {
            zbf = "\t";
          }
        }else{
          if (rowLine[i].contains(",")) {
            zbf = ",";
          }else if (rowLine[i].contains("\t")) {
            zbf = "\t";
          }else if (rowLine[i].contains(" ")) {
            zbf = " ";
          }
        }
        String[] cellLine = rowLine[i].split(zbf);
        List<String> cellNew = new ArrayList<String>();
        if(cellLine.length>0){
          for (String newVal : cellLine) {
            if ("" == newVal || null == newVal || newVal.length() <= 0) {
              continue;
            }
            cellNew.add(newVal);
          }
        }
        for (int j = 0; j < cellNew.size() && j < newArs.length; j++) {
          String headName = newArs[j];
          String value = cellNew.get(j);
          rowMap.put(headName, value);
        }
        dataList.add(rowMap);
      }
    }
    return dataList;
  }

  /**
   * 处理数字带0的情况
   * @param sNum
   * @return
   */
  private static String getRightStr(String sNum) {
    DecimalFormat df = new DecimalFormat("#.000000");
    String resultStr = df.format(new Double(sNum));
    if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
      resultStr = resultStr.substring(0, resultStr.indexOf("."));
    }
    return resultStr;
  }

  /**
   * excelExport2 
   * @param maps
   * @param list
   * @param file
   * @return
   */
  public static boolean excelExport(Map<String, String> maps, List<Map<String, Object>> list, File file) {
    try {
      FileUtil.ensureParentFileExists(file);
      Workbook wb = null;
      String filename = file.getName();
      String type = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
      if (type.equals(ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2003)) {
        wb = new HSSFWorkbook();
      }
      if (type.equals(ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2007)) {
        wb = new XSSFWorkbook();
      }
      CreationHelper createHelper = wb.getCreationHelper();
      Sheet sheet = wb.createSheet("sheet1");
      Set<String> sets = maps.keySet();
      Row row = sheet.createRow(0);
      int i = 0;
      // 定义表头
      for (Iterator<String> it = sets.iterator(); it.hasNext();) {
        String key = it.next();
        Cell cell = row.createCell(i++);
        cell.setCellValue(createHelper.createRichTextString(maps.get(key)));
      }
      // 填充表单内容
      float avg = list.size() / 20f;
      int count = 1;
      for (int j = 0; j < list.size(); j++) {
        Map<String, Object> map = list.get(j);
        int index = 0;
        Row row1 = sheet.createRow(j + 1);
        for (Iterator<String> it = sets.iterator(); it.hasNext();) {
          String key = it.next();
          Object value = map.get(key);
          if (null == value || "null".equals(value)) {
            value = "";
          }
          Cell cell = row1.createCell(index++);
          cell.setCellValue(value + "");
        }
        if (j > avg * count) {
          count++;
        }
        if (count == 20) {
          count++;
        }
      }
      FileOutputStream fileOut = new FileOutputStream(file);
      wb.write(fileOut);
      fileOut.close();

    } catch (Exception e) {
      //e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 拼接文件路径
   */
  public static String getExportFilePathAndName(String table) {
//    int num = RandomUtil.randomInt(1000, 10000);
    String date = DateUtil.formatQuietly(new Date(), "yyyyMMddHHmmss");
    String dir = ITestCC.exportCC.bc_all.getName(table);
   // return "export/" + dir + "/" + table + "_" + uid + "_" + date + "_" + num + "." + ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2003;
    return "export/" + dir + "/" + table + date + "." + ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2003;
  }

  /**
  *
  * @param Map
  *            <String,String> maps 属性表，成员属性age为KEY，中文名称为VALUE
  * @param List
  *            <T> list 需要导出的数据列表对象
  * @param File
  *            file 指定输出文件位置，只能导出excel2003以上版本
  *           
  * @return true 导出成功 false 导出失败
  
  public static <T> boolean excelExport(Map<String, String> maps, List<T> list, File file) {
    try {
      Workbook wb = null;
      String filename = file.getName();
      String type = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
      if (type.equals(ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2003)) {
        wb = new HSSFWorkbook();
      }
      if (type.equals(ITestCC.ImportCC.C_EXCEL_SUFFIX.SUFFIX_2007)) {
        wb = new XSSFWorkbook();
      }
      CreationHelper createHelper = wb.getCreationHelper();
      Sheet sheet = wb.createSheet("sheet1");
      Set<String> sets = maps.keySet();
      Row row = sheet.createRow(0);
      int i = 0;
      // 定义表头
      for (Iterator<String> it = sets.iterator(); it.hasNext();) {
        String key = it.next();
        Cell cell = row.createCell(i++);
        cell.setCellValue(createHelper.createRichTextString(maps.get(key)));
      }
      // 填充表单内容
      // System.out.println("--------------------100%");
      float avg = list.size() / 20f;
      int count = 1;
      for (int j = 0; j < list.size(); j++) {
        T p = list.get(j);
        Class classType = p.getClass();
        int index = 0;
        Row row1 = sheet.createRow(j + 1);
        for (Iterator<String> it = sets.iterator(); it.hasNext();) {
          String key = it.next();
          String firstLetter = key.substring(0, 1).toUpperCase();
          // 获得和属性对应的getXXX()方法的名字
          String getMethodName = "get" + firstLetter + key.substring(1);
          // 获得和属性对应的getXXX()方法
          Method getMethod = classType.getMethod(getMethodName, new Class[] {});
          // 调用原对象的getXXX()方法
          Object value = getMethod.invoke(p, new Object[] {});
          Cell cell = row1.createCell(index++);
          cell.setCellValue(value.toString());
        }
        if (j > avg * count) {
          count++;
          //System.out.print("I");
        }
        if (count == 20) {
          //System.out.print("I100%");
          count++;
        }
      }
      FileOutputStream fileOut = new FileOutputStream(file);
      wb.write(fileOut);
      fileOut.close();

    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (SecurityException e) {
      e.printStackTrace();
      return false;
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      return false;
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return false;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return false;
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
   */
}