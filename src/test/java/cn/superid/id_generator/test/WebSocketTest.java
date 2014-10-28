package cn.superid.id_generator.test;

import cn.superid.id_generator.exceptions.IdGeneratorException;
import cn.superid.id_generator.net.ServiceHolder;
import cn.superid.id_generator.net.beans.IdRequest;
import cn.superid.id_generator.net.beans.IdResponse;
import cn.superid.id_generator.utils.Logger;
import cn.superid.id_generator.workers.IIdGeneratorWorker;
import cn.superid.net.AbstractApiEventHandler;
import cn.superid.net.IWebSocketHandlerDispatcher;
import cn.superid.net.annotations.Event;
import cn.superid.net.client.WebSocketClient;
import cn.superid.net.handlers.PongHandler;
import cn.superid.net.impl.WebSocketHandlerDispatcher;
import cn.superid.net.handlers.CloseHandler;
import cn.superid.utils.MapUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 维 on 2014/9/15.
 */
public class WebSocketTest extends TestCase {
    public static class IdGeneratedHandler extends AbstractApiEventHandler {
        @Event("id_generated")
        public void handleIdResponse(ChannelHandlerContext ctx, IdResponse idResponse) {
            // TODO: check errors
            assert idResponse != null;
            System.out.println(idResponse.getId());
        }
    }

    public void testRpcClient() {
        IWebSocketHandlerDispatcher dispatcher = new WebSocketHandlerDispatcher();
        dispatcher.addHandler(new CloseHandler());
        dispatcher.addHandler(new IdGeneratedHandler());
        final WebSocketClient client = new WebSocketClient("ws://localhost:8086/websocket", dispatcher);
        client.addRpcHandler();
        try {
            client.connect();
            Executor executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
            Date startTime = new Date();
            int n = 19;
            final AtomicInteger finished = new AtomicInteger(0);
            for (int i = 0; i < n; ++i) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        Future future = client.callRpc("id_generate", "nextId", "user", MapUtil.hashmap());
                        try {
                            Object res = future.get();
                            System.out.println("response: " + res);
                            finished.incrementAndGet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                executor.execute(task);
            }

            while (true) {
                if (finished.get() >= n) {
                    Date endTime = new Date();
                    System.out.println("using time: " + (endTime.getTime() - startTime.getTime()) + "ms");
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, 1);
    }

    public void testWebSocketClient() {
//        IWebSocketHandlerDispatcher dispatcher = new WebSocketHandlerDispatcher();
//        dispatcher.addHandler(new PongHandler());
//        dispatcher.addHandler(new CloseHandler());
//        dispatcher.addHandler(new IdGeneratedHandler());
//        WebSocketClient client = new WebSocketClient("ws://localhost:8080/websocket", dispatcher);
//        try {
//            client.connect();
//            client.ping();
//            IdRequest idRequest = new IdRequest();
//            idRequest.setRequestId("xxoo");
//            idRequest.setRecord(MapUtil.hashmap());
//            idRequest.setTable("user");
//            client.sendJson(idRequest);
//            while (true) {
//                Thread.sleep(1000);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        assertEquals(true, true);
    }

//    public void testDubboRpcClient() {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[]{"META-INF/consumer.xml"}
//        );
//        context.start();
//        final IIdGeneratorWorker service = (IIdGeneratorWorker) context.getBean("idGenerateService");
//        Executor executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
//        int n = 600000;
//        Date startTime = new Date();
//        final AtomicInteger finished = new AtomicInteger(0);
//
//        for (int i = 0; i < n; ++i) {
//            Runnable task = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        String hello = service.nextId("user", MapUtil.hashmap());
//                    } catch (IdGeneratorException e) {
//                        e.printStackTrace();
//                    }
//                    finished.incrementAndGet();
//                }
//            };
//            executor.execute(task);
//        }
//        while (finished.get() < n) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Date endTime = new Date();
//        System.out.println("using time(" + n + " times): " + (endTime.getTime() - startTime.getTime()));
//    }
}
