package com.sjw.vo;

import java.util.concurrent.Future;

/**
 * 并发题目处理时，返回处理的题目结果
 */
public class MultiProblemVo {

    private final String problemText;//要么就是题目处理后的文本;
    private final Future<ProblemCacheVo> problemFuture;//处理题目的任务

    //其中1个必然为null，提供2个构造

    public MultiProblemVo(String problemText) {
        this.problemText = problemText;
        this.problemFuture = null;
    }

    public MultiProblemVo(Future<ProblemCacheVo> problemFuture) {
        this.problemFuture = problemFuture;
        this.problemText = null;
    }

    public String getProblemText() {
        return problemText;
    }

    public Future<ProblemCacheVo> getProblemFuture() {
        return problemFuture;
    }
}
