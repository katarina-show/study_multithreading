package com.sjw.forkjoin;


import com.sjw.forkjoin.vo.Color;
import com.sjw.forkjoin.vo.PanTao;
import com.sjw.forkjoin.vo.Size;

import java.util.Random;

/**
 * 一个产生 很多各式各样蟠桃 的数组
 */
public class MakePanTaoArray {

    //数组长度
    public static final int ARRAY_LENGTH  = 40000;
    //作为基准的值
    public static final int STANDARD_VAL  = 66694523;

    public static PanTao[] makeArray() {

        //new三个随机数发生器
        Random rColor = new Random();
        Random rSize = new Random();
        Random rYear = new Random();

        //一共40000个蟠桃
        PanTao[] result = new PanTao[ARRAY_LENGTH];

        for(int i = 0; i < ARRAY_LENGTH; i++){
            //填充数组，nextBoolean随机返回Boolean，nextInt生成一个随机的[0,n)的数
            PanTao panTao = new PanTao(
                    rColor.nextBoolean() ? Color.RED:Color.GREEN,
                    rSize.nextBoolean() ? Size.BIG:Size.SMALL,
                    rYear.nextInt(9001));
            result[i] =  panTao;
        }
        return result;
    }

    public static void main(String[] args) {
        PanTao[] panTaos = makeArray();
        for(PanTao panTao:panTaos){
            System.out.println(panTao);
        }
    }

}
