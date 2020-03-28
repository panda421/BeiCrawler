package cn.edu.zjut.weining.beicrawler;

import cn.edu.zjut.weining.beicrawler.executor.BeiCrawlerExecutor;
import cn.edu.zjut.weining.beicrawler.crawler.BeiCrawlerWorker;

/**
 * @ClassName: BeiCrawler
 * @Description: instance
 * @Author: panda421
 * @Date: 2020-03-28 10:09
 */
public class BeiCrawler {
    public static BeiCrawlerExecutor newInstance(BeiCrawlerWorker worker){
        return new BeiCrawlerExecutor(worker);
    }

}
