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
@Table(name = "test")
public class Test extends Model {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(unique = true)
    private String name = UUID.randomUUID().toString();
    private Date createTime = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Finder<String, Test> find = new Finder<String, Test>(String.class, Test.class);
}
