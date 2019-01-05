package com.sjw.forkjoin.SunWuKong.Impl;


import com.sjw.forkjoin.service.IProcessTaoZi;
import com.sjw.forkjoin.vo.PanTao;

/**
 * 孙悟空摘桃后的处理
 */
public class WuKongProcessImpl implements IProcessTaoZi {

    @Override
    public void processTaoZi(PanTao taoZi) {
        //放到口袋里，给花果山的猴子们吃
        inBag();
    }

    private void inBag(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
