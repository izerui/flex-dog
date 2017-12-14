package com.github.izerui.file

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Created by serv on 2017/3/10.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = arrayOf("com.github.izerui.file.repository"))
open class Application{

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}