package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.service.dto.GameClassDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameClass} and its DTO {@link GameClassDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameClassMapper extends EntityMapper<GameClassDTO, GameClass> {}
