package com.corn.springcloud.authcenter.oauth.service.impl;

import com.corn.springcloud.authcenter.oauth.entity.OauthClientDetails;
import com.corn.springcloud.authcenter.oauth.mapper.OauthClientDetailsMapper;
import com.corn.springcloud.authcenter.oauth.service.OauthClientDetailsService;
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
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

}
