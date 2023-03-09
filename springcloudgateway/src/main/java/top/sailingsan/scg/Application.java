package top.sailingsan.scg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import top.sailingsan.scg.meta.RouteHandler;

/**
 * @author wangsan
 * @see <a href="https://spring.io/guides/gs/gateway/">example</a></a>
 */
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(config -> config.setName("mycmd")))
                        .uri("http://httpbin.org:80"))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> myRouterFunction(RouteHandler routeHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/route/list"), routeHandler::listRoute)
                .andRoute(RequestPredicates.POST("/route/save"), routeHandler::saveRoute)
                .andRoute(RequestPredicates.POST("/route/delete"), routeHandler::deleteRoute);
    }

}
