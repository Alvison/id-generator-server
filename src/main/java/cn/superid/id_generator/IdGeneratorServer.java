package cn.superid.id_generator;

import cn.superid.id_generator.exceptions.IdGeneratorException;
import cn.superid.id_generator.net.*;
import cn.superid.id_generator.net.handlers.*;
import cn.superid.id_generator.net.services.IdGenerateService;
import cn.superid.id_generator.utils.Logger;
import cn.superid.net.IWebSocketHandlerDispatcher;
import cn.superid.net.SSLUtil;
import cn.superid.net.handlers.CloseHandler;
import cn.superid.net.handlers.PingHandler;
import cn.superid.net.impl.WebSocketHandlerDispatcher;
import cn.superid.net.server.WebSocketServer;
import cn.superid.utils.MapUtil;
import cn.superid.id_generator.workers.IIdGeneratorWorker;
import io.netty.handler.ssl.SslContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;


/**
 * Created by 维 on 2014/8/29.
 */
public class IdGeneratorServer {
    public static final boolean SSL = System.getProperty("ssl") != null;
    public static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8086"));

    private static void startApiServer() {
        IWebSocketHandlerDispatcher dispatcher = new WebSocketHandlerDispatcher();
        dispatcher.addHandler(new PingHandler());
        dispatcher.addHandler(new CloseHandler());
        dispatcher.addHandler(new DemoEventHandler());
        dispatcher.addHandler(new IdGenerateHandler());
        final SslContext sslCtx = SSL ? SSLUtil.makeSslContext() : null;
        try {
            WebSocketServer server = new WebSocketServer(PORT, dispatcher);
            server.addRpcHandler();
            server.addRpcService(new IdGenerateService());
            server.setSslCtx(sslCtx);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startDubboApiServer() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/provider.xml"}
        );
        context.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Logger.info("hello");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/beans.xml"}
        );
//        IIdMerger idMerger = (IIdMerger) context.getBean("idMerge", IIdMerger.class);
//        Injector injector = Guice.createInjector(new IdGeneratorMudule());
//        IIdMerger idMerger = injector.getInstance(IIdMerger.class);
//        Logger.info(idMerger.generateFinalId(100, "group1", 200));
//        IIdGeneratorWorker worker = injector.getInstance(IIdGeneratorWorker.class);
        IIdGeneratorWorker worker = (IIdGeneratorWorker) context.getBean("idGeneratorWorker");
        ServiceHolder.setIdGeneratorWorker(worker);
        Map<String, Object> record = MapUtil.hashmap("name", "superid", "age", "23");
        try {
            Logger.info(worker.nextId("group1"));
            Logger.info(worker.nextId("user", record));
        } catch (IdGeneratorException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                startApiServer();
//            }
//        }).start();
        startApiServer();
//        startDubboApiServer();
    }
}
