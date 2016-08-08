package richard.ztesoft.com.encrypttest;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import javax.crypto.SecretKey;

import richard.ztesoft.com.encrypttest.utils.SecretUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void test() throws Exception{
        String str = "hello";
        String seed = "1234";
        SecretKey secretKey = SecretUtils.generateSessionKey(seed);
        byte[] encryptBytes = SecretUtils.encrypt(str,secretKey);
        String encryptedString = SecretUtils.parseByte2HexStr(encryptBytes);
        Log.i("xsailor","encryptedString = "+encryptedString);

        //secretKey = SecretUtils.generateSessionKey(seed);
        byte[] decryptedBytes = SecretUtils.decrypt(encryptBytes,secretKey);
        String decryptedString = new String(decryptedBytes,"UTF-8");
        Log.i("xsailor","decryptedString = "+decryptedString);
    }
}