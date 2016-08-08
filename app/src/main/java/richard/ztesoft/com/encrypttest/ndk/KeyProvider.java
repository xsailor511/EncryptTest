package richard.ztesoft.com.encrypttest.ndk;

/**
 * Created by richard on 16/7/21.
 */
public class KeyProvider {
    static {
        System.loadLibrary("key_provider");
    }

    public static native byte[] getKeyValue();
    public static native byte[] getIV();
}
