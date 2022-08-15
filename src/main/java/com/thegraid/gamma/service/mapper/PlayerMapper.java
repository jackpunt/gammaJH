package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.Asset;
import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.domain.Player;
import com.thegraid.gamma.service.dto.AssetDTO;
import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Player} and its DTO {@link PlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {
    @Mapping(target = "gameClass", source = "gameClass", qualifiedByName = "gameClassId")
    @Mapping(target = "mainJar", source = "mainJar", qualifiedByName = "assetId")
    PlayerDTO toDto(Player s);

    @Named("gameClassId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameClassDTO toDtoGameClassId(GameClass gameClass);

    @Named("assetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetDTO toDtoAssetId(Asset asset);
}
