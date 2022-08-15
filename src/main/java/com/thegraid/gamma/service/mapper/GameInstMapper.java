package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.domain.GameInst;
import com.thegraid.gamma.domain.GameInstProps;
import com.thegraid.gamma.domain.Player;
import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.GameInstDTO;
import com.thegraid.gamma.service.dto.GameInstPropsDTO;
import com.thegraid.gamma.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameInst} and its DTO {@link GameInstDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameInstMapper extends EntityMapper<GameInstDTO, GameInst> {
    @Mapping(target = "props", source = "props", qualifiedByName = "gameInstPropsId")
    @Mapping(target = "gameClass", source = "gameClass", qualifiedByName = "gameClassId")
    @Mapping(target = "playerA", source = "playerA", qualifiedByName = "playerId")
    @Mapping(target = "playerB", source = "playerB", qualifiedByName = "playerId")
    GameInstDTO toDto(GameInst s);

    @Named("gameInstPropsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameInstPropsDTO toDtoGameInstPropsId(GameInstProps gameInstProps);

    @Named("gameClassId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameClassDTO toDtoGameClassId(GameClass gameClass);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);
}
