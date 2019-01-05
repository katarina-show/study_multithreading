package com.sjw.forkjoin.SunWuKong;


import com.sjw.forkjoin.MakePanTaoArray;
import com.sjw.forkjoin.SunWuKong.Impl.WuKongPickImpl;
import com.sjw.forkjoin.SunWuKong.Impl.WuKongProcessImpl;
import com.sjw.forkjoin.service.IPickTaoZi;
import com.sjw.forkjoin.service.IProcessTaoZi;
import com.sjw.forkjoin.vo.PanTao;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin的使用--孙悟空
 * RecursiveAction：用于没有返回结果的任务。
 * RecursiveTask：用于有返回结果的任务。
 */
public class ForkJoinWuKong {

  //孙悟空1人摘不完，拔了一根毛，变成很多小悟空
  //需要返回值表示摘了多少桃子，因此使用RecursiveTask，泛型中的参数将作为重写的compute方法的返回值
  private static class XiaoWuKong extends RecursiveTask<Integer> {

      //阈值，数组变成多小的时候，就不再切分了，进行实际业务操作
      private final static int THRESHOLD = 100;
      //蟠桃数组
      private PanTao[] src;
      //开始下标
      private int fromIndex;
      //结束下标
      private int toIndex;
      //摘桃的实现
      private IPickTaoZi pickTaoZi;

      public XiaoWuKong(PanTao[] src, int fromIndex, int toIndex, IPickTaoZi pickTaoZi) {
          this.src = src;
          this.fromIndex = fromIndex;
          this.toIndex = toIndex;
          this.pickTaoZi = pickTaoZi;
      }

      @Override
      protected Integer compute() {
          //小于阈值了，就直接业务操作
          if (toIndex - fromIndex < THRESHOLD){
              int count = 0;
              for(int i = fromIndex; i < toIndex; i++){
                  if (pickTaoZi.pick(src,i)){
                      count ++;
                  }
              }
              return count;
          }else{
              //大于阈值，开始切分，从中间切分
              //获得中间位置mid
              int mid = (fromIndex + toIndex)/2;
              //分成了2部分，数组的左边和右边
              //左边再让1个小悟空做，右边也让1个小悟空做
              XiaoWuKong left = new XiaoWuKong(src,fromIndex,mid,pickTaoZi);
              XiaoWuKong right = new XiaoWuKong(src,mid,toIndex,pickTaoZi);

              //提交给ForkJoin框架去执行，invokeAll会让left和right继续执行compute方法，有点类似递归
              //invokeAll有多个重载方法，不代表只能拆成2个
              invokeAll(left,right);

              //left.join() + right.join()获得最终结果
              //join方法会阻塞，如果需要异步模式，请看八戒的fork join
              return left.join() + right.join();

          }
      }
  }

    public static void main(String[] args) {
      //先要new一个ForkJoinPool
      ForkJoinPool pool = new ForkJoinPool();
      //产出蟠桃
      PanTao[] src = MakePanTaoArray.makeArray();
      IProcessTaoZi processTaoZi = new WuKongProcessImpl();
      IPickTaoZi pickTaoZi = new WuKongPickImpl(processTaoZi);

      long start = System.currentTimeMillis();

      XiaoWuKong xiaoWuKong = new XiaoWuKong(src,0,src.length-1,pickTaoZi);

      //invoke开始执行任务，是同步方法，执行没有完成的话，程序不会继续往后执行
      pool.invoke(xiaoWuKong);

      System.out.println("The count is " + xiaoWuKong.join()
                +" spend time:" + (System.currentTimeMillis()-start) + "ms");

    }

}
