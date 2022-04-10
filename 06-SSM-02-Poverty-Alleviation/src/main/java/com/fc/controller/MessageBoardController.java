package com.fc.controller;

import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MessageBoardService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("msgboard")
public class MessageBoardController {
    @Autowired
    private MessageBoardService messageBoardService;

    @GetMapping("getlist")
    public ResultVO getlist(@RequestParam(value = "pageNum") Integer pageNum,
                            @RequestParam(value = "pageSize") Integer pageSize,
                            Long id) {
        return messageBoardService.getList(pageNum,pageSize,id);
    }

    @PostMapping("add")
    public ResultVO add(@RequestBody MessageBoardWithBLOBs messageBoard) {
        return messageBoardService.add(messageBoard);
    }

   /* @PostMapping("update")
    public ResultVO update(@RequestBody MessageBoardWithBLOBs messageBoard) {
        return messageBoardService.update(messageBoard);
    }*/

    @GetMapping("delete")
    public ResultVO delete(@RequestParam Long id) {
        return messageBoardService.delete(id);
    }
}
