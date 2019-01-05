package com.sjw;

import com.sjw.aim.MakeSrcDoc;
import com.sjw.vo.PendingDocVo;
import com.sjw.aim.ProblemBank;
import com.sjw.service.DocService;

import java.util.List;

/**
 * web服务启动 模拟
 */
public class SingleWeb {
    public static void main(String[] args) {

        System.out.println("题库开始初始化...........");
        ProblemBank.initBank();
        System.out.println("题库初始化完成。");

        //生成待处理文档
        List<PendingDocVo> docList = MakeSrcDoc.makeDoc(2);
        long startTotal = System.currentTimeMillis();
        for(PendingDocVo doc:docList){
            System.out.println("开始处理文档：" + doc.getDocName() + ".......");

            long start = System.currentTimeMillis();

            //生成到本地，返回本地路径
            String localName = DocService.makeDoc(doc);
            System.out.println("文档" + localName + "生成耗时："
                    + (System.currentTimeMillis() - start) + "ms");


            start = System.currentTimeMillis();

            //上传到服务器，返回url地址
            String remoteUrl = DocService.upLoadDoc(localName);
            System.out.println("已上传至[" + remoteUrl + "]耗时："
                    + (System.currentTimeMillis() - start) + "ms");
        }
        System.out.println("共耗时：" + (System.currentTimeMillis()-startTotal) + "ms");
    }
}
