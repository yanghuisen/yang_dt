/**
 * 
 */
package com.hzmd.iwrite.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Yangming
 *
 */
public class ScheduleUtil {

  /**
   * 
   */
  public ScheduleUtil() {
  }
  
  //时间间隔(一天)  
  private static final long PERIOD_DAY = 24L * 60 * 60 * 1000;  
  
  public static Timer scheduleDailyTask(int hour, int minut, int second, TimerTask task) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, hour); //凌晨1点  
    calendar.set(Calendar.MINUTE, minut);
    calendar.set(Calendar.SECOND, second);
    Date date = calendar.getTime(); //第一次执行定时任务的时间  
    //如果第一次执行定时任务的时间 小于当前的时间  
    //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。  
    if (date.before(new Date())) {
      date = _addDay(date, 1);
    }
    Timer timer = new Timer();
    //安排指定的任务在指定的时间开始进行重复的固定延迟执行。  
    timer.schedule(task, date, PERIOD_DAY);
    return timer;
  }
  
  private static Date _addDay(Date date, int num) {    // 增加或减少天数
    Calendar startDT = Calendar.getInstance();
    startDT.setTime(date);
    startDT.add(Calendar.DAY_OF_MONTH, num);
    return startDT.getTime();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    
  }

}
