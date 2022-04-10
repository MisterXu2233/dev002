package com.fc.service.impl;

import com.fc.dao.CollectionMapper;
import com.fc.entity.Collection;
import com.fc.service.CollectionService;
import com.fc.vo.DateVO;
import com.fc.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Long id) {
        //返回给前端的结果
        ResultVO resultVO;

        // 分页相关的参数
        DateVO<Collection> userDateVO;

        //结果中data对应的用户数组
        List<Collection> collections;

        //说明传递了id,那就是findById
        if (id != null) {
            collections = new ArrayList<>();

            //查询
            Collection collection = collectionMapper.selectByPrimaryKey(id);

            //没有查到用户的情况
            if(collection == null) {
                userDateVO = new DateVO<>(0L,collections,pageNum,pageSize);

                resultVO = new ResultVO(4000,"查询无此收藏表",false,userDateVO);

            } else {
                //查到了用户扔到集合中
                collections.add(collection);

                userDateVO = new DateVO<>(1L,collections,pageNum,pageSize);

                resultVO = new ResultVO(1000,"查到了此收藏表",true,userDateVO);

            }
        } else {
            //开启分页
            PageHelper.startPage(pageNum,pageSize);

            collections = collectionMapper.selectByExample(null);

            //如果数据库是空的，一个人都没查到
            if (collections.size() == 0) {
                userDateVO = new DateVO<>(0L,collections,pageNum,pageSize);

                resultVO = new ResultVO(4100,"没有收藏表",false,userDateVO);

                //查到了
            } else {

                //封装pageInfo,为了获取总数据量
                PageInfo<Collection> pageInfo = new PageInfo<>(collections);

                userDateVO = new DateVO<>(pageInfo.getTotal(),collections,pageNum,pageSize);

                resultVO = new ResultVO(1100,"收藏表查询成功",true,userDateVO);

            }

        }

        return resultVO;
    }

    @Override
    public ResultVO add(Collection collection) {
        ResultVO vo;

        //判断是否存在创建时间，没有就自己加上
        if (collection.getCreateTime() == null) {
            collection.setCreateTime(new Date());
        }

        int affectedRows = collectionMapper.insertSelective(collection);

        if (affectedRows > 0) {
            vo = new ResultVO(1000,"添加收藏表成功",true,collection);
        } else {
            vo = new ResultVO(5000,"添加收藏表失败",false,null);

        }

        return vo;
    }

    @Override
    public ResultVO update(Collection collection) {
        int affectedRows = collectionMapper.updateByPrimaryKeySelective(collection);

        ResultVO vo;

        if (affectedRows > 0) {
            //修改完成之后，在重新查询一次，保证返回给前端的是最全的数据
            collection = collectionMapper.selectByPrimaryKey(collection.getId());

            vo = new ResultVO(1000,"修改收藏表成功",true,collection);
        } else {
            vo = new ResultVO(5000,"修改收藏表失败",false,null );

        }

        return vo;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = collectionMapper.deleteByPrimaryKey(id);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(1000,"删除收藏表成功",true,null);
        } else {
            vo = new ResultVO(5000,"删除收藏表失败",false,null );

        }

        return vo;
    }
}
