package com.app.aihealthapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/18 16:26
 * 修改人：Chen
 * 修改时间：2019/8/18 16:26
 */
public class utils {
    /**
     * @return
     */
    public static String getPresentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
