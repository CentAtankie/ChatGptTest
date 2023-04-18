package com.tencent.wxcloudrun.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Description
 * @Author CentAtankie
 **/
public class ExcelUtil {

    /**
     * 导入excel表格 返回表格数据
     * @param file
     * @return 表格数据
     */
    public static ArrayList<ArrayList<Object>> ImportExcel(MultipartFile file){
        ObjectMapper objectMapper =new ObjectMapper();
        String array = "[\"0\",\"1\"]";

        try {

            InputStream inputStream =file.getInputStream();//获取前端传递过来的文件对象，存储在“inputStream”中
            String fileName = file.getOriginalFilename();//获取文件名

            Workbook workbook =null; //用于存储解析后的Excel文件

            //判断文件扩展名为“.xls还是xlsx的Excel文件”,因为不同扩展名的Excel所用到的解析方法不同
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            if(".xls".equals(fileType)){
                workbook= new HSSFWorkbook(inputStream);//HSSFWorkbook专门解析.xls文件
            }else if(".xlsx".equals(fileType)){
                workbook = new XSSFWorkbook(inputStream);//XSSFWorkbook专门解析.xlsx文件
            }

            ArrayList<ArrayList<Object>> list =new ArrayList<>();

            Sheet sheet ; //工作表
            Row row;      //行
            Cell cell;    //单元格

            //循环遍历，获取数据
            for(int i = 0; i< Objects.requireNonNull(workbook).getNumberOfSheets(); i++){
                sheet=workbook.getSheetAt(i);//获取sheet
                for(int j=sheet.getFirstRowNum();j<=sheet.getLastRowNum();j++){//从有数据的第行开始遍历
                    row=sheet.getRow(j);
                    if(row!=null&&row.getFirstCellNum()!=j){ //row.getFirstCellNum()!=j的作用是去除首行，即标题行，如果无标题行可将该条件去掉
                        ArrayList tempList =new ArrayList();
                        for(int k=row.getFirstCellNum();k<row.getLastCellNum();k++){//这里需要注意的是getLastCellNum()的返回值为“下标+1”
                            cell =row.getCell(k);
                            tempList.add(cell);
                        }
                        list.add(tempList);
                    }
                }
            }

//            System.out.println("我是读取的字符串："+nativeStr);
//            System.out.println("我是读取的数组："+arrayList.toString());
            System.out.println("我是解析的Excel："+list.toString());

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static MultipartFile getMultipartFile(File file) {
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , file.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }


}
