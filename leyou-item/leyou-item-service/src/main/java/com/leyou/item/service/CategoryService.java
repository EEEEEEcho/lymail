package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点的ID查询子节点
     * @param pid
     * @return
     */
    public List<Category> quertyCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setIsParentId(pid);
         return this.categoryMapper.select(category);
    }

    public List<String> queryNamesByIds(List<Long> ids){
        //根据多个ID查询
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        //处理一个旧的集合，返回一个新的集合用stream表达式,返回的是一个由category的name组成的集合
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }
}
