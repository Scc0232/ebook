package com.zhijian.ebook.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel操作帮助类
 * @author conlin
 *
 */
public class ExcelUtils<T> {

    private static final Logger logger = LogManager.getLogger();
    
    /**
     * 读取Excel
     * @param file  文件
     * @param entityClazz  封装到的class
     * @param medthod  忽略的set方法
     * @return
     * @throws Exception
     */
    public List<T> readExcelAll(MultipartFile file,Class<T> entityClazz,List<String> medthods) throws Exception {
        return readExcel(file, entityClazz,medthods);
    }
    
    /**
     * 读取Excel
     * @param file  文件
     * @param entityClazz  封装的bean
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unlikely-arg-type")
	private List<T> readExcel(MultipartFile file,Class<T> entityClazz,List<String> medthodList) throws Exception {
        String _fileName = file.getOriginalFilename();
        String suffer = _fileName.substring(_fileName.lastIndexOf(".") + 1);
        List<T> list = new ArrayList<T>();
        logger.info("读取Excel文件开始，文件格式为：{},",suffer);
        if ("xls".equalsIgnoreCase(suffer) || "xlsx".equalsIgnoreCase(suffer)) {
            InputStream is = file.getInputStream();
            Workbook hssfWorkbook = null;
            if ("xls".equalsIgnoreCase(suffer) ) {
                hssfWorkbook = new HSSFWorkbook(is);
            } else if ("xlsx".equalsIgnoreCase(suffer)) {
                hssfWorkbook = new XSSFWorkbook(is); 
            }
            int sheetCount = hssfWorkbook.getNumberOfSheets();
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < sheetCount; numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                int lastRow = hssfSheet.getLastRowNum();
                boolean isAddValue =  false;
                // 循环行Row
                for (int rowNum = 1; rowNum <= lastRow; rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) {
                        continue;
                    }
                    //通过反射把值set到实体
                    T t = entityClazz.newInstance();
                    Class<? extends Object> clazz = t.getClass();
                    Field[] field = clazz.getDeclaredFields();
                    int index = 0;
                    boolean isHasValue = false;
                    for (int i = 0; i < field.length; i++) {
                        String propertyName = field[i].getName();
                        Class<?> clazzType = field[i].getType();
                        String methodName = "set" + propertyName.substring(0,1).toUpperCase()
                                + propertyName.substring(1, propertyName.length());
                        Method method = null;
                        try {
                            method = clazz.getMethod(methodName, new Class[]{clazzType});
                        } catch (Exception e) {
                            logger.error("方法{}不存在",methodName);
                        }
                        if (method == null) {
                            continue;
                        }
                        if (medthodList.contains(methodName)) {
                        	continue;
						}
                       Cell cell = hssfRow.getCell(index);
                       if (cell == null || cell.equals("")) {
                    	   index ++;
                           continue;
                       }
                       isHasValue = setValue(clazzType, method, t, cell);
                       logger.info("isHashValue = {},index={}",isHasValue,index);
                       index ++;
                       if (isHasValue && !isAddValue) {
                    	   isAddValue = true;
                       }
                    }
                    logger.info("rowNum = {},isAddValue = {}",rowNum,isAddValue);
                    if (isAddValue) {
                    	list.add(t);
                    	isAddValue = false;
					}
                }
            }
        } else if ("csv".equalsIgnoreCase(suffer)) {
            InputStreamReader fr = null;  
            BufferedReader br = null;  
            try {  
                fr = new InputStreamReader(file.getInputStream());  
                br = new BufferedReader(fr);  
                String rec = null;  
                String[] argsArr = null;  
                int index = 0;
                while ((rec = br.readLine()) != null) {
                    index ++;
                    if (index == 1) {
                       continue;
                    }
                    argsArr = rec.split(",");
                    //通过反射把值set到实体
                    T t = entityClazz.newInstance();
                    Class<? extends Object> clazz = t.getClass();
                    Field[] field = clazz.getDeclaredFields();
                    int j = 0;
                    for (int i = 0; i < field.length; i++) {
                        String propertyName = field[i].getName();
                        Class<?> clazzType = field[i].getType();
                        String methodName = "set" + propertyName.substring(0,1).toUpperCase()
                                + propertyName.substring(1, propertyName.length());
                        Method method = null;
                        try {
                            method = clazz.getMethod(methodName, new Class[]{clazzType});
                        } catch (Exception e) {
                            logger.error("方法{}不存在",methodName);
                        }
                        if (method == null) {
                            continue;
                        }
                        if (medthodList.contains(methodName)) {
                        	continue;
						}
                        if (j < argsArr.length) {
                            setValue(clazzType, method, t, argsArr[j]);
                        }
                        j ++;
                    }
                    list.add(t);
                }  
            } catch (IOException e) {  
                logger.error("Excel导入异常！",e);
            } finally {  
                try {  
                    if (fr != null)  
                        fr.close();  
                    if (br != null)  
                        br.close();  
                } catch (IOException ex) {  
                    logger.error("Excel导入-关闭资源异常！",ex);  
                }  
            }  
        
        }
        return list;
    }
    
    /**
     * xls,xlsx setValue
     * @param clazzType
     * @param method
     * @param t
     * @param cell
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unlikely-arg-type")
	private boolean setValue(Class<?> clazzType,Method method,T t,Cell cell) throws Exception {
        if (cell == null || cell.equals("")) {
            return false;
        }
        if (clazzType == String.class) {
        	String value = "";
        	try {
        		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        		Date date = cell.getDateCellValue();
        		value = format.format(date);
			} catch (Exception e) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				value = cell.getStringCellValue();
			}
        	
        	if (StringUtils.isNotBlank(value)) {
        		method.invoke(t, new Object[]{value});
        		return true;
			}
            return false;
        } else if (clazzType == Integer.class) {
        	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        	Double value = cell.getNumericCellValue();
        	if (value != null && value > 0) {
        		method.invoke(t, new Object[]{Integer.valueOf(value.intValue())});
                return true;
			}
            return false;
        } else if (clazzType == Date.class) {
        	cell.setCellType(Cell.CELL_TYPE_FORMULA);
        	Date date = cell.getDateCellValue();
        	if (date != null) {
        		method.invoke(t, new Object[]{date});
                return true;
			}
            return false;
        } else if (clazzType == Double.class) {
        	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        	Double value = cell.getNumericCellValue();
        	if (value != null && value > 0.0) {
        		method.invoke(t, new Object[]{value});
                return true;
			}
            return false;
        } else if (clazzType == Float.class) {
        	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        	Double value = cell.getNumericCellValue();
        	if (value != null && value > 0.0) {
        		 method.invoke(t, new Object[]{value});
                 return true;
			}
           return false;
        }
        return false;
    }
    
    /**'
     * csv setValue
     * @param clazzType
     * @param method
     * @param t
     * @param value
     * @throws Exception
     */
    private void setValue(Class<?> clazzType,Method method,T t,String value) throws Exception {
        if (StringUtils.isBlank(value)) {
            return ;
        }
        if (clazzType == String.class) {
            method.invoke(t, new Object[]{value});
        } else if (clazzType == Integer.class) {
            method.invoke(t, new Object[]{Integer.valueOf(value)});
        } else if (clazzType == Date.class) {
            method.invoke(t, new Object[]{value});
        } else if (clazzType == Double.class) {
            method.invoke(t, new Object[]{Double.valueOf(value)});
        } else if (clazzType == Float.class) {
            method.invoke(t, new Object[]{Double.valueOf(value)});
        }
    }
}
