package com.naoh.iossupersign.storage.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naoh.iossupersign.model.po.DevicePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ami.Tsai
 * @date 2019/12/10
 */
@Mapper
@Repository
public interface DeviceMapper extends BaseMapper<DevicePO> {

    void insertList(List<DevicePO> devicePOS);

    DevicePO getDeviceByUdid(String udid);
}
