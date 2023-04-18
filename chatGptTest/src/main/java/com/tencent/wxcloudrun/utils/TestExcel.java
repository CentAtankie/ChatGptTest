package com.tencent.wxcloudrun.utils;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @Description
 * @Author CentAtankie
 **/
public class TestExcel {
    public static void main(String[] args) throws Exception {

        String fileName = "/Users/centatankie/Desktop/副本3天效果数据0418.xlsx";
        List<String> details = new ArrayList<>();
//        details.add("/Users/centatankie/Desktop/3.22/导出数据2023-03-22.xlsx");
//        details.add("/Users/centatankie/Desktop/导出数据2023-04-14.xlsx");
        details.add("/Users/centatankie/Desktop/0418/导出数据2023-04-18.xlsx");
        details.add("/Users/centatankie/Desktop/0418/导出数据2023-04-18 (1).xlsx");
        details.add("/Users/centatankie/Desktop/0418/导出数据2023-04-18 (2).xlsx");
        details.add("/Users/centatankie/Desktop/0418/导出数据2023-04-18 (3).xlsx");
        details.add("/Users/centatankie/Desktop/0418/导出数据2023-04-18 (4).xlsx");

        File file = new File(fileName);
        MultipartFile multipartFile = ExcelUtil.getMultipartFile(file);
        InputStream inputStream =multipartFile.getInputStream();//获取前端传递过来的文件对象，存储在“inputStream”中
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        Workbook workbook =null; //用于存储解析后的Excel文件
        if(".xls".equals(fileType)){
            workbook= new HSSFWorkbook(inputStream);//HSSFWorkbook专门解析.xls文件
        }else if(".xlsx".equals(fileType)){
            workbook = new XSSFWorkbook(inputStream);//XSSFWorkbook专门解析.xlsx文件
        }

        Sheet sheet ; //工作表
        Row row;      //行
        Cell cell;    //单元格

        for (String s:details){
            //循环遍历，获取数据
            for(int i = 0; i< Objects.requireNonNull(workbook).getNumberOfSheets(); i++){
                sheet=workbook.getSheetAt(i);//获取sheet
                for(int j=sheet.getFirstRowNum();j<=sheet.getLastRowNum();j++){//从有数据的第行开始遍历
                    row=sheet.getRow(j);
                    if(row!=null&&row.getFirstCellNum()!=j&&row.getFirstCellNum()!=j){ //row.getFirstCellNum()!=j的作用是去除首行，即标题行，如果无标题行可将该条件去掉
                        for(int k=row.getFirstCellNum();k<1;k++){//这里需要注意的是getLastCellNum()的返回值为“下标+1”
                            cell =row.getCell(k);
                            //查询表格内容
                            Map<String,Object> obj  = getDetailObj(cell.toString(),s);
                            if(obj.containsKey("estimate_touchNum")){
                                Cell cellnew = row.createCell(cell.getColumnIndex()+1);
                                cellnew.setCellValue(obj.get("estimate_touchNum").toString());
                            }
                            if(obj.containsKey("touchNum")){
                                Cell cellnew = row.createCell(cell.getColumnIndex()+2);
                                cellnew.setCellValue(obj.get("touchNum").toString());
                            }
                            if(obj.containsKey("clickNum")){
                                Cell cellnew = row.createCell(cell.getColumnIndex()+3);
                                cellnew.setCellValue(obj.get("clickNum").toString());
                            }
                            if(obj.containsKey("uv")){
                                Cell cellnew = row.createCell(cell.getColumnIndex()+4);
                                cellnew.setCellValue(obj.get("uv").toString());
                            }
                            if(obj.containsKey("payNum")){
                                Cell cellnew = row.createCell(cell.getColumnIndex()+5);
                                cellnew.setCellValue(obj.get("payNum").toString());
                            }
                            if(obj.containsKey("payment")){
                                Cell cellnew = row.createCell(cell.getColumnIndex()+6);
                                cellnew.setCellValue(obj.get("payment").toString());
                            }
                        }
                    }
                }
            }
        }

        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();

}

    private static Map<String,Object> getDetailObj(String taskName,String fileName) throws Exception{
        File file = new File(fileName);
        MultipartFile multipartFile = ExcelUtil.getMultipartFile(file);
        InputStream inputStream = multipartFile.getInputStream();//获取前端传递过来的文件对象，存储在“inputStream”中
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        Workbook workbook = null; //用于存储解析后的Excel文件
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inputStream);//HSSFWorkbook专门解析.xls文件
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inputStream);//XSSFWorkbook专门解析.xlsx文件
        }
        Map<String,Object> obj = new HashMap<>();

        if(workbook!=null){
            Sheet sheet = workbook.getSheetAt(0);
            Row row;
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {//从有数据的第行开始遍历
                row = sheet.getRow(j);
                if (row != null && row.getFirstCellNum() != j && row.getFirstCellNum() != j - 1) { //row.getFirstCellNum()!=j的作用是去除首行，即标题行，如果无标题行可将该条件去掉
                    String name = row.getCell(2).toString();
                    if(name.equals(taskName)){
                        String estimate_touchNum = row.getCell(5).toString();
                        String touchNum = row.getCell(6).toString();
                        String clickNum = row.getCell(8).toString();
                        String uv = row.getCell(9).toString();
                        String payNum = row.getCell(10).toString();
                        String payment = "0";
                        try {
                            payment = row.getCell(111).toString();
                        }catch (Exception e){
                        }
                        obj.put("estimate_touchNum",estimate_touchNum);
                        obj.put("touchNum",touchNum);
                        obj.put("clickNum",clickNum);
                        obj.put("uv",uv);
                        obj.put("payNum",payNum);
                        obj.put("payment",payment);
                    }
                }
            }
        }

        return obj;
    }


    /**
     * 上传图片
     *
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     *                    contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public static String formUpload(String urlStr, Map<String, String> textMap,
                                    Map<String, String> fileMap, String contentType) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            // conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if (!"".equals(contentType)) {
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        } else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        } else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }



}
