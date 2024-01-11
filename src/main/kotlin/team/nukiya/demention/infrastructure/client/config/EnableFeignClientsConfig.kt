package team.nukiya.demention.infrastructure.client.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["team.nukiya.demention.infrastructure.client"])
class EnableFeignClientsConfig