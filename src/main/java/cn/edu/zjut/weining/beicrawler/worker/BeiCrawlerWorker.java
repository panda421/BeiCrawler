package cn.edu.zjut.weining.beicrawler.worker;


/**
 * @ClassName: BeiCrawlerWorker
 * @Description: interface of crawler
 * @Author: panda421
 * @Date: 2020-03-28 10:13
 */
public interface BeiCrawlerWorker<T> {
    /**
     * 如何解析爬下来的HTML文档？
     * @param html OKhttp提供的文档
     * @return
     */
    T run(String html);

    /**
     * run方法异常则执行fail方法
     * @param link 错误的网址
     * @return
     */
    T fail(String link);
}
