package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.GameInst;
import com.thegraid.gamma.domain.GamePlayer;
import com.thegraid.gamma.domain.Player;
import com.thegraid.gamma.service.dto.GameInstDTO;
import com.thegraid.gamma.service.dto.GamePlayerDTO;
import com.thegraid.gamma.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GamePlayer} and its DTO {@link GamePlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface GamePlayerMapper extends EntityMapper<GamePlayerDTO, GamePlayer> {
    @Mapping(target = "gameInst", source = "gameInst", qualifiedByName = "gameInstId")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerId")
    GamePlayerDTO toDto(GamePlayer s);

    @Named("gameInstId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameInstDTO toDtoGameInstId(GameInst gameInst);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);
}
