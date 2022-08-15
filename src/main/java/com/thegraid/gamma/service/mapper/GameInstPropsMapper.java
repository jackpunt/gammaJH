package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.GameInstProps;
import com.thegraid.gamma.service.dto.GameInstPropsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameInstProps} and its DTO {@link GameInstPropsDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameInstPropsMapper extends EntityMapper<GameInstPropsDTO, GameInstProps> {}
