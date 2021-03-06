package cn.superid.id_generator.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by 维 on 2014/9/5.
 */
@Entity
@Table(name = "test2")
public class Test2 extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name = UUID.randomUUID().toString();
    private Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static Finder<Long, Test2> find = new Finder<Long, Test2>(Long.class, Test2.class);
}
