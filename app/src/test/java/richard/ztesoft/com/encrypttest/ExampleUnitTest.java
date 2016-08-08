package richard.ztesoft.com.encrypttest;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.SecretKey;

import richard.ztesoft.com.encrypttest.utils.AES256Encryption;
import richard.ztesoft.com.encrypttest.utils.DesUtil;
import richard.ztesoft.com.encrypttest.utils.MyAES;
import richard.ztesoft.com.encrypttest.utils.MyMessageDigest;
import richard.ztesoft.com.encrypttest.utils.RC4;
import richard.ztesoft.com.encrypttest.utils.SecretUtils;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        String str = "hello";
//        String seed = "1234";
//        SecretKey secretKey = SecretUtils.generateSessionKey(seed);
//        byte[] encryptBytes = SecretUtils.encrypt(str,secretKey);
//        String encryptedString = SecretUtils.parseByte2HexStr(encryptBytes);
//        System.out.println("encryptedString = "+encryptedString);
//
//        byte[] decryptedBytes = SecretUtils.decrypt(encryptBytes,secretKey);
//        String decryptedString = new String(decryptedBytes,"UTF-8");
//        System.out.println("decryptedString = "+decryptedString);



        byte[] secretBytes = MyAES.generateAESSecretKey();
        SecretKey key = MyAES.restoreSecretKey(secretBytes);
        byte[] encodedText = MyAES.AesEcbEncode(MyAES.PLAIN_TEXT.getBytes(), key);

        System.out.println("AES ECB encoded with Base64: " + Base64.encodeBase64String(encodedText));
        System.out.println("AES ECB decoded: "
                + MyAES.AesEcbDecode(encodedText, key));



        encodedText = MyAES.AesCbcEncode(MyAES.PLAIN_TEXT.getBytes(), key, MyAES.IVPARAMETERS);


        System.out.println("AES CBC encoded with Base64: " + Base64.encodeBase64String(encodedText));
        System.out.println("AES CBC decoded: "
                + MyAES.AesCbcDecode(encodedText, key,
                MyAES.IVPARAMETERS));


        System.out.println("MD5: " + MyMessageDigest.MD5(MyMessageDigest.PLAIN_TEXT.getBytes()));
        System.out.println("SHA-512: " + MyMessageDigest.SHA(MyMessageDigest.PLAIN_TEXT.getBytes()));
        System.out.println("HmacSHA512：" + MyMessageDigest.MAC(MyMessageDigest.PLAIN_TEXT.getBytes()));


        String url = "http://baidu.com";
        url = DesUtil.encrypt(url, "URIW853FKDJAF9363KDJKF7MFS3FKD");
        System.err.println(url);
        url = DesUtil.decrypt("3AD66C12BFC094DD65566158F09F5C72AD4021DB9ABCD50D", "URIW853FKDJAF9363KDJKF7MFS3FKD");
        System.out.println(url);

        String inputStr = "做个好男人";
        String str = RC4.encry_RC4_string(inputStr, "123456");
        System.out.println(str);
        System.out.println(RC4.decry_RC4(str, "123456"));


    }

    @Test
    public void Test2() throws Exception {
        String str="芸sweet";

        //打印原文
        System.out.println("原文："+str);

        //密钥
        byte[] key=null;

        try {

            //生成随机密钥
            key = AES256Encryption.initkey();

            //打印密钥
            System.out.print("密钥：");
            for(int i = 0;i<key.length;i++)
                System.out.printf("%x", key[i]);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("\n");

        //加密
        byte[] data= new byte[0];
        try {
            data = AES256Encryption.encrypt(str.getBytes(), key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //打印密文
        System.out.print("加密后：");

        for(int i = 0;i<data.length;i++)
            System.out.printf("%x", data[i]);

        System.out.print("\n");

        //解密密文
        try {
            data= AES256Encryption.decrypt(data, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //打印原文
        System.out.println("解密后："+new String(data));
    }
}