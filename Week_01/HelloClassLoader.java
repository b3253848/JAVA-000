package common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 *
 * @author zhaohl
 * @date 2020/10/21 16:47
 * @version 1.0
 * @since JDK1.8
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> cls = new HelloClassLoader().findClass("Hello");// 加载Hello类
            Object obj = cls.newInstance();// 初始化Hello类
            Method method = cls.getMethod("hello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写findClass方法
     *
     * @param name className类名
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream in = null;
        byte[] bytes = null;
        String filePath = "D:\\geektime\\Hello.xlass";
        try {
            in = new FileInputStream(filePath);
            bytes = toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 转换为byte数组
     * @param in InputStream
     * @return
     * @throws IOException
     */
    private byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1];
        int i = 255;
        while (in.read(buffer) != -1) {
            buffer[0] = (byte) (i - buffer[0]);
            out.write(buffer, 0, 1);
        }
        return out.toByteArray();
    }
}
