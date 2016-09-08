package org.biac.manage.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.biac.manage.entity.Area;
import org.biac.manage.service.AreaService;
import org.biac.manage.service.StoreInfoService;
import org.biac.manage.utils.FileManager;
import org.biac.manage.utils.JsonUtil;
import org.biac.manage.utils.MD5Util;
import org.biac.manage.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Song on 2016/7/19.
 */
@Controller
@RequestMapping(value = "/test")
public class Test {
    @Autowired
    private StoreInfoService storeInfoService;
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/mail")
    public void mail(){
        MailUtil.sendEmail("1147649695@qq.com","激活测试邮件","<a href='www.baidu.com'>点击激活</a>");
    }

    @RequestMapping(value = "/tojson")
    public void tojson (HttpServletResponse response) throws IOException{
        String [] list = {"asd","0123456","asdqwe"};
        response.getWriter().write(JsonUtil.statusResponse(0,"test",list));
    }

    @RequestMapping(value = "/toobj")
    public void toobj (@RequestParam String data) throws IOException{
        List<String> list = JsonUtil.toObject(data,List.class);
        for (String str:list) {
            System.out.println(str);
        }
    }

    @RequestMapping(value = "/toobj2")
    public void toobj2 (@RequestParam String data) throws IOException{
        String [] list = data.split(",");
        for (String str:list) {
            System.out.println(str);
        }
    }
    @RequestMapping(value = "/transac")
    public void transac () throws IOException{
        try{
            if(0!= test())  System.out.println("ERROR");
            else System.out.println("GET");
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    private int test(){
        storeInfoService.test();
        return  0;
    }

    @RequestMapping(value = "/encode")
    public void encode (@RequestParam String key) throws IOException{
        MD5Util.encode(key);
    }

    @RequestMapping(value = "/area")
    public void area (HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        List<Area> list = areaService.queryByCode(null);
        response.getWriter().write(JsonUtil.statusResponse(0,"",list));
    }

    @RequestMapping(value = "/toarea")
    public String toarea () throws IOException{
        return "test/area";
    }

    @RequestMapping(value = "/tofile")
    public String tofile () throws IOException{
        return "test/file";
    }

    @RequestMapping(value = "/file")
    public void file (HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
            factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
            /*//设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);*/
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传进度
            upload.setProgressListener(new ProgressListener(){
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                }
            });
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return;
            }

            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
            upload.setFileSizeMax(1024*1024*10);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
            upload.setSizeMax(1024*1024*30);
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //得到上传文件的扩展名
                    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
                    filename = filename.substring(0,filename.lastIndexOf("."));
                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    System.out.println("上传的文件的扩展名是："+fileExtName);
                    //保存文件
                    FileManager fileManager = new FileManager();
                    if(FileManager.ERROR.equals(fileManager.save(filename,fileExtName,"song",item.getInputStream()))){
                        response.getWriter().write(JsonUtil.statusResponse(1,"上传文件失败",null));
                    }else  response.getWriter().write(JsonUtil.statusResponse(0,"上传文件成功",fileManager.getFileURI(filename,fileExtName)));
                }
            }
        }catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            response.getWriter().write(JsonUtil.statusResponse(1,"单个文件超出最大值！！！",null));
        }catch (FileUploadBase.SizeLimitExceededException e) {
            e.printStackTrace();
            response.getWriter().write(JsonUtil.statusResponse(1,"上传文件的总的大小超出限制的最大值！！！",null));
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(JsonUtil.statusResponse(1,"其他异常，上传失败！！！",null));
        }
        return;
    }

}
