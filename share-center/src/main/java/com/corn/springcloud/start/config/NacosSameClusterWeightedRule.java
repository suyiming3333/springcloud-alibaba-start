package com.corn.springcloud.start.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义路由规则，根据cluster有限获取调用的服务地址
 */
@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @SneakyThrows
    @Override
    public Server choose(Object o) {
        try {
            //拿到配置文件中的集群名称
            String clusterName = discoveryProperties.getClusterName();
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //想要请求的微服务的名称
            String name = loadBalancer.getName();
            //拿到服务发现的相关api
            NamingService namingService = nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());

            //1. 找到指定服务的所有实例 A
            List<Instance> instances = namingService.selectInstances(name, true);

            //2. 过滤出相同集群下的所有实例  B
            List<Instance> sameClusterInstances = instances.stream()
                    .filter(instance -> Objects.equals(instance.getClusterName(), clusterName))
                    .collect(Collectors.toList());

            //3. 如果B是空,就用A
            List<Instance> instancesToBeChosen = new ArrayList<>();
            if (CollectionUtils.isEmpty(sameClusterInstances)) {
                instancesToBeChosen = instances;
                log.warn("发生跨集群的调用,name = {},clusterName =  {},instences = {}",name,clusterName,instances);
            } else {
                instancesToBeChosen = sameClusterInstances;
            }
            //4. 基于nacos权重的负载均衡算法,返回一个实例
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToBeChosen);
            log.info("选择的实例是: port = {},instance = {}",instance.getPort(),instance);

            return new NacosServer(instance);
        } catch (NacosException e) {
            log.error("发生异常了: {}",e);
            return null;
        }
    }
}

/**
 * 因为getHostByRandomWeight()方法为protected,可以这样 包装调用
 */
class ExtendBalancer extends Balancer {
    /**
     * 根据权重，随机选择实例
     *
     * @param hosts 实例列表
     * @return 选择的实例
     */
    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}
