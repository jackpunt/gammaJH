package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.GameInst;
import com.thegraid.gamma.domain.GameInstProps;
import com.thegraid.gamma.service.dto.GameInstDTO;
import com.thegraid.gamma.service.dto.GameInstPropsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameInstProps} and its DTO {@link GameInstPropsDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameInstPropsMapper extends EntityMapper<GameInstPropsDTO, GameInstProps> {
    @Mapping(target = "gameInst", source = "gameInst", qualifiedByName = "gameInstId")
    GameInstPropsDTO toDto(GameInstProps s);

    @Named("gameInstId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameInstDTO toDtoGameInstId(GameInst gameInst);
}
