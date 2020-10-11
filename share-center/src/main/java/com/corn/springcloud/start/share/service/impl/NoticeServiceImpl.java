package com.corn.springcloud.start.share.service.impl;

import com.corn.springcloud.start.share.entity.Notice;
import com.corn.springcloud.start.share.mapper.NoticeMapper;
import com.corn.springcloud.start.share.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2020-10-11
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
