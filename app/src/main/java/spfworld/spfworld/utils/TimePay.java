package spfworld.spfworld.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guozhengke on 2016/10/12.
 * 倒计时判断
 */
public class TimePay {

    public void Time(String currentTime,String submitTime) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = dfs.format(curDate);
        Date begin=dfs.parse(str);
        Date end = dfs.parse(submitTime);
        long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒

        long day=between/(24*3600);//天
        long hour=between%(24*3600)/3600;//小时
        long minute=between%3600/60;//分
        long second=between%60/60;//秒
    }
}
