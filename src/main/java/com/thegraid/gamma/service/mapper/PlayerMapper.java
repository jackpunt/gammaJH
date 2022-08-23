package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.Asset;
import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.domain.Player;
import com.thegraid.gamma.domain.User;
import com.thegraid.gamma.service.dto.AssetDTO;
import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.PlayerDTO;
import com.thegraid.gamma.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Player} and its DTO {@link PlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {
    @Mapping(target = "gameClass", source = "gameClass", qualifiedByName = "gameClassId")
    @Mapping(target = "mainJar", source = "mainJar", qualifiedByName = "assetId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    PlayerDTO toDto(Player s);

    @Named("gameClassId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameClassDTO toDtoGameClassId(GameClass gameClass);

    @Named("assetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetDTO toDtoAssetId(Asset asset);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
