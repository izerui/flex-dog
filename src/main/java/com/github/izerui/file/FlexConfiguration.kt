package com.github.izerui.file

import flex.messaging.HttpFlexSession
import flex.messaging.MessageBroker
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.flex.config.BeanIds
import org.springframework.flex.config.MessageBrokerConfigProcessor
import org.springframework.flex.config.RemotingAnnotationPostProcessor
import org.springframework.flex.core.MessageBrokerFactoryBean
import org.springframework.flex.servlet.MessageBrokerHandlerAdapter
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
import java.util.*

/**
 * Created by serv on 2015/1/24.
 */
@Configuration
@ConditionalOnWebApplication
open class FlexConfiguration {

    @Bean
    open fun flexSession(): ServletListenerRegistrationBean<HttpFlexSession> {
        return ServletListenerRegistrationBean(HttpFlexSession())
    }

    /**
     * 1. Parsing the BlazeDS XML configuration files and applying their settings to a newly created MessageBroker
     * 2. Starting the MessageBroker and its services
     * @return
     */
    @Bean(name = arrayOf(BeanIds.MESSAGE_BROKER))
    open fun messageBrokerFactoryBean(applicationContext: ApplicationContext): MessageBrokerFactoryBean {
        val messageBrokerFactoryBean = MessageBrokerFactoryBean()
        messageBrokerFactoryBean.setServicesConfigPath("classpath:flex/services-config.xml")

        // http://docs.spring.io/spring-flex/docs/1.6.x/reference/html/#security-filter-config
        val beansOfType = applicationContext.getBeansOfType<MessageBrokerConfigProcessor>(MessageBrokerConfigProcessor::class.java)
        if (beansOfType != null && beansOfType.size > 0) {
            messageBrokerFactoryBean.configProcessors = HashSet(beansOfType.values)
        }

        return messageBrokerFactoryBean
    }

    /**
     * [org.springframework.web.servlet.HandlerAdapter] for routing HTTP messages to a Spring-managed [MessageBroker].
     */
    @Bean(name = arrayOf(BeanIds.MESSAGE_BROKER_HANDLER_ADAPTER))
    open fun messageBrokerHandlerAdapter(): MessageBrokerHandlerAdapter {
        return MessageBrokerHandlerAdapter()
    }

    //Maps request paths at /messagebroker/* to the BlazeDS MessageBroker
    @Bean(name = arrayOf(BeanIds.HANDLER_MAPPING_SUFFIX))
    open fun messagebrokerHandlerMapping(messageBroker: MessageBroker): SimpleUrlHandlerMapping {
        val mapping = SimpleUrlHandlerMapping()
        mapping.setOrder(Integer.MIN_VALUE)
        mapping.setUrlMap(Collections.singletonMap<String, MessageBroker>("/messagebroker/*", messageBroker))
        return mapping
    }

    @Bean(name = arrayOf(BeanIds.REMOTING_ANNOTATION_PROCESSOR))
    open fun remotingAnnotationPostProcessor(): RemotingAnnotationPostProcessor {
        return RemotingAnnotationPostProcessor()
    }

}
