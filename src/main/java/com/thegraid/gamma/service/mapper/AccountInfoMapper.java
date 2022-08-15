package com.thegraid.gamma.service.mapper;

import com.thegraid.gamma.domain.AccountInfo;
import com.thegraid.gamma.domain.User;
import com.thegraid.gamma.service.dto.AccountInfoDTO;
import com.thegraid.gamma.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountInfo} and its DTO {@link AccountInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountInfoMapper extends EntityMapper<AccountInfoDTO, AccountInfo> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    AccountInfoDTO toDto(AccountInfo s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
