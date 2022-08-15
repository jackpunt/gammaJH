package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.Asset;
import com.thegraid.gamma.domain.User;
import com.thegraid.gamma.service.dto.AssetDTO;
import com.thegraid.gamma.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asset} and its DTO {@link AssetDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetMapper extends EntityMapper<AssetDTO, Asset> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    AssetDTO toDto(Asset s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
