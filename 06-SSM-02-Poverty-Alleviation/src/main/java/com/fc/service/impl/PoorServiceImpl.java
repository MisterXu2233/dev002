package com.fc.service.impl;

import com.fc.dao.PoorMapper;
import com.fc.entity.Poor;
import com.fc.entity.PoorWithBLOBs;
import com.fc.service.PoorService;
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
public class PoorServiceImpl implements PoorService {
    @Autowired
    private PoorMapper poorMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Long id) {
        //返回给前端的结果
        ResultVO resultVO;

        // 分页相关的参数
        DateVO<Poor> userDateVO;

        //结果中data对应的用户数组
        List<Poor> poors;

        //说明传递了id,那就是findById
        if (id != null) {
            poors = new ArrayList<>();

            //查询
            Poor poor = poorMapper.selectByPrimaryKey(id);

            //没有查到用户的情况
            if(poor == null) {
                userDateVO = new DateVO<>(0L,poors,pageNum,pageSize);

                resultVO = new ResultVO(4000,"查询无此贫困户",false,userDateVO);

            } else {
                //查到了用户扔到集合中
                poors.add(poor);

                userDateVO = new DateVO<>(1L,poors,pageNum,pageSize);

                resultVO = new ResultVO(1000,"查到了该贫困户",true,userDateVO);

            }
        } else {
            //开启分页
            PageHelper.startPage(pageNum,pageSize);

            poors = poorMapper.selectByExample(null);

            //如果数据库是空的，一个人都没查到
            if (poors.size() == 0) {
                userDateVO = new DateVO<>(0L,poors,pageNum,pageSize);

                resultVO = new ResultVO(4100,"没有贫困户",false,userDateVO);

                //查到了
            } else {

                //封装pageInfo,为了获取总数据量
                PageInfo<Poor> pageInfo = new PageInfo<>(poors);

                userDateVO = new DateVO<>(pageInfo.getTotal(),poors,pageNum,pageSize);

                resultVO = new ResultVO(1100,"用户查询成功",true,userDateVO);

            }

        }

        return resultVO;
    }

    @Override
    public ResultVO add(PoorWithBLOBs poor) {
        ResultVO vo;

        //判断是否存在创建时间，没有就自己加上
        if (poor.getCreateTime() == null) {
            poor.setCreateTime(new Date());
        }

        int affectedRows = poorMapper.insertSelective(poor);

        if (affectedRows > 0) {
            vo = new ResultVO(1000,"添加贫困户成功",true,poor);
        } else {
            vo = new ResultVO(5000,"添加贫困户失败",false,null);

        }

        return vo;
    }

    @Override
    public ResultVO update(PoorWithBLOBs poor) {
        int affectedRows = poorMapper.updateByPrimaryKeySelective(poor);

        ResultVO vo;

        if (affectedRows > 0) {
            //修改完成之后，在重新查询一次，保证返回给前端的是最全的数据
            poor = poorMapper.selectByPrimaryKey(poor.getId());

            vo = new ResultVO(1000,"修改贫困户成功",true,poor);
        } else {
            vo = new ResultVO(5000,"修改贫困户失败",false,null );

        }

        return vo;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = poorMapper.deleteByPrimaryKey(id);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(1000,"删除贫困户成功",true,null);
        } else {
            vo = new ResultVO(5000,"删除贫困户失败",false,null );

        }

        return vo;
    }


}