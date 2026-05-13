package com.taxiandcabservice.mappers;

import com.taxiandcabservice.dto.RegionDTO;
import com.taxiandcabservice.dto.SubRegionCreationDTO;
import com.taxiandcabservice.dto.SubRegionDTO;
import com.taxiandcabservice.entities.Region;
import com.taxiandcabservice.entities.SubRegion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(source = "request.subRegionName", target = "name")
    @Mapping(source = "request.subRegionDisplayName", target = "displayName")
    @Mapping(source = "region", target = "region")
    SubRegion toSubRegion(SubRegionCreationDTO request, Region region);

    @Mapping(source = "id", target = "subRegionId")
    @Mapping(source = "name", target = "subRegionName")
    @Mapping(source = "displayName", target = "subRegionDisplayName")
    SubRegionDTO toSubRegionDTO(SubRegion subRegion);

    @Mapping(source = "id", target = "regionId")
    @Mapping(source = "name", target = "regionName")
    @Mapping(source = "displayName", target = "regionDisplayName")
    RegionDTO toRegionDTO(Region region);

    default List<SubRegion> toSubRegionList(List<SubRegionCreationDTO> requestList, Region region) {
        return requestList.stream()
                .map(request -> toSubRegion(request, region))
                .toList();
    }

    List<SubRegionDTO> toSubRegionDTOList(Iterable<SubRegion> subRegionList);

    List<RegionDTO> toRegionDTOList(Iterable<Region> regionList);
}
