package com.corn.springcloud.start.share.mapper;

import com.corn.springcloud.start.share.entity.Share;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
public interface ShareMapper extends BaseMapper<Share> {
    List<Share> querySharesByPage(@Param("title") String title);
}
