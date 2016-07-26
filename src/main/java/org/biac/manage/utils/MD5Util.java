package org.biac.manage.utils;

import java.security.MessageDigest;

public class MD5Util {
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //对md5编码后的密码做按规则进一步编码
    public static String encode(String str){
        String key = "0123456789ABCDEF";
        char [] key_swap = {'B', '1', '5', '4', 'E', '7', '6', '2', 'F', '9', 'A', '3', 'C', 'D', '8', '0'};
        String result="";
        for (char c:str.toCharArray()) {
            result+=key_swap[key.indexOf(c+"")];
        }
        return result;
    }

    //对encode方法编码的字符串进行解密
    public static String decode(String str){
        String key = "0123456789ABCDEF";
        char [] key_swap = {'B', '1', '5', '4', 'E', '7', '6', '2', 'F', '9', 'A', '3', 'C', 'D', '8', '0'};
        String index = "B154E762F9A3CD80";
        String result="";
        for (char c:str.toCharArray()) {
            result+=key.charAt(index.indexOf(c+""));
        }
        return result;
    }
}