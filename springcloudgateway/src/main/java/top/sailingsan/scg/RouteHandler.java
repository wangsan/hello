package top.sailingsan.scg;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class RouteHandler implements ApplicationEventPublisherAware {
    @Autowired
    private InMemoryRouteDefinitionRepository routeDefinitionRepository;
    ApplicationEventPublisher eventPublisher;

    public Mono<ServerResponse> listRoute(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(routeDefinitionRepository.getRouteDefinitions()
                        .map(RouteVO::toVO), RouteVO.class);
    }

    public Mono<ServerResponse> saveRoute(ServerRequest serverRequest) {
        return routeDefinitionRepository.save(serverRequest.bodyToMono(RouteVO.class).map(RouteVO::toDef))
                .then(Mono.fromRunnable(() -> eventPublisher.publishEvent(new RefreshRoutesEvent(this))))
                .then(ok().build())
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> deleteRoute(ServerRequest serverRequest) {
        return routeDefinitionRepository.delete(serverRequest.bodyToMono(RouteIdVO.class).map(RouteIdVO::toString))
                .then(Mono.fromRunnable(() -> eventPublisher.publishEvent(new RefreshRoutesEvent(this))))
                .then(ok().build())
                .switchIfEmpty(notFound().build());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        eventPublisher = applicationEventPublisher;
    }
}
