package com.sjw.rpcmode;

import com.sjw.aim.Consts;
import com.sjw.aim.MakeSrcDoc;
import com.sjw.aim.ProblemBank;
import com.sjw.service.DocService;
import com.sjw.vo.PendingDocVo;

import java.util.List;
import java.util.concurrent.*;

/**
 * 服务的拆分，rpc服务
 */
public class RpcMode {

    //生成文档的线程池
    private static ExecutorService docMakeService = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE*2);
    //上传文档的线程池
    private static ExecutorService docUploadService = Executors.newFixedThreadPool(Consts.THREAD_COUNT_BASE*2);

    //用CompletionService可以让先生成好的文档先上传
    private static CompletionService docCompletionService = new ExecutorCompletionService(docMakeService);
    //uploadCompletionService可以get到已经在生成好的本地文档路径，同时由于上传时间不等，使调用者可以先拿到上传好的url
    private static CompletionService uploadCompletionService = new ExecutorCompletionService(docUploadService);


    //生成文档的线程---生产者，需要返回本地路径地址
    private static class MakeDocTask implements Callable<String>{
        private PendingDocVo pendingDocVo;

        public MakeDocTask(PendingDocVo pendingDocVo) {
            this.pendingDocVo = pendingDocVo;
        }

        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();

            //String localName = DocService.makeDoc(pendingDocVo);

            //改为调用makeAsyn方法
            String localName = DocService.makeAsyn(pendingDocVo);

            System.out.println("文档"+localName+"生成耗时："
                    +(System.currentTimeMillis()-start)+"ms");
            return localName;
        }
    }


    //上传文档的线程，返回文档的url
    private static class UploadDocTask implements Callable<String>{
        private String localName;

        public UploadDocTask(String localName) {
            this.localName = localName;
        }

        @Override
        public String call() throws Exception {
            long  start = System.currentTimeMillis();
            String remoteUrl = DocService.upLoadDoc(localName);
            System.out.println("已上传至["+remoteUrl+"]耗时：" + (System.currentTimeMillis() - start) + "ms");
            return remoteUrl;
        }
    }

    /**
     * 生产者--消费者优化
     */
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        System.out.println("题库开始初始化...........");
        ProblemBank.initBank();
        System.out.println("题库初始化完成。");

        List<PendingDocVo> docList = MakeSrcDoc.makeDoc(60);

        long startTotal = System.currentTimeMillis();

        for(PendingDocVo doc:docList){
            docCompletionService.submit(new MakeDocTask(doc));
        }

        for(PendingDocVo doc:docList){
            Future<String> futureLocalName = docCompletionService.take();
            uploadCompletionService.submit(new UploadDocTask(futureLocalName.get()));
        }

        for(PendingDocVo doc:docList){
            //把上传后的网络存储地址拿到
            uploadCompletionService.take().get();
        }
        System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal)+"ms");
    }
}
