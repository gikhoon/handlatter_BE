package handlatter.config;

import handlatter.service.FlaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public FlaskService flaskService() {
        RestClient restClient = RestClient.builder().baseUrl("http://ec2-43-203-5-59.ap-northeast-2.compute.amazonaws.com:5000").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(FlaskService.class);
    }
}
