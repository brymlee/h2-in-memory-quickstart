package brymlee

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CustomerDatabaseTestContext{

    @Bean
    open fun repository() = Repository()
}
