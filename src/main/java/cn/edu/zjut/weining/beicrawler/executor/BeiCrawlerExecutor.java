package cn.edu.zjut.weining.beicrawler.executor;

import cn.edu.zjut.weining.beicrawler.worker.BeiCrawlerWorker;
import cn.edu.zjut.weining.beicrawler.log.BeiCrawlerLogger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: BeiCrawlerExecutor
 * @Description: executor for crawler
 * @Author: panda421
 * @Date: 2020-03-28 10:11
 */
public class BeiCrawlerExecutor {
    private String TAG = "BeiCrawlerExecutor";
    private BeiCrawlerWorker beiCrawlerWorker;
    private int maxFailCount = 5;
    private Map<String, String> header = null;
    private Map<String, String> cookies = null;
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:72.0) Gecko/20100101 Firefox/72.0";
    private int timeout = 1;
    private int interval = 1;

    public BeiCrawlerExecutor(BeiCrawlerWorker beiCrawlerWorker) {
        this.beiCrawlerWorker = beiCrawlerWorker;
    }

    public void submit(String link) {
        try {
            String html = getHtml(link);
            beiCrawlerWorker.run(html);
        } catch (Throwable e) {
            beiCrawlerWorker.fail(link);
        }
    }

    public String getHtml(String link) {
        int c = 0;
        while (c < maxFailCount) {
            try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(timeout, TimeUnit.MINUTES);
                OkHttpClient httpClient = builder.build();
                Request request = new Request.Builder().url(link).get()
                        .addHeader("User-Agent", userAgent)
                        .build();
                Thread.sleep(635 * (new Random().nextInt(2)) + interval*1000);
                Response response = httpClient.newCall(request).execute();
                return response.body().string();
            } catch (Throwable e) {
                c++;
                if (c == maxFailCount) {
                    BeiCrawlerLogger.log(TAG, e.toString());
                }
            }
        }
        return null;
    }

    public BeiCrawlerExecutor setMaxFailCount(int maxFailCount) {
        this.maxFailCount = maxFailCount;
        return this;
    }

    public BeiCrawlerExecutor setInterval(int interval) {
        this.interval = interval;
        return this;
    }


    public BeiCrawlerExecutor setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }


    public BeiCrawlerExecutor setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }


    public BeiCrawlerExecutor setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }


    public BeiCrawlerExecutor setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }
}
