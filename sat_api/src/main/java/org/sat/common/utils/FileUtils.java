package org.sat.common.utils;

import java.io.File;
import java.util.Calendar;

public class FileUtils {


    /**
     *
     * @param basePath
     * @return
     */
    public static String makeDateDir(String basePath) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        Integer year = calendar.get(Calendar.YEAR);
        String yearDir = basePath + File.separator + year;
        File yearFile = new File(yearDir);
        if (!yearFile.exists()) {
            yearFile.mkdirs();
        }

        Integer month = calendar.get(Calendar.MONTH + 1);
        String monthDir = yearDir + File.separator + month;
        File monthFile = new File(monthDir);
        if (!monthFile.exists()) {
            monthFile.mkdirs();
        }

        Integer day = calendar.get(Calendar.DATE);
        String dayDir = monthDir + File.separator + calendar.get(Calendar.DATE);
        File dayFile = new File(dayDir);
        if (!dayFile.exists()) {
            dayFile.mkdirs();
        }
        return File.separator + year + File.separator + month + File.separator + day+ File.separator ;
    }
}
