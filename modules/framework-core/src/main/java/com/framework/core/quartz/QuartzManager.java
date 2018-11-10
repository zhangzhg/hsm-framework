package com.framework.core.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;

import com.framework.common.util.ParamUtils;
import com.framework.common.util.SpringUtils;
import com.framework.common.util.UUIDUtils;

/**
 * 定时任务管理类
 *
 */
public class QuartzManager {

    private static Scheduler scheduler = (Scheduler) SpringUtils.getBean("schedulerFactory");

    /**
     * 添加任务
     *
     * @param name
     * @param clz
     * @param cronException
     * @param parameter
     * @throws SchedulerException
     */
    public static void createJob(String name, Class<? extends Job> clz, String cronException, Map<String, Object> parameter) throws SchedulerException {
    	JobDetail jobDetail = null;
    	JobKey jobKey = new JobKey(name);
    	if (ParamUtils.isNull(scheduler.getJobDetail(jobKey))) {
    		jobDetail = JobBuilder.newJob(clz).withIdentity(name).storeDurably().build(); 
    		scheduler.addJob(jobDetail, false);
    	} else {
        	jobDetail = scheduler.getJobDetail(jobKey);
    	}
    	JobDataMap jobDataMap = new JobDataMap();
    	if (!ParamUtils.isEmpty(parameter)) {
			for (Entry<String, Object> entry : parameter.entrySet()) {
				jobDataMap.put(entry.getKey(), entry.getValue());
	        }
        }
        CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronException);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + name + UUIDUtils.generate()).forJob(jobDetail).usingJobData(jobDataMap).withSchedule(schedBuilder).build();
        scheduler.scheduleJob(trigger);
    }

    /**
     * 将java.util.Date转换成cronException
     *
     * @param date
     * @return
     */
    public static String dateToCronException(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        if (date == null) {
            return null;
        }
        return sdf.format(date);
    }

}
