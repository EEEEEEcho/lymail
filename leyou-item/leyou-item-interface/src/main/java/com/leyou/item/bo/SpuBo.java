package com.leyou.item.bo;

import com.leyou.item.pojo.Spu;

public class SpuBo extends Spu {
    /**
     * 由于不能随便修改pojo中的实体类。
     * 如果想要新增属性，则需要对原表进行扩展
     */
    private String cname;
    private String bname;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
