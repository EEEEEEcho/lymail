package com.leyou.item.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_category")    //建立实体类和数据库表之间的对应关系
public class Category {
    @Id // 声明这个类中id属性 所对应的是数据库表中的主键字段  不声明的话会采用联合查询的方式
    @GeneratedValue(strategy = GenerationType.IDENTITY) //让通用 Mapper 在执行 insert 操作之后
                                                        // 将数据库自动生成的主键值回写到实体类对象中。
    private Long id;

    private String name;
    private Long parentId;
    private Boolean isParent;   //生成getter和setter时，需要手动加上Is
    private Integer sort;

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

    public Long getParentId() {
        return parentId;
    }

    public void setIsParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
