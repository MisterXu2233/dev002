package com.fc.service;

import com.fc.entity.PoorWithBLOBs;
import com.fc.vo.ResultVO;

public interface PoorService {
    ResultVO getList(Integer pageNum, Integer pageSize, Long id);

    ResultVO add(PoorWithBLOBs poor);

    ResultVO update(PoorWithBLOBs poor);

    ResultVO delete(Long id);
}
