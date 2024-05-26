package org.nghia.identityservice.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.nghia.identityservice.dto.UserLoginPrinciple;
import org.nghia.identityservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperService {

    private final ModelMapper modelMapper;

    public ModelMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserLoginPrinciple mapUserToUserLoginPrinciple(User user) {
        TypeMap<User, UserLoginPrinciple> typeMap = modelMapper.createTypeMap(User.class, UserLoginPrinciple.class);
        typeMap.addMappings(
                mapper -> mapper.map(User::getAuthorities, UserLoginPrinciple::setRoles)
        );
        return modelMapper.map(user, UserLoginPrinciple.class);
    }
}
