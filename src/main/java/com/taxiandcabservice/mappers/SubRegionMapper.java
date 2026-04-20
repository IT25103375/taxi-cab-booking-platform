package com.taxiandcabservice.mappers;

import com.taxiandcabservice.dto.SubRegionRequest;
import com.taxiandcabservice.entities.Region;
import com.taxiandcabservice.entities.SubRegion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubRegionMapper {

    @Mapping(source = "request.subRegionName", target = "name")
    @Mapping(source = "request.subRegionDisplayName", target = "displayName")
    @Mapping(source = "region", target = "region")
    SubRegion toSubRegion(SubRegionRequest request, Region region);

    default List<SubRegion> toSubRegionList(List<SubRegionRequest> requestList, Region region) {
        return requestList.stream()
                .map(request -> toSubRegion(request, region))
                .toList();
    }
}
