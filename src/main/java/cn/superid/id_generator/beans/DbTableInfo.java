package cn.superid.id_generator.beans;

import java.util.List;

/**
 * Created by 维 on 2014/9/3.
 */
public class DbTableInfo {
    private String name;
    private List<String> splitColumns;
    private String group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSplitColumns() {
        return splitColumns;
    }

    public void setSplitColumns(List<String> splitColumns) {
        this.splitColumns = splitColumns;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
