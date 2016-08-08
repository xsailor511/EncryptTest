package richard.ztesoft.com.encrypttest.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by richard on 16/7/19.
 */

public class DES3Model {

    public static final String KEY_ALGORITHM = "DESede";
    private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
    //    private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/ISO10126Padding";

    /**
     * 初始化密钥
     *
     * @return byte[] 密钥
     * @throws Exception
     */
    public static byte[] initSecretKey() throws Exception{
        //密钥的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //指定密钥大小
        kg.init(168);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     *
     * @param key   二进制密钥
     * @return Key  密钥
     * @throws Exception
     */
    public static Key toKey(byte[] key) throws Exception{
        //实例化DES密钥规则
        DESedeKeySpec dks = new DESedeKeySpec(key);
        //实例化密钥工厂
        SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成密钥
        SecretKey  secretKey = skf.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密
     *
     * @param data  待加密数据
     * @param key   密钥
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,Key key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data  待加密数据
     * @param key   二进制密钥
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }


    /**
     * 加密
     *
     * @param data  待加密数据
     * @param key   二进制密钥
     * @param cipherAlgorithm   加密算法/工作模式/填充方式
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    /**
     * 加密
     *
     * @param data  待加密数据
     * @param key   密钥
     * @param cipherAlgorithm   加密算法/工作模式/填充方式
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data  待解密数据
     * @param key   二进制密钥
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data  待解密数据
     * @param key   密钥
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,Key key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data  待解密数据
     * @param key   二进制密钥
     * @param cipherAlgorithm   加密算法/工作模式/填充方式
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    public static void encryptionFile(Key sessionKey,String srcFile,String destionFile) throws Exception {
        int len = 0;
        byte[] buffer = new byte[5 * 1024];
        byte[] cipherbuffer = null;

        // 使用会话密钥对文件加密。
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM,new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, sessionKey);

        FileInputStream fis = new FileInputStream(new File(srcFile));
        FileOutputStream fos = new FileOutputStream(new File(destionFile));
        // 读取原文，加密并写密文到输出文件。
        while ((len = fis.read(buffer)) != -1) {
            cipherbuffer = cipher.update(buffer, 0, len);
            fos.write(cipherbuffer);
            fos.flush();
        }
        cipherbuffer = cipher.doFinal();
        fos.write(cipherbuffer);
        fos.flush();

        if (fis != null)
            fis.close();
        if (fos != null)
            fos.close();
    }
    //根据密钥解密文件
    public static void descryptionFile(Key sessionKey,String srcFile,String destionFile) throws Exception{
        int len = 0;
        byte[] buffer = new byte[5 * 1024];
        byte[] plainbuffer = null;

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM,new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE,sessionKey);

        FileInputStream fis = new FileInputStream(new File(srcFile));
        FileOutputStream fos = new FileOutputStream(new File(destionFile));

        while ((len = fis.read(buffer)) != -1){
            plainbuffer = cipher.update(buffer,0,len);
            fos.write(plainbuffer);
            fos.flush();
        }

        plainbuffer = cipher.doFinal();
        fos.write(plainbuffer);
        fos.flush();

        if(fis!=null)
            fis.close();
        if(fos!=null)
            fos.close();
    }
    /**
     * 解密
     *
     * @param data  待解密数据
     * @param key   密钥
     * @param cipherAlgorithm   加密算法/工作模式/填充方式
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }



}