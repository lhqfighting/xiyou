package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querryGroupByCid(@PathVariable("cid")Long cid){

        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }

    //新增规格组
    @PostMapping("group")
    public ResponseEntity<List<Void>> insertGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.insertGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //更新规格组
    @PutMapping("group")
    public ResponseEntity<List<Void>> updateGroup(@RequestBody SpecGroup specGroup){
        this.specificationService.updateGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //删除规格组
    @DeleteMapping("group/{id}")
    public ResponseEntity<List<Void>> deleteGroup(@PathVariable("id")Long id){
        this.specificationService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询参数集合
     * @param gid  组id
     * @param cid  分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "searching", required = false)Boolean searching){
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));
    }

    //新增参数
    @PostMapping("param")
    public ResponseEntity<List<Void>> insertParam(@RequestBody SpecParam specParam){
        this.specificationService.insertParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //更新参数
    @PutMapping("param")
    public ResponseEntity<List<Void>> updateParam(@RequestBody SpecParam specParam){
        this.specificationService.updateParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //删除参数
    @DeleteMapping("param/{id}")
    public ResponseEntity<List<Void>> deleteParam(@PathVariable("id")Long id){
        this.specificationService.deleteParam(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类查询规格组及组内参数
     * @param cid
     * @return
     */
    @GetMapping("group")
    public ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryListByCid(cid));
    }
}
