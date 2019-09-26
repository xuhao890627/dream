package com.st.dream.classLoader;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {
        String dir = "file:///Users/jxu/workspace/dream/src/main/java/com/st/dream/collection";
        URLClassLoader v1 = new URLClassLoader(new URL[]{new URL(dir)});
        URLClassLoader v2 = new URLClassLoader(new URL[]{new URL(dir)});

        Class<?> depv1Class = v1.loadClass("HashMapTest");
        Object depv1 = depv1Class.getConstructor().newInstance();
        depv1Class.getMethod("print").invoke(depv1);

        Class<?> depv2Class = v2.loadClass("HashMapTest");
        Object depv2 = depv2Class.getConstructor().newInstance();
        depv2Class.getMethod("print").invoke(depv2);

        System.out.println(depv1Class.equals(depv2Class));

    }
}
