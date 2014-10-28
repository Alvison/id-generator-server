package cn.superid.id_generator.services.impl;

import cn.superid.id_generator.models.IdSequence;
import cn.superid.id_generator.services.IDbIdSequenceService;
import com.zoowii.jpa_utils.core.Session;
import org.springframework.stereotype.Component;

/**
 * Created by 维 on 2014/9/1.
 */
@Component
public class DbIdSequenceService implements IDbIdSequenceService {
    @Override
    public long nextUniqueId() {
        Session session = Session.currentSession();
        session.begin();
        try {
            IdSequence idSequence = new IdSequence();
            idSequence.save();
            session.commit();
            return idSequence.getId();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
            return 0;
        }
    }
}
