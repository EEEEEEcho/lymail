package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class UploadService {
    //定义一个合法文件类型白名单
    private static final List<String> content_types = Arrays.asList("image/gif","image/jpeg");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);
    @Autowired
    private FastFileStorageClient fileStorageClient;

    public String uploadImage(MultipartFile file){
        System.out.println("HHHH");
        String originalFilename = file.getOriginalFilename();
        //检验文件类型
        String contentType = file.getContentType();
        System.out.println("ContentType:" + contentType);
        if(! content_types.contains(contentType)){
            //输出日志
            LOGGER.info("文件类型不合法: {}" ,originalFilename);
            //返回空值
            return null;
        }
        //校验文件的内容，防止恶意脚本更改后缀名上传
        //使用ImageIO这个工具，如果返回值为空，就是非图片，否则就是图片
        BufferedImage read = null;
        try {
            read = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            LOGGER.info("文件读取失败:{}" + originalFilename);
            return null;
        }
        if(read == null){
            LOGGER.info("文件内容不合法:{}",originalFilename);
            return null;
        }
        //保存到服务器
        try {
            String lastfixName = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = fileStorageClient.uploadFile(file.getInputStream(), file.getSize(),lastfixName, null);
//            file.transferTo(new File("G:\\lymail\\images\\" + originalFilename));
            //返回url进行回显
//            return "http://image.leyou.com/" + originalFilename;
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("文件读取路径非法:{}",originalFilename);
            return null;
        }
    }
}
