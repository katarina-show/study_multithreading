package com.sjw.vo;

import java.util.List;

/**
 * 待处理文档实体类
 */
public class PendingDocVo {
    //待处理文档名称
    private final String docName;
    //待处理文档中题目id列表
    private final List<Integer> problemVoList;

    public PendingDocVo(String docName, List<Integer> problemVoList) {
        this.docName = docName;
        this.problemVoList = problemVoList;
    }

    public String getDocName() {
        return docName;
    }

    public List<Integer> getProblemVoList() {
        return problemVoList;
    }
}
