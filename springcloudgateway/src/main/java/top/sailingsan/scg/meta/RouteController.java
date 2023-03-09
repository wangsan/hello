package top.sailingsan.scg.meta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;

@Controller
public class RouteController {
    @Autowired
    private InMemoryRouteDefinitionRepository routeDefinitionRepository;

    @PostMapping("/route/findOne")
    @ResponseBody
    public Flux<RouteVO> findOne(@RequestBody RouteIdVO routeVO) {
        return routeDefinitionRepository.getRouteDefinitions()
                .filter(rd -> rd.getId().equals(routeVO.getId()))
                .map(RouteVO::toVO);
    }
}
