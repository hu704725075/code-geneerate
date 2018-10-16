package com.royyun.tool.codeware.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * IO流的工具类
 *
 * @author liuzb
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    public static void update(String filePath, String str) {
        update(new File(filePath), str);
    }

    public static void update(File file, String str) {
        boolean append = false;
        String encoding = "UTF-8";

        FileOutputStream out = null;
        try {
            createFile(file, 0);
            out = new FileOutputStream(file, append);
            out.write(str.getBytes(encoding));
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    private static void createFile(File file, int index) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile.isDirectory() && parentFile.exists()) {
                if (index > 0) {
                    file.mkdir();
                } else {
                    file.createNewFile();
                }
            } else {
                createFile(parentFile, index + 1);
                if (index > 0) {
                    file.mkdir();
                } else {
                    file.createNewFile();
                }

            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> loadProperties(String filePath) {
        InputStream file = getFile(filePath);
        Properties pro = new Properties();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pro.load(file);
            map = new HashMap<String, Object>((Map) pro);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return map;
    }

    public static InputStream getFile(String fileName) {
        try {
            ClassLoader classLoader = FileUtil.class.getClassLoader();
            /**
             * getResource()方法会去classpath下找这个文件，获取到url resource,
             * 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
             */
            URL url = classLoader.getResource(fileName);
            /**
             * url.getFile() 得到这个文件的绝对路径
             */
            System.out.println(url.getFile());
            File file = new File(url.getFile());
            logger.info(file.getAbsolutePath());
            if (file.exists()) {
                logger.info(fileName + ":classpath加载成功");
                return new FileInputStream(file);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            //logger.error(e.getMessage(), e);
            //logger.info(fileName + ":classpath加载失败");
        }
        try {
            InputStream in = FileUtil.class.getResourceAsStream(fileName);
            if (in == null) {
                throw new FileNotFoundException();
            }
            logger.info(fileName + ":resource加载成功");
            return in;

        } catch (Exception e) {
            //e.printStackTrace();
            //logger.error(e.getMessage(), e);
            //logger.info(fileName + ":resource加载失败");
        }
        try {
            InputStream in = new FileInputStream(new File(fileName));
            if (in == null) {
                throw new FileNotFoundException();
            }
            logger.info(fileName + ":file加载成功");
            return in;

        } catch (Exception e) {
            //e.printStackTrace();
            //logger.error(e.getMessage(), e);
            //logger.info(fileName + ":file加载失败");
        }
        logger.info(fileName + ":加载失败");
        return null;
    }
}
