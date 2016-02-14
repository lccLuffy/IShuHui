package lcc.ishuhui.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lcc_luffy on 2016/1/27.
 */
public class SecurityUtil {
    public static String createMd5(String... strParams)
    {
        StringBuffer strBuffer = new StringBuffer();

        for(int i = 0 ;i<strParams.length;i++)
        {
            strBuffer.append(strParams[i]);
        }

        return encryptionFor32(strBuffer.toString());
    }
    public static String encryptionFor32(String plainText)
    {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}
