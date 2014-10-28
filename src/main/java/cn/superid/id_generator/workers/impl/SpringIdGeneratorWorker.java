package cn.superid.id_generator.workers.impl;

import cn.superid.id_generator.exceptions.IdGeneratorException;
import cn.superid.id_generator.workers.IIdGeneratorWorker;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by 维 on 2014/9/20.
 */
public class SpringIdGeneratorWorker implements IIdGeneratorWorker {
    private final IIdGeneratorWorker proxy;

    public SpringIdGeneratorWorker() {
//        Injector injector = Guice.createInjector(new IdGeneratorMudule());
//        this.proxy = injector.getInstance(IIdGeneratorWorker.class);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/beans.xml"}
        );
        this.proxy = (IIdGeneratorWorker) context.getBean("idGeneratorWorker", IIdGeneratorWorker.class);
    }

    @Override
    public String nextId(String tableName, Map<String, Object> record) throws IdGeneratorException {
        return this.proxy.nextId(tableName, record);
    }

    @Override
    public String nextId(String group) throws IdGeneratorException {
        return this.proxy.nextId(group);
    }
}
