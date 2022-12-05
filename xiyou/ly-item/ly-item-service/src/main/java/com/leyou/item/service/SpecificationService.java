package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SpecParamMapper specParamMapper;

    @Autowired
    private SpecGroupMapper specGroupMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(list)) {
            //没查到
            throw new LyException(ExceptionEnum.SPEC_BRAND_NOT_FOUND);
        }
        return list;
    }

    @Transactional
    public void insertGroup(SpecGroup specGroup) {
        this.specGroupMapper.insertSelective(specGroup);
    }


    @Transactional
    public void updateGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKey(specGroup);
    }

    @Transactional
    public void deleteGroup(Long id) {
        this.specGroupMapper.deleteByPrimaryKey(id);
    }

    //根据查询参数集合
    public List<SpecParam> queryParamList(Long gid,Long cid,Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.SPEC_BRAND_NOT_FOUND);
        }
        return list;
    }

    @Transactional
    public void insertParam(SpecParam specParam) {
        this.specParamMapper.insertSelective(specParam);
    }

    @Transactional
    public void updateParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }

    @Transactional
    public void deleteParam(Long id) {
        this.specParamMapper.deleteByPrimaryKey(id);
    }

    public List<SpecGroup> queryListByCid(Long cid) {
        //查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        //查询当前分类下的参数
        List<SpecParam> specParams = queryParamList(null, cid, null);
        //先把规格参数变成map，map的key是规格组id，值是组下的所有参数
        Map<Long,List<SpecParam>> map = new HashMap<>();

        for (SpecParam specParam : specParams) {
            if (!map.containsKey(specParam.getId())){
                //这个组id在map中不存在，新增一个list
                map.put(specParam.getGroupId(),new ArrayList<>());
            }
            map.get(specParam.getGroupId()).add(specParam);
        }
        //填充param到group
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }

        return specGroups;
    }
}
