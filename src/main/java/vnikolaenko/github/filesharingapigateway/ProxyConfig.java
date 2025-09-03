package vnikolaenko.github.filesharingapigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProxyConfig {

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth",
                        r -> r.path("/auth/**")
                                .filters(f -> f
                                        .preserveHostHeader()
                                )
                                .uri("http://localhost:8081"))

                .route("user_manipulation",
                        r -> r.path("/user/**")
                                .filters(f -> f
                                        .preserveHostHeader()
                                )
                                .uri("http://localhost:8081"))

                .route("file_manipulation",
                        r -> r.path("/files/**")
                                .filters(GatewayFilterSpec::preserveHostHeader
                                )
                                .uri("http://localhost:8082"))
                .build();
    }
}