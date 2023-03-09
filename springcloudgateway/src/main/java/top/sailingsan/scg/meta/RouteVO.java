package top.sailingsan.scg.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class RouteVO {
    private String id;
    private String uri;
    private int order;
    private List<PredicateDefinition> predicates;
    private List<FilterDefinition> filters;
    private Map<String, Object> metadata;

    public static RouteDefinition toDef(RouteVO vo) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(vo.getId());
        definition.setFilters(vo.getFilters());
        definition.setMetadata(Optional.ofNullable(vo.getMetadata()).orElse(new HashMap<>()));
        definition.setOrder(vo.getOrder());
        definition.setPredicates(vo.getPredicates());
        definition.setUri(UriComponentsBuilder.fromUriString(vo.getUri()).build().toUri());
        return definition;
    }

    public static RouteVO toVO(RouteDefinition def) {
        RouteVO vo = new RouteVO();
        vo.setId(def.getId());
        vo.setFilters(def.getFilters());
        vo.setMetadata(Optional.ofNullable(def.getMetadata()).orElse(new HashMap<>()));
        vo.setOrder(def.getOrder());
        vo.setPredicates(def.getPredicates());
        vo.setUri(def.getUri().toString());
        return vo;
    }
}
