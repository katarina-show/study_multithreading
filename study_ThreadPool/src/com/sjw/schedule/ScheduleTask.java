package com.sjw.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调度任务的线程
 */
public class ScheduleTask implements Runnable {

    //操作类型
    public enum OperationType{
        None,OnlyThrowException,CatheException
    }

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private OperationType operationType;

    public ScheduleTask(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public void run() {

        switch (operationType){
            case OnlyThrowException:
                System.out.println("Exception will throw:" + formatter.format(new Date()));
                throw new RuntimeException("OnlyThrowException");
            case CatheException:
                try {
                    throw new RuntimeException("CatheException");
                } catch (RuntimeException e) {
                    System.out.println("Exception has been caught:" + formatter.format(new Date()));
                }
                break;
            case None:
                System.out.println("None :" + formatter.format(new Date()));
        }
    }
}
