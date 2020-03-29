package cn.edu.zjut.weining.beicrawler.log;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: BeiLogger
 * @Description: log file
 * @Author: panda421
 * @Date: 2020-03-29 07:39
 */
public class BeiCrawlerLogger {

    private static SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private static org.apache.log4j.Logger logger;
    private static Queue<String> cache = new LinkedList<>();

    static {
        File dir = new File("BeiLog");
        if (!dir.exists() || dir.isFile()) {
            try {
                Files.createDirectories(dir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 定义日志和相关数据库
        // INFO及以上 文件
        // WARN及以上 控制台
        Properties logProperties = new Properties();
        logProperties.put("log4j.rootLogger", "DEBUG, file, stdout");
        // 文件
        logProperties.put("log4j.appender.file", "org.apache.log4j.RollingFileAppender");
        logProperties.put("log4j.appender.file.Threshold", "INFO");
        logProperties.put("log4j.appender.file.File", "BeiLog/BeiCrawler.log");
        logProperties.put("log4j.appender.file.MaxFileSize", "50MB");
        logProperties.put("log4j.appender.file.MaxBackupIndex", "10");
        logProperties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        logProperties.put("log4j.appender.file.layout.ConversionPattern", "%d{MM-dd HH:mm:ss} | %m%n");
        // 控制台
        logProperties.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        logProperties.put("log4j.appender.stdout.Threshold", "FATAL");
        logProperties.put("log4j.appender.stdout.Target", "System.out");
        logProperties.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        logProperties.put("log4j.appender.stdout.layout.ConversionPattern", "%d{MM-dd HH:mm:ss} | %m%n");
        // 配置日志
        PropertyConfigurator.configure(logProperties);
        // 日志对象
        logger = org.apache.log4j.Logger.getLogger("");
    }

    /**
     * 将日志仅输出到文件。
     * @param tag 输出者的识别号。
     * @param content 日志内容。
     */
    public static void log(String tag, String content) {
        String msg = tag + " | " + content;
        logger.info(msg);
        while (cache.size() >= 1000) {
            cache.poll();
        }
        String wt = format.format(new Date()) + " | " + msg;
        cache.offer(wt);
        System.out.println(wt);
    }

    /**
     * 打印最新的n条日志。
     * @param n 打印的条数，最大不超过1000。
     */
    public static void printCache(int n) {
        for (int i = Math.max(cache.size() - n, 0), j = 0; i < cache.size() && j < n; i++, j++) {
            System.out.println(((List) cache).get(i));
        }
    }
}
