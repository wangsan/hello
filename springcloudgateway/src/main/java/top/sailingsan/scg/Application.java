package top.sailingsan.scg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @see <a href="https://spring.io/guides/gs/gateway/">example</a></a>
 * @author wangsan
 */
@SpringBootApplication
@RestController
@EnableConfigurationProperties(Application.UriConfiguration.class)
public class Application {
	final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpbin();
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.route(p -> p
						.host("*.circuitbreaker.com")
						.filters(f -> f.circuitBreaker(config -> config.setName("mycmd")))
						.uri("http://httpbin.org:80"))
				.route(p -> p
						.host("*.circuitbreaker2.com")
						.filters(f -> f.circuitBreaker(c -> c.setName("mycmd2").setFallbackUri("forward:/fallback")))
						.uri("http://httpbin.org:80"))
				.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}

	@Bean
	public MyGatewayFilterFactory myGatewayFilterFactory() {
		return new MyGatewayFilterFactory();
	}

	@Bean
	public MyGlobalFilter myGlobalFilter() {
		return new MyGlobalFilter();
	}

	@ConfigurationProperties(prefix = "gateway")
	public class UriConfiguration {

		private String httpbin = "http://httpbin.org:80";

		public String getHttpbin() {
			return httpbin;
		}

		public void setHttpbin(String httpbin) {
			this.httpbin = httpbin;
		}
	}

}
