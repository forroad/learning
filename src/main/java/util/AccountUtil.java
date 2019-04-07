package util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
public class AccountUtil {

    public static String EncodePassword(String password,String salt){
        try{
            MessageDigest md5=MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            return base64en.encode(md5.digest((password + salt).getBytes("utf-8")));
        }catch (Exception e){
            System.out.println("加密密码出错");
            e.printStackTrace();
            return password + salt;
        }
    }
}
