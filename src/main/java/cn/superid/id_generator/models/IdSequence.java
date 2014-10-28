package cn.superid.id_generator.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 维 on 2014/9/1.
 */
@Entity
@Table(name = "id_sequence")
public class IdSequence extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static Finder<Long, IdSequence> find = new Finder<Long, IdSequence>(Long.class, IdSequence.class);
}
