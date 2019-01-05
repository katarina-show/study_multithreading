package com.sjw.forkjoin.BaJie.impl;

import com.sjw.forkjoin.service.IProcessTaoZi;
import com.sjw.forkjoin.vo.PanTao;

/**
 * 八戒摘完桃的处理
 */
public class BaJieProcessImpl implements IProcessTaoZi {
    @Override
    public void processTaoZi(PanTao taoZi) {
        //一口吃了
        eat();
    }

    private void eat(){
        try {
            //吃桃子比放包里稍微慢1点
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
