package com.st.dream.utils;

import java.io.*;

public class SerializableUtil {

    private SerializableUtil() {

    }

    public static void writeObject(Object o, String path)  {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
            oos.close();
        } catch (IOException e){
            e.printStackTrace();;
        }
    }

    public static <T>T readObject(String path) {
        ObjectInputStream inStream = null;
        try {
            inStream = new ObjectInputStream(new FileInputStream(path));
            Object o = inStream.readObject();
            System.out.println("序列化后："+o.toString());
            return (T)o;
//            return (T)o.getClass().cast(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
