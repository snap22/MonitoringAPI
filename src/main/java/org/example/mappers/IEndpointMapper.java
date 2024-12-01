package org.example.mappers;

import org.example.dto.EndpointRequest;
import org.example.dto.EndpointResponse;
import org.example.dto.MonitoringResultResponse;
import org.example.entities.EndpointEntity;
import org.example.entities.MonitoringResultEntity;
import org.mapstruct.*;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.time.LocalDateTime;

/**
 * Mapper for converting between endpoint related DTOs and entities.
 */
@Mapper(componentModel = "spring", uses = {
        IUserMapper.class
})
public interface IEndpointMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "url", target = "url"),
            @Mapping(source = "checkInterval", target = "interval"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "lastCheckedAt", target = "lastCheckedAt"),
            @Mapping(source = "user", target = "owner"),
    })
    EndpointResponse toResponse(EndpointEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastCheckedAt", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "url", target = "url"),
            @Mapping(source = "interval", target = "checkInterval"),
    })
    EndpointEntity toEntity(EndpointRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastCheckedAt", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "url", target = "url"),
            @Mapping(source = "interval", target = "checkInterval"),
    })
    void updateFromRequest(EndpointRequest request, @MappingTarget EndpointEntity entity);
}
