package richard.ztesoft.com.encrypttest.utils;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by richard on 16/7/22.
 */
public class FileUtils {
    /**
     * 读取文本文件
     * @param filePath
     * @return 文件内容字符串
     */
    public static String readTxtFile(String filePath){
        StringBuilder stringBuilder = new StringBuilder();
        if(ExternalStorage.isWritable()){

            File normalFile = new File(filePath);
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            if(normalFile.exists()){
                try {
                     fileReader = new FileReader(normalFile);
                     bufferedReader = new BufferedReader(fileReader);

                    String lineString ;
                    while ((lineString=bufferedReader.readLine())!=null){
                        stringBuilder.append(lineString);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }finally {
                    try {
                        if (bufferedReader!=null){
                            bufferedReader.close();
                        }
                        if (fileReader!=null){
                            fileReader.close();
                        }

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return null;
        }

        String normal = stringBuilder.toString();
        return normal;
    }


    /**
     * 向文件里写内容
     * @param content
     * @param filePath
     * @return true写入成功，false写入失败
     */
    public static boolean writeTxtFile(String content,String filePath){

        if(ExternalStorage.isWritable()){
            File normalFile = new File(filePath);
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileWriter = new FileWriter(normalFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.flush();

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if (fileWriter!=null){
                        fileWriter.close();
                    }
                    if (bufferedWriter!=null){
                        bufferedWriter.close();
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            return true;
        }else{
            return false;
        }
    }

    /**
     * 读取任意文件
     * @param filePath
     * @return 十六进制字符串
     */
    public static String readFile(String filePath){
        StringBuilder stringBuilder = new StringBuilder();
        if(ExternalStorage.isWritable()){
            File normalFile = new File(filePath);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            try {
                FileInputStream inputStream = new FileInputStream(normalFile);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                while ((bytesRead = bufferedInputStream.read(buffer))!=-1){
                    String hexString = toHex(buffer);
                    stringBuilder.append(hexString);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }

            return stringBuilder.toString();
        }else {
            return null;
        }
    }


    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
}
