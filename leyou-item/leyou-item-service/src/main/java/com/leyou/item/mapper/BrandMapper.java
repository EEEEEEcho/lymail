package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    //自定义一个通用mapper的查询方法，
    @Insert("insert into tb_category_brand (category_id,brand_id) values(#{cid},#{brandId})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("brandId") Long brandId);

    @Select("select * from tb_brand a inner join tb_category_brand b on a.id=b.brand_id where b.category_id=#{cid}")
    List<Brand> selectBrandsByCid(Long cid);
}
