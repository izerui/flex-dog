package com.github.izerui.file;

import com.ecworking.jpa.factory.PlatformJpaRepositoryFactoryBean;
import com.ecworking.jpa.impl.PlatformRepositoryImpl;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * Created by serv on 2017/3/10.
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.github.izerui.file.client"})
@EnableEurekaClient
@EnableAsync
@EnableJpaRepositories(repositoryFactoryBeanClass = PlatformJpaRepositoryFactoryBean.class)
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = {MchuanSmsConfiguration.class, BaseAutoConfiguration.class, SleuthStreamAutoConfiguration.class, SwaggerConfiguration.class})
public class Application {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
