package richard.ztesoft.com.encrypttest.utils;

import android.os.Environment;

import java.io.File;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by richard on 16/7/19.
 */
public class FileSecret {

    /**
     * 使用AES算法加密文件
     * @param key_AES
     * @param srcPath
     * @param srcjiamiFile
     */
   public static void testAESJia(String key_AES,String srcPath,String srcjiamiFile){
        File f=new File(srcPath);
        if(!f.exists()||f.isDirectory()){

        }
//            Toast.makeText(getApplicationContext(), "该文件不合法!", Toast.LENGTH_SHORT).show();
        else{
            //String prefix=f.getName().substring(0, f.getName().indexOf('.'));
            //String suffix=f.getName().substring(f.getName().indexOf('.'));
            //srcjiamiFile= Environment.getExternalStorageDirectory()+File.separator+prefix+"AES_jiAMi"+suffix;

//            AESKeyModel model_aes=new AESKeyModel();
//            model_aes.setSrcFile(srcPath);
//            model_aes.setDestionFile(srcjiamiFile);

            try {
                Key key_AES2=new SecretKeySpec(AESKeyModel.initSecretKey(),AESKeyModel.KEY_ALGORITHM);
                AESKeyModel.encryptionFile(key_AES2,srcPath,srcjiamiFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用AES算法解密文件
     * @param JiamiFilePath
     * @param outjiemiFile
     */
    public static void testAESJieMi(String JiamiFilePath,String outjiemiFile){
        File f=new File(JiamiFilePath);
        if(!f.exists()||f.isDirectory()){

        }
        else{
//            String prefix=f.getName().substring(0, f.getName().indexOf('.'));
//            String suffix=f.getName().substring(f.getName().indexOf('.'));
            //outjiemiFile=Environment.getExternalStorageDirectory()+File.separator+prefix+"AES_jieMi"+suffix;

            Key key_AES2=new SecretKeySpec(AESKeyModel.initSecretKey(),AESKeyModel.KEY_ALGORITHM);
            try {
                AESKeyModel.descryptionFile(key_AES2,JiamiFilePath,outjiemiFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 使用DES3 加密文件
     * @param sourceNormalFilePath
     * @param outSecretFilePath
     */
    public static void test3desJiaMi(String password,String sourceNormalFilePath,String outSecretFilePath){
        File f=new File(sourceNormalFilePath);
        if(!f.exists()||f.isDirectory()){
            return;
        }
        else{
//            String prefix=f.getName().substring(0, f.getName().indexOf('.'));
//            String suffix=f.getName().substring(f.getName().indexOf('.'));
//            srcjiamiFile=Environment.getExternalStorageDirectory()+File.separator+prefix+"des3_jiAMi"+suffix;

//            DES3Model model_des3=new DES3Model();
//            model_des3.setSrcFile(srcPath);
//            model_des3.setDestionFile(srcjiamiFile);
            try {
                Key key_des3= DES3Model.toKey(password.getBytes("UTF8")); //new SecretKeySpec(DES3Model.initSecretKey(), DES3Model.KEY_ALGORITHM);
                DES3Model.encryptionFile(key_des3,sourceNormalFilePath,outSecretFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void test3desJieMi(String password,String sourceSecretFilePath,String outNormalFilePath){
        File f=new File(sourceSecretFilePath);
        if(!f.exists()||f.isDirectory()){
            return;
        }

        else{
//            String prefix=f.getName().substring(0, f.getName().indexOf('.'));
//            String suffix=f.getName().substring(f.getName().indexOf('.'));
//            outjiemiFile=Environment.getExternalStorageDirectory()+File.separator+prefix+"des3_jieMi"+suffix;
//
//            DES3Model model_des3=new DES3Model();
//            model_des3.setSrcFile(srcjiamiFile);
//            model_des3.setDestionFile(outjiemiFile);

            try {
                Key key_des3= DES3Model.toKey(password.getBytes("UTF8"));   //new SecretKeySpec(DES3Model.initSecretKey(), DES3Model.KEY_ALGORITHM);
                DES3Model.descryptionFile(key_des3,sourceSecretFilePath,outNormalFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
