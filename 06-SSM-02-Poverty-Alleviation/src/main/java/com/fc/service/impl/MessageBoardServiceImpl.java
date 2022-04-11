package com.fc.service.impl;

import com.fc.dao.MessageBoardMapper;
import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MessageBoardService;
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
public class MessageBoardServiceImpl implements MessageBoardService {
    @Autowired
    private MessageBoardMapper messageBoardMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Long id) {
        //返回给前端的结果
        ResultVO resultVO;

        // 分页相关的参数
        DateVO<MessageBoardWithBLOBs> userDateVO;

        //结果中data对应的用户数组
        List<MessageBoardWithBLOBs> messageBoard;

        //说明传递了id,那就是findById
        if (id != null) {
            messageBoard = new ArrayList<>();

            //查询
            MessageBoardWithBLOBs messageBoards = messageBoardMapper.selectByPrimaryKey(id);

            //没有查到用户的情况
            if(messageBoard == null) {
                userDateVO = new DateVO<>(0L,messageBoard,pageNum,pageSize);

                resultVO = new ResultVO(4000,"查询无此留言",false,userDateVO);

            } else {
                //查到了用户扔到集合中
                messageBoard.add(messageBoards);

                userDateVO = new DateVO<>(1L,messageBoard,pageNum,pageSize);

                resultVO = new ResultVO(1000,"查到了该用户",true,userDateVO);

            }
        } else {
            //开启分页
            PageHelper.startPage(pageNum,pageSize);

            messageBoard = messageBoardMapper.selectByExampleWithBLOBs(null);

            //如果数据库是空的，一个人都没查到
            if (messageBoard.size() == 0) {
                userDateVO = new DateVO<>(0L,messageBoard,pageNum,pageSize);

                resultVO = new ResultVO(4100,"没有用户",false,userDateVO);

                //查到了
            } else {

                //封装pageInfo,为了获取总数据量
                PageInfo<MessageBoardWithBLOBs> pageInfo = new PageInfo<>(messageBoard);

                userDateVO = new DateVO<>(pageInfo.getTotal(),messageBoard,pageNum,pageSize);

                resultVO = new ResultVO(1100,"用户查询成功",true,userDateVO);

            }

        }

        return resultVO;
    }

    @Override
    public ResultVO add(MessageBoardWithBLOBs messageBoard) {
        ResultVO vo;

        //判断是否存在创建时间，没有就自己加上
        if (messageBoard.getCreateTime() == null) {
            messageBoard.setCreateTime(new Date());
        }

        int affectedRows = messageBoardMapper.insertSelective(messageBoard);

        if (affectedRows > 0) {
            vo = new ResultVO(1000,"添加留言成功",true,messageBoard);
        } else {
            vo = new ResultVO(5000,"添加留言失败",false,null);

        }

        return vo;
    }

    /*@Override
    public ResultVO update(MessageBoardWithBLOBs messageBoard) {
        int affectedRows = messageBoardMapper.updateByPrimaryKeySelective(messageBoard);

        ResultVO vo;

        if (affectedRows > 0) {
            //修改完成之后，在重新查询一次，保证返回给前端的是最全的数据
            messageBoard = messageBoardMapper.selectByPrimaryKey(messageBoard.getId());

            vo = new ResultVO(1000,"修改留言成功",true,messageBoard);
        } else {
            vo = new ResultVO(5000,"修改留言失败",false,null );

        }

        return vo;
    }*/

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = messageBoardMapper.deleteByPrimaryKey(id);

        ResultVO vo;

        if (affectedRows > 0) {

            vo = new ResultVO(1000,"删除留言成功",true,null);
        } else {
            vo = new ResultVO(5000,"删除留言失败",false,null );

        }

        return vo;
    }
}
