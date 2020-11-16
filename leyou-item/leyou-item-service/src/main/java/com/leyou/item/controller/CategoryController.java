package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")     //@RequestParam(value = "pid",defaultValue = "0") 设置defaultvalue 防止用户不传
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        try{
            if(pid == null || pid < 0){
                //.build 工厂方法
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                //参数不合法
                return ResponseEntity.badRequest().build();
            }

            List<Category> categories = this.categoryService.quertyCategoriesByPid(pid);
            if(CollectionUtils.isEmpty(categories)){
                //这是三年工作经验的判断方式 404 资源服务未找到
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categories);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // INTERNAL_SERVER_ERROR 因特网错误状态码
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
