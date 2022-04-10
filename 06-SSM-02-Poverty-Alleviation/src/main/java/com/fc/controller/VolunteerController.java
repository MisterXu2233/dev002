package com.fc.controller;

import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @PostMapping("click")
    public ResultVO click(VolunteerRecruitment volunteer) {
        return volunteerService.click(volunteer.getId(),volunteer.getLastClickTime());
    }

    @GetMapping("getlist")
    public ResultVO getlist(@RequestParam(value = "pageNum") Integer pageNum,
                            @RequestParam(value = "pageSize") Integer pageSize,
                            Long id) {
        return volunteerService.getList(pageNum,pageSize,id);
    }

    @PostMapping("add")
    public ResultVO add(@RequestBody VolunteerRecruitment volunteer) {
        return volunteerService.add(volunteer);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody VolunteerRecruitment volunteer) {
        return volunteerService.update(volunteer);
    }

    @GetMapping("delete")
    public ResultVO delete(@RequestParam Long id) {
        return volunteerService.delete(id);
    }

}
