package com.naoh.iossupersign.storage.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Mapper
@Repository
public interface IpaPackageMapper {

    Page<IpaPackagePO> selectPage(Page<IpaPackagePO> pageParam, @Param("name")String name);

    IpaPackagePO selectByDownloadId(@Param("ipaDownloadId")String ipaDownloadId);

    int updateById(IpaPackagePO ipaPackagePO);

    int insert(IpaPackagePO ipaPackagePO);

    IpaPackagePO selectById(Long id);
}
