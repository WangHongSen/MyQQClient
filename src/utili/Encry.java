package utili;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encry {
    //加密密码，使用MD5加密算法，可以替换
    public static String digest(String p){
        String algrithm = "MD5";//修改此处替换摘要算法
        try{
            MessageDigest md = MessageDigest.getInstance(algrithm);
            md.update(p.getBytes());
            byte[] arrays = md.digest();
            String res="";
            for(int i=0;i<arrays.length;i++) {
                int temp = arrays[i] & 0xff;
                if (temp <= 0xf) {
                    res += "0";
                }
                res += Integer.toHexString(temp);
            }
            p=res.substring(0,20);
        }catch( NoSuchAlgorithmException ne){
            ne.printStackTrace();
        }
        return p;
    }

    private static byte[] key;
    public static void setKey(byte[] k){
        key = k;
    }
    public static void jdkDES(byte[] src,int mode){
        try {
            //生成key
//            KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
//            keyGenerator.init(56);      //指定key长度，同时也是密钥长度(56位)
//            SecretKey secretKey = keyGenerator.generateKey(); //生成key的材料
//            byte[] key = secretKey.getEncoded();  //生成key

            //key转换成密钥
            DESKeySpec desKeySpec=new DESKeySpec(key);
            SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
            SecretKey key2 = factory.generateSecret(desKeySpec);      //转换后的密钥

            //加密
            Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式

            cipher.init(Cipher.ENCRYPT_MODE, key2);   //指定为加密模式
            byte[] result=cipher.doFinal(src);

            //解密
            cipher.init(Cipher.DECRYPT_MODE,key2);  //相同密钥，指定为解密模式
            result = cipher.doFinal(result);   //根据加密内容解密
          //  System.out.println("jdkDES解密: "+new String(result));  //转换字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       // System.out.println(Encry.encry("1234"));
    }
}