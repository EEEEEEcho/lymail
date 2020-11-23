package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化一个example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria(); //创建查询条件
        //根据name模糊查询，或者根据首字母查询
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%" + key + "%").orEqualTo("letter",key);
        }
        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序条件
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        //包装成pageInfo对象
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //先新增品牌
        //.insert方法:insert into tb_brand(id,name,letter,images) values (null,name,null,null)
        // 会在插入时全部插入，没有提供的字段设置为null
        //.insertSelective方法：则是insert into tb_brand(name) values (name)
        // 没有传的参数不新增 效率更高
//        boolean isInsertSuccess = this.brandMapper.insertSelective(brand) == 1;
        //加了Transactional 进行事务处理后 就不需要再判断了
        this.brandMapper.insertSelective(brand);
        //后新增中间表
//        if(isInsertSuccess){
//            cids.forEach(cid ->{
//                //因为通用mapper只能操作单张表，所以若是设计多表操作则需要自己写sql
//                this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
//            });
//        }
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
    }
}
