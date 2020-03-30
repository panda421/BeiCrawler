package cn.edu.zjut.weining.beicrawler.record;

import cn.edu.zjut.weining.beicrawler.log.BeiCrawlerLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @ClassName: Recorder
 * @Description: TODO
 * @Author: panda421
 * @Date: 2020-03-29 20:51
 */
public class Recorder {

    private static final String TAG = "Recorder";
    private Recorder() {}
    private static Recorder instance = new Recorder();
    public static Recorder getInstance() {
        return instance;
    }
    public void save2Txt(String data, String fileName, String filePath) {
        try {
            File idTxtDir = new File(filePath+"/"+fileName+".txt");
            FileOutputStream outputStream = new FileOutputStream(idTxtDir,true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            streamWriter.write(data+"\n");
            streamWriter.close();
        } catch (Throwable e) {
            BeiCrawlerLogger.log(TAG, e.toString());
        }
    }
}
