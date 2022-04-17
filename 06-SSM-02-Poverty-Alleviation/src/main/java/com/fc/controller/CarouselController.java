package com.fc.controller;

import com.fc.entity.Carousel;
import com.fc.service.CarouselService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @GetMapping ("state")
     public ResultVO changeStatus(@RequestParam Integer id) {
        return carouselService.changeStatus(id);
    }

    @GetMapping("getlist")
    public ResultVO getlist(@RequestParam(value = "pageNum") Integer pageNum,
                            @RequestParam(value = "pageSize") Integer pageSize,
                            Integer id) {
        return carouselService.getList(pageNum,pageSize,id);
    }

    @PostMapping("add")
    public ResultVO add(@RequestBody Carousel carousel) {
        return carouselService.add(carousel);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody Carousel carousel) {
        return carouselService.update(carousel);
    }

    @GetMapping("delete")
    public ResultVO delete(@RequestParam Integer id) {
        return carouselService.delete(id);
    }
}
