package com.sjw.forkjoin.service;


import com.sjw.forkjoin.vo.PanTao;

/**
 * 摘桃子的接口
 */
public interface IPickTaoZi {
    boolean pick(PanTao[] src, int index);
}
