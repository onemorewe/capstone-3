package org.yearup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.yearup.controllers.dto.ProfileDto;
import org.yearup.models.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateProfileFromDto(ProfileDto profileDto, @MappingTarget Profile profile);

    ProfileDto toDto(Profile profile);
}
