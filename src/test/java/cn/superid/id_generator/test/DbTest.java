package cn.superid.id_generator.test;

import cn.superid.id_generator.models.Test;
import cn.superid.id_generator.models.Test2;
import cn.superid.id_generator.utils.Logger;
import com.zoowii.jpa_utils.core.Session;
import junit.framework.TestCase;

import java.util.Date;
import java.util.List;

/**
 * Created by 维 on 2014/9/15.
 */
public class DbTest extends TestCase {
    public void testSelectTest2Table() {
//        Session session = Session.currentSession();
//        session.begin();
//        try {
//            List<Test2> records = Test2.find.where().limit(100000).all();
//            Date start = new Date();
//            for (Test2 record : records) {
//                Test2 test = Test2.find.byId(record.getId());
//            }
//            Date end = new Date();
//            Logger.info("using time: " + (end.getTime() - start.getTime()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.rollback();
//        }
    }

    public void testSelectTestTable() {
//        Session session = Session.currentSession();
//        session.begin();
//        try {
//            List<Test> records = Test.find.where().limit(100000).all();
//            Date start = new Date();
//            for (Test record : records) {
//                Test test = Test.find.byId(record.getId());
//            }
//            Date end = new Date();
//            Logger.info("using time: " + (end.getTime() - start.getTime()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.rollback();
//        }
    }

    public void testInsertTestTable() {
//        Session session = Session.currentSession();
//        session.begin();
//        try {
//            Test last = null;
//            for (int i = 0; i < 10000; ++i) {
//                Test test = new Test();
//                test.save();
//                last = test;
//            }
//            Logger.info("last id: " + last.getId() + " and name: " + last.getName());
//            session.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.rollback();
//        }
        assertEquals(1, 1);
    }

    public void testInsertNumberTestTable() {
        assertEquals(1, 1);
//        Session session = Session.currentSession();
//        session.begin();
//        try {
//            Test2 last = null;
//            for (int i = 0; i < 10000; ++i) {
//                Test2 test = new Test2();
//                test.save();
//                last = test;
//            }
//            Logger.info("last id: " + last.getId() + " and name: " + last.getName());
//            session.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.rollback();
//        }
    }

}
