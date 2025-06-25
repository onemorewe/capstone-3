package org.yearup.mapper;

import org.mapstruct.Mapper;
import org.yearup.controllers.dto.ProductDto;
import org.yearup.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product entity);
}
