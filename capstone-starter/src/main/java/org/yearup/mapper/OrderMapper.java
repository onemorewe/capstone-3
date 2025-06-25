package org.yearup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.yearup.models.Order;
import org.yearup.models.Profile;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "shippingAmount", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateOrderAddressFromProfile(@MappingTarget Order order, Profile profile);
}
