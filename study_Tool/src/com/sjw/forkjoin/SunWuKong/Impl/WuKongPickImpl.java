package com.sjw.forkjoin.SunWuKong.Impl;


import com.sjw.forkjoin.service.IPickTaoZi;
import com.sjw.forkjoin.service.IProcessTaoZi;
import com.sjw.forkjoin.vo.Color;
import com.sjw.forkjoin.vo.PanTao;
import com.sjw.forkjoin.vo.Size;

/**
 * 孙悟空摘桃的实现
 */
public class WuKongPickImpl implements IPickTaoZi {

    private IProcessTaoZi processTaoZi;

    public WuKongPickImpl(IProcessTaoZi processTaoZi) {
        this.processTaoZi = processTaoZi;
    }

    @Override
    public boolean pick(PanTao[] src, int index) {
        //孙悟空认为，熟了的桃子（RED）+ 大桃子（RED）+ 至少6000年1熟的桃子，才去摘，否则没有摘的必要
        if(src[index].getColor() == Color.RED &&
                src[index].getSize() == Size.BIG &&
                src[index].getYear() >= 6000){
            processTaoZi.processTaoZi(src[index]);
            return true;
        }else{
            return false;
        }
    }
}
