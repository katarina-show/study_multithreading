package com.sjw.completionService;

import java.util.concurrent.*;

/**
 * 在ExecutorService的submit方法中可以获取返回值，通过Future的get方法
 * 但是这个Future类存在缺陷，Future接口调用get（）方法取得处理后的返回结果时具有阻塞性
 * 也就是说调用Future的get方法时，任务没有执行完成，则get方法要一直阻塞等到任务完成为止
 * 这样大大的影响了系统的性能，这就是Future的最大缺点。
 * 为此，java1.5以后提供了CompletionService来解决这个问题。
 *
 */
public class CompletionTest {
    private final int POOL_SIZE  = 5;
    private final int TOTAL_TASK = 10;

    //方法一，自己维护一个Collection或者BlockingQueue来保存 线程池.submit方法返回的Future存根
    private void testByQueue() throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<>();
        // 向里面扔任务
        for(int i = 0; i < TOTAL_TASK; i++){
            Future<String> future = pool.submit(new WorkTask("ExecTask" + i));
            //用自己定义的容器来保存这些Future
            queue.add(future);
        }

        // 检查线程池任务执行结果，遍历自己的容器，然后依次get
        for(int i = 0; i < TOTAL_TASK; i++){
            //缺陷：只能按顺序拿出结果，而不能拿出已经执行完的线程的结果
            System.out.println("ExecTask:" + queue.take().get());
        }

        pool.shutdown();

    }

    // 方法二，通过CompletionService来实现获取线程池中任务的返回结果
    private void testByCompletion() throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        //CompletionService只有1个实现
        CompletionService<String> service = new ExecutorCompletionService<>(pool);

        // 向里面扔任务
        for(int i = 0; i < TOTAL_TASK; i++){
            service.submit(new WorkTask("ExecTask" + i) );
        }

        // 检查线程池任务执行结果
        for(int i = 0; i < TOTAL_TASK; i++){
            Future<String> future = service.take();
            System.out.println("CompletionService:" + future.get());
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletionTest  completionTest = new CompletionTest();
        //completionTest.testByQueue();
        completionTest.testByCompletion();
    }

}
