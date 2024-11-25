package org.example.mappers;

import org.example.dto.MonitoringResultResponse;
import org.example.entities.EndpointEntity;
import org.example.entities.MonitoringResultEntity;
import org.mapstruct.*;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface IMonitoringResultMapper {
    @Mappings({
        @Mapping(target = "statusCode", expression = "java(String.valueOf(clientResponse.statusCode()))"),
    })
    MonitoringResultEntity toResultEntity(ClientResponse clientResponse, @Context String body, @Context EndpointEntity endpoint);

    @AfterMapping
    default void afterMapping(@MappingTarget MonitoringResultEntity entity, ClientResponse clientResponse, @Context String body, @Context EndpointEntity endpoint) {
        entity.setPayload(body);
        entity.setEndpoint(endpoint);
        entity.setCheckedAt(LocalDateTime.now());
    }

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "statusCode", target = "statusCode"),
            @Mapping(source = "payload", target = "payload"),
            @Mapping(source = "checkedAt", target = "checkedAt"),
    })
    MonitoringResultResponse toResponse(MonitoringResultEntity monitoringResultEntity);
}
