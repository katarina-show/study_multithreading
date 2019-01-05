package com.sjw;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 线程间的通信--通过流PipedWriter、PipedReader
 * 限制非常明显，只适用于1对1的线程通信
 */
public class PipeTransfer {

    private static class Print implements Runnable{
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
           int receive;
            try {
                //read是阻塞方法，一旦main线程中调用了writer的write方法，这里就会成功的read到并输出
                while((receive = in.read())!= -1){
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //必须进行连接
        out.connect(in);

        Thread t1 = new Thread(new Print(in),"PrintThread");
        t1.start();

        int receive;
        try {
            while((receive = System.in.read())!= -1){
                out.write(receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

}
