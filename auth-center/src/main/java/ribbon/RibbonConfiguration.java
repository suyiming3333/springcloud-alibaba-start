package ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;

public class RibbonConfiguration {

    @Bean(name = "myRule")
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
