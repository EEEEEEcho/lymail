package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
