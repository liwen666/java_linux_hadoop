package com.linux.temp.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  描述
 * </p>
 *
 *
 * @author lw
 * @since  2019/12/5 14:55
 */
public class YamlUtil {
    public static Logger log = LoggerFactory.getLogger(YamlUtil.class);
    public static Map<?, ?> loadYaml(String fileName) throws FileNotFoundException {
        InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
        return StringUtils.isNotEmpty(fileName) ? (LinkedHashMap<?, ?>) new Yaml().load(in) : null;
    }

    public static void dumpYaml(String fileName, Map<?, ?> map) throws IOException {
        if (StringUtils.isNotEmpty(fileName)) {
            URL resource = YamlUtil.class.getClassLoader().getResource("");
            File dump = new File(resource.getPath() + fileName);
            if (!dump.exists()) {
                dump.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(YamlUtil.class.getClassLoader().getResource(fileName).getFile());
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(map, fileWriter);
        }
    }

    public static Object getProperty(Map<?, ?> map, Object qualifiedKey) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!"".equals(input)) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    return getProperty((Map<?, ?>) map.get(left), right);
                } else if (map.containsKey(input)) {
                    return map.get(input);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void setProperty(Map map, Object qualifiedKey, Object value) {
        if (map != null && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (!input.equals("")) {
                if (input.contains(".")) {
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    if (map.get(left) == null) map.put(left, new HashMap<>());
                    setProperty((Map<?, ?>) map.get(left), right, value);
                } else {
                    ((Map<Object, Object>) map).put(qualifiedKey, value);
                }
            }
        }
    }

    public static Map<String, Object> converMapToProperties(Map<?, ?> map, Map<String, Object> resPor, String prefixKey) {
        Set<?> objects = map.keySet();
        for (Object key : objects) {
            if (map.get(key) instanceof Map) {
                if (StringUtils.isEmpty(prefixKey)) {
                    converMapToProperties((Map<?, ?>) map.get(key), resPor, (String) key);
                } else {
                    converMapToProperties((Map<?, ?>) map.get(key), resPor, prefixKey + "." + (String) key);
                }
            } else {
                if (null == map.get(key)) {
                    log.warn("===配置文件值为空，配置项key:{} -> value:{}", prefixKey + "." + key, map.get(key));
                } else {
                    resPor.put(prefixKey + "." + (String) key, map.get(key));
                }
            }
        }
        return resPor;
    }


    public static Map converPropertiesToMap(Map<String, Object> resPor) {
        Map m = new HashMap();
        Set<String> strings = resPor.keySet();
        for (String key : strings) {
            YamlUtil.setProperty(m, key, resPor.get(key));
        }
        return m;
    }

    public static Map<?, ?> loadYamlByContext(String context) {
        return new Yaml().load(context);
    }

    public static Map<String, Object> converMapToProperties(Map<?, ?> map) {
        Map<String, Object> resPor = new HashMap<>();
        return converMapToProperties(map, resPor, "");
    }

    public static Map collatingCfg(String context) {
        String[] split = context.split("---");
        Map result = new HashMap();
        for (String str : split) {
            Map<?, ?> map = YamlUtil.loadYamlByContext(str);
            Map<String, Object> stringObjectMap = YamlUtil.converMapToProperties(map);
            result.putAll(stringObjectMap);
        }
        return YamlUtil.converPropertiesToMap(result);
    }
}