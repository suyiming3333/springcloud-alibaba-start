package com.corn.springcloud.start.share.service.impl;

import com.corn.springcloud.start.share.entity.Share;
import com.corn.springcloud.start.share.mapper.ShareMapper;
import com.corn.springcloud.start.share.service.ShareService;
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
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {

}
