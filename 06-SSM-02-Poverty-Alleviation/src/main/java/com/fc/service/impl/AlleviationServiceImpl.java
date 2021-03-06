package com.fc.service.impl;

import com.fc.dao.AlleviationMapper;
import com.fc.entity.Alleviation;
import com.fc.service.AlleviationService;
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
public class AlleviationServiceImpl implements AlleviationService {
    @Autowired
    private AlleviationMapper alleviationMapper;


    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Long id) {
        //返回给前端的结果
        ResultVO resultVO;

        // 分页相关的参数
        DateVO<Alleviation> dateVO;

        //结果中data对应的用户数组
        List<Alleviation> alleviations;

        //说明传递了id,那就是findById
        if (id != null) {
            alleviations = new ArrayList<>();

            //查询
            Alleviation alleviation = alleviationMapper.selectByPrimaryKey(id);

            //没有查到用户的情况
            if(alleviation == null) {
                dateVO = new DateVO<>(0L,alleviations,pageNum,pageSize);

                resultVO = new ResultVO(4000,"未查到此政策",false,dateVO);

            } else {
                //如果查询单个，那么点击量应该加1
                click(alleviation.getId(), null);

                //更新点击的次数
                alleviation.setClickNum(alleviation.getClickNum() + 1);

                //查到了用户扔到集合中
                alleviations.add(alleviation);

                dateVO = new DateVO<>(1L,alleviations,pageNum,pageSize);

                resultVO = new ResultVO(200,"查到了该政策",true,dateVO);

            }
        } else {
            //开启分页
            PageHelper.startPage(pageNum,pageSize);

            alleviations = alleviationMapper.selectByExample(null);

            //如果数据库是空的，一个人都没查到
            if (alleviations.size() == 0) {
                dateVO = new DateVO<>(0L,alleviations,pageNum,pageSize);

                resultVO = new ResultVO(4100,"没有扶贫政策",false,dateVO);

                //查到了
            } else {

                //封装pageInfo,为了获取总数据量
                PageInfo<Alleviation> pageInfo = new PageInfo<>(alleviations);

                dateVO = new DateVO<>(pageInfo.getTotal(),alleviations,pageNum,pageSize);

                resultVO = new ResultVO(200,"扶贫政策查询成功",true,dateVO);

            }

        }

        return resultVO;
    }

    @Override
    public ResultVO add(Alleviation alleviation) {
        ResultVO vo;

        //判断是否存在创建时间，没有就自己加上
        if (alleviation.getCreateTime() == null) {
            alleviation.setCreateTime(new Date());
        }

        //新创建的扶贫政策，点击两应该是0
        alleviation.setClickNum(0);
        alleviation.setLastClickTime(null);

        int affectedRows = alleviationMapper.insertSelective(alleviation);

        if (affectedRows > 0) {
            vo = new ResultVO(200,"添加政策成功",true,alleviation);
        } else {
            vo = new ResultVO(5000,"添加政策失败",false,null);

        }

        return vo;
    }

    @Override
    public ResultVO update(Alleviation alleviation) {
        int affectedRows = alleviationMapper.updateByPrimaryKeySelective(alleviation);

        ResultVO vo;

        if (affectedRows > 0) {
            //修改完成之后，在重新查询一次，保证返回给前端的是最全的数据
            alleviation = alleviationMapper.selectByPrimaryKey(alleviation.getId());

            vo = new ResultVO(200,"修改政策成功",true,alleviation);
        } else {
            vo = new ResultVO(5000,"修改政策失败",false,null );

        }

        return vo;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = alleviationMapper.deleteByPrimaryKey(id);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(200,"删除政策成功",true,null);
        } else {
            vo = new ResultVO(5000,"删除政策失败",false,null );

        }

        return vo;
    }

    @Override
    public ResultVO click(Long id, Date lastClickTime) {
        if (lastClickTime == null) {
            lastClickTime = new Date();
        }

        Integer affectedRows = alleviationMapper.click(id,lastClickTime);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(200,"播放量加1成功",true,null);
        } else {
            vo = new ResultVO(5000,"播放量加1失败",false,null );

        }

        return vo;
    }
}
