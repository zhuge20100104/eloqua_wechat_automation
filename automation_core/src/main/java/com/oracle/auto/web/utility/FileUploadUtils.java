package com.oracle.auto.web.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by madirv on 12/16/2014.
 */
public class FileUploadUtils {


    static private final int minWait = PropertyConfiger.instance().getEnvData("min.file.upload.time.out", 60);
    static private final int maxWiat = PropertyConfiger.instance().getEnvData("max.file.upload.time.out", 300);

    // 10MB - 60 s
    static public int estimatedTimeNeededForTimeUploading(String filePath) {
        ArrayList<String> lst = new ArrayList<String>();
        lst.add(filePath);
        return estimatedTimeNeededForTimeUploading(lst);
    }

    static public int estimatedTimeNeededForTimeUploading(List<String> filePaths) {
        int speedBPS = PropertyConfiger.instance().getEnvData("file.upload.speed", 50) * 1024;
        long totalFileSize = 0;
        for (String filePath : filePaths)
            totalFileSize += new File(filePath).length();

        int secondsNeeded = (int) ( totalFileSize / speedBPS ); // MB size
        return filterTimeOut(secondsNeeded);
    }

    static public int filterTimeOut(int secondsNeeded) {
        secondsNeeded = secondsNeeded < minWait ? minWait : secondsNeeded;
        secondsNeeded = secondsNeeded > maxWiat ? maxWiat : secondsNeeded;

        return secondsNeeded;
    }




}
