package com.sjw.forkjoin.BaJie.impl;


import com.sjw.forkjoin.service.IPickTaoZi;
import com.sjw.forkjoin.service.IProcessTaoZi;
import com.sjw.forkjoin.vo.Color;
import com.sjw.forkjoin.vo.PanTao;
import com.sjw.forkjoin.vo.Size;

/**
 * 八戒摘桃的实现
 */
public class BaJiePickImpl implements IPickTaoZi {

    private IProcessTaoZi processTaoZi;

    public BaJiePickImpl(IProcessTaoZi processTaoZi) {
        this.processTaoZi = processTaoZi;
    }

    @Override
    public boolean pick(PanTao[] src, int index) {
        //八戒认为，只要熟了（RED）+ 大桃子（BIG）就可以摘，多少年1熟不重要，其他的桃子就不摘
        if(src[index].getColor() == Color.RED &&
                src[index].getSize() == Size.BIG){
            processTaoZi.processTaoZi(src[index]);
            return true;
        }else{
            return false;
        }
    }
}
