package com.corn.springcloud.authcenter.oauth.service.impl;

import com.corn.springcloud.authcenter.oauth.entity.OauthCode;
import com.corn.springcloud.authcenter.oauth.mapper.OauthCodeMapper;
import com.corn.springcloud.authcenter.oauth.service.OauthCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suyiming3333
 * @since 2021-01-20
 */
@Service
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeMapper, OauthCode> implements OauthCodeService {

}
