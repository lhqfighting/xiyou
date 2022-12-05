package com.leyou.page.service;

import com.leyou.common.utils.ThreadUtils;
import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PageService {

    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specificationClient;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long spuId) {
        //查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        //查询skus
        List<Sku> skus = spu.getSkus();
        //查询detail
        SpuDetail detail = spu.getSpuDetail();
        //查询brand
        Brand brand = brandClient.queryBrandById(spuId);
        //查询商品分类
        List<Category> categories = categoryClient.queryCategotyByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        //查询规格参数
        List<SpecGroup> specs = specificationClient.queryListByCid(spu.getCid3());

        Map<String, Object> model = new HashMap<>();
        model.put("title", spu.getTitle());
        model.put("subTitle", spu.getSubTitle());
        model.put("detail", detail);
        model.put("skus", skus);
        model.put("brand", brand);
        model.put("categories", categories);
        model.put("specs", specs);

        return model;
    }

    public void createHtml(Long spuId){
        //上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        //输出流
        File file = new File("D:\\JavaEssentialtools\\nginx-1.12.2\\html\\item" + spuId + ".html");

        //判断文件是否存在
        if (file.exists()){
            //以及存在则删除
            file.delete();
        }

        PrintWriter writer = null;
        try{
            writer = new PrintWriter(file,"UTF-8");
            //生成HTML
            templateEngine.process("item",context,writer);
        } catch (Exception e) {
            log.error("[静态页服务] 生成静态页异常！",e);
        }finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    public void deleteHtml(Long spuId) {
        File file = new File("D:\\JavaEssentialtools\\nginx-1.12.2\\html\\item" + spuId + ".html");
        if (file.exists()){
            file.delete();
        }
    }

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    public void asyncExcute(Long spuId) {
        ThreadUtils.execute(()->createHtml(spuId));
        /*ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                createHtml(spuId);
            }
        });*/
    }

}
