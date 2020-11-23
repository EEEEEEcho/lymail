package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value="key",required = false)String key,
            @RequestParam(value="page",defaultValue = "1")Integer page,
            @RequestParam(value="rows",defaultValue = "5")Integer rows,
            @RequestParam(value="sortBy",required = false)String sortBy,
            @RequestParam(value="desc",required = false)Boolean desc
    ){
        PageResult<Brand> result = brandService.queryBrandsByPage(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        // 响应json数据要用 @ResponseBody注解
        // 接收json数据要用 @RequestBody注解 ，而且，发送过来的json数据只能由一个对象来接收，
        // 不能分开接收 ,所以，解决办法只能是在前端进行修改，将json对象进行改动，使用qs工具
        // params  变成 this.$qs.stringify(params) 前端做这样的修改
        // 本质上是将json变成了查询字符串 即访问url时 ?后面拼接的参数
        //{name:san,age:18} -> ?name=san&age=18
        this.brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
