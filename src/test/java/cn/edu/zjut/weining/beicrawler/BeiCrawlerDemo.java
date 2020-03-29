package cn.edu.zjut.weining.beicrawler;

import cn.edu.zjut.weining.beicrawler.executor.BeiCrawlerExecutor;
import cn.edu.zjut.weining.beicrawler.log.BeiCrawlerLogger;
import cn.edu.zjut.weining.beicrawler.worker.BeiCrawlerWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: BeiCrawlerDemo
 * @Description: a simple crawler demo
 * @Author: panda421
 * @Date: 2020-03-28 13:21
 */
public class BeiCrawlerDemo implements BeiCrawlerWorker {
    @Override
    public Object run(String html) {
        Document doc = Jsoup.parse(html);
        BeiCrawlerLogger.log(this.getClass().getName(),doc.title());
        return html;
    }

    @Override
    public Object fail(String link) {
        return false;
    }

    public static void main(String[] args) {
        List<String> linkList = new LinkedList<String>();
        linkList.add("https://www.qq.com");
        linkList.add("https://www.baidu.com");
        linkList.add("https://www.sina.com");
        //第一步：新建BeiCrawler实例
        BeiCrawlerExecutor executor = BeiCrawler.newInstance(new BeiCrawlerDemo());
        //第二步：提交任务
        for (String link : linkList) {
            executor.submit(link);
        }
    }
}
