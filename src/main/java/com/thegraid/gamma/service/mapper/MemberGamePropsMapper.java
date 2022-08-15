package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.GameClass;
import com.thegraid.gamma.domain.MemberGameProps;
import com.thegraid.gamma.domain.User;
import com.thegraid.gamma.service.dto.GameClassDTO;
import com.thegraid.gamma.service.dto.MemberGamePropsDTO;
import com.thegraid.gamma.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberGameProps} and its DTO {@link MemberGamePropsDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberGamePropsMapper extends EntityMapper<MemberGamePropsDTO, MemberGameProps> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "gameClass", source = "gameClass", qualifiedByName = "gameClassId")
    MemberGamePropsDTO toDto(MemberGameProps s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("gameClassId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameClassDTO toDtoGameClassId(GameClass gameClass);
}
