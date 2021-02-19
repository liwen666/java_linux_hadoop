package com.linux.temp.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class PropertiesThreadLocalHolder {
    private final static ThreadLocal<Map<String, String>> localProperties = new ThreadLocal<>();

    public static void addProperties(String key, String value) {
        if (null == localProperties.get()) {
            localProperties.set(new HashMap<>());
        }
        localProperties.get().put(key, value);
    }

    public static Map<String, String> get() {
        return localProperties.get();
    }

    public static String getProperties(String key) {
        try {
            return localProperties.get().get(key);
        } catch (Exception e) {
            return null;
        }

    }

    public static void remove() {
        localProperties.remove();
    }
}


class TestLocal {
    public static void main(String[] args) {
        //这里0和0L效果一样
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            es.submit(new Runnable() {
                @Override
                public void run() {
                    PropertiesThreadLocalHolder.addProperties("parentId", finalI + "");
                    System.out.println(PropertiesThreadLocalHolder.getProperties("parentId"));
                    PropertiesThreadLocalHolder.remove();
                    Map<String, String> stringStringMap = PropertiesThreadLocalHolder.get();
                    System.out.println(stringStringMap);

                }
            });
        }


    }
}


