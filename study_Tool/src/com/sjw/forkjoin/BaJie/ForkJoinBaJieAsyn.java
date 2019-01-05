package com.sjw.forkjoin.BaJie;


import com.sjw.forkjoin.MakePanTaoArray;
import com.sjw.forkjoin.SunWuKong.Impl.WuKongPickImpl;
import com.sjw.forkjoin.SunWuKong.Impl.WuKongProcessImpl;
import com.sjw.forkjoin.service.IPickTaoZi;
import com.sjw.forkjoin.service.IProcessTaoZi;
import com.sjw.forkjoin.vo.PanTao;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoin的使用--猪8戒
 * RecursiveAction：用于没有返回结果的任务。
 * RecursiveTask：用于有返回结果的任务。
 */
public class ForkJoinBaJieAsyn {

    //八戒只吃桃，不用和悟空一样统计数量，所以继承RecursiveAction
    private static class XiaoBaJie extends RecursiveAction {

        //大部分代码和孙悟空相同
        private final static int THRESHOLD = 100;
        private PanTao[] src;
        private int fromIndex;
        private int toIndex;
        private IPickTaoZi pickTaoZi;

        public XiaoBaJie(PanTao[] src, int fromIndex, int toIndex,
                         IPickTaoZi pickTaoZi) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.pickTaoZi = pickTaoZi;
        }

        @Override
        protected void compute() {
            if (toIndex - fromIndex < THRESHOLD){
                System.out.println(" from index = "+fromIndex+" toIndex="+toIndex);
                //这里就不统计数量
                for(int i = fromIndex;i <= toIndex; i++){
                    pickTaoZi.pick(src,i);
                }
            }else{

                int mid = (fromIndex+toIndex)/2;
                XiaoBaJie left = new XiaoBaJie(src,fromIndex,mid,pickTaoZi);
                XiaoBaJie right = new XiaoBaJie(src,mid + 1,toIndex,pickTaoZi);
                invokeAll(left,right);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ForkJoinPool pool = new ForkJoinPool();
        PanTao[] src = MakePanTaoArray.makeArray();
        IProcessTaoZi processTaoZi = new WuKongProcessImpl();
        IPickTaoZi pickTaoZi = new WuKongPickImpl(processTaoZi);

        long start = System.currentTimeMillis();

        XiaoBaJie xiaoBaJie = new XiaoBaJie(src,0,src.length-1,pickTaoZi);

        //execute执行任务，是异步方法，会立即返回，main线程会继续做事
        pool.execute(xiaoBaJie);

        System.out.println("BaJie is picking.....");

        Thread.sleep(2);
        System.out.println("Please waiting.....");

        while(!xiaoBaJie.isDone()){
            showLog(pool);
            TimeUnit.MILLISECONDS.sleep(100);
        }
//
        pool.shutdown();
        pool.awaitTermination(1,TimeUnit.DAYS);
        showLog(pool);

        xiaoBaJie.join();
        System.out.println(" spend time:"+(System.currentTimeMillis()-start)+"ms");



    }

    //监控Fork/Join池相关方法
    private static void showLog(ForkJoinPool pool) {
        System.out.printf("**********************\n");

        System.out.printf("线程池的worker线程们的数量:%d\n",
                pool.getPoolSize());
        System.out.printf("当前执行任务的线程的数量:%d\n",
                pool.getActiveThreadCount());
        System.out.printf("没有被阻塞的正在工作的线程:%d\n",
                pool.getRunningThreadCount());
        System.out.printf("已经提交给池还没有开始执行的任务数:%d\n",
                pool.getQueuedSubmissionCount());
        System.out.printf("已经提交给池已经开始执行的任务数:%d\n",
                pool.getQueuedTaskCount());
        System.out.printf("线程偷取任务数:%d\n",
                pool.getStealCount());
        System.out.printf("池是否已经终止 :%s\n",
                pool.isTerminated());
        System.out.printf("**********************\n");
    }

}
