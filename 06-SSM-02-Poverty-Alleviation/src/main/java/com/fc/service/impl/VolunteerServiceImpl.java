package com.fc.service.impl;

import com.fc.dao.VolunteerRecruitmentMapper;
import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
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
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerRecruitmentMapper volunteerMapper;


    @Override
    //需要在Mapper中声明一下
    public ResultVO click(Long id, Date lastClickTime) {
        if (lastClickTime == null) {
            lastClickTime = new Date();
        }

        Integer affectedRows = volunteerMapper.click(id,lastClickTime);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(200,"招聘扶贫志愿者浏览量加1成功",true,null);
        } else {
            vo = new ResultVO(5000,"招聘扶贫志愿者浏览量加1失败",false,null );

        }

        return vo;
    }

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Long id) {
        //返回给前端的结果
        ResultVO resultVO;

        // 分页相关的参数
        DateVO<VolunteerRecruitment> dateVO;

        //结果中data对应的用户数组
        List<VolunteerRecruitment> volunteers;

        //说明传递了id,那就是findById
        if (id != null) {
            volunteers = new ArrayList<>();

            //查询
            VolunteerRecruitment volunteer = volunteerMapper.selectByPrimaryKey(id);

            //没有查到用户的情况
            if(volunteer == null) {
                dateVO = new DateVO<>(0L,volunteers,pageNum,pageSize);

                resultVO = new ResultVO(4000,"未查到这条志愿者招聘信息",false,dateVO);

            } else {
                //如果查询单个，那么点击量应该加1
                click(volunteer.getId(), null);

                //更新点击的次数
                volunteer.setClickNum(volunteer.getClickNum() + 1);

                //查到了用户扔到集合中
                volunteers.add(volunteer);

                dateVO = new DateVO<>(1L,volunteers,pageNum,pageSize);

                resultVO = new ResultVO(200,"查到了该招聘信息",true,dateVO);

            }
        } else {
            //开启分页
            PageHelper.startPage(pageNum,pageSize);

            volunteers = volunteerMapper.selectByExampleWithBLOBs(null);

            //如果数据库是空的，一个人都没查到
            if (volunteers.size() == 0) {
                dateVO = new DateVO<>(0L,volunteers,pageNum,pageSize);

                resultVO = new ResultVO(4100,"没有招聘信息",false,dateVO);

                //查到了
            } else {

                //封装pageInfo,为了获取总数据量
                PageInfo<VolunteerRecruitment> pageInfo = new PageInfo<>(volunteers);

                dateVO = new DateVO<>(pageInfo.getTotal(),volunteers,pageNum,pageSize);

                resultVO = new ResultVO(200,"志愿者招聘信息查询成功",true,dateVO);

            }

        }

        return resultVO;
    }

    @Override
    public ResultVO add(VolunteerRecruitment volunteer) {
        ResultVO vo;

        //判断是否存在创建时间，没有就自己加上
        if (volunteer.getCreateTime() == null) {
            volunteer.setCreateTime(new Date());
        }

        //新创建的扶贫政策，点击两应该是0
        volunteer.setClickNum(0);
        volunteer.setLastClickTime(null);

        int affectedRows = volunteerMapper.insertSelective(volunteer);

        if (affectedRows > 0) {
            vo = new ResultVO(1000,"添加志愿者招聘信息成功",true,volunteer);
        } else {
            vo = new ResultVO(5000,"添加志愿者招聘信息失败",false,null);

        }

        return vo;
    }

    @Override
    public ResultVO update(VolunteerRecruitment volunteer) {
        int affectedRows = volunteerMapper.updateByPrimaryKeySelective(volunteer);

        ResultVO vo;

        if (affectedRows > 0) {
            //修改完成之后，在重新查询一次，保证返回给前端的是最全的数据
            volunteer = volunteerMapper.selectByPrimaryKey(volunteer.getId());

            vo = new ResultVO(200,"修改志愿者招聘信息成功",true,volunteer);
        } else {
            vo = new ResultVO(5000,"修改志愿者招聘信息失败",false,null );

        }

        return vo;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows =volunteerMapper.deleteByPrimaryKey(id);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(200,"删除志愿者招聘信息成功",true,null);
        } else {
            vo = new ResultVO(5000,"删除志愿者招聘信息失败",false,null );

        }

        return vo;
    }
}
