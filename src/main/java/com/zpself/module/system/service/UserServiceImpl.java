package com.zpself.module.system.service;

import com.zpself.jpa.repository.BaseRepository;
import com.zpself.jpa.service.impl.BaseServiceImpl;
import com.zpself.module.system.entity.User;
import com.zpself.module.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {
   @Autowired
   UserRepository userRepository;

    @Override
    public BaseRepository<User, Long> getCommonRepository() {
        return userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        new User().getUserName();
        return null;//userRepository.findByUserName(userName);
    }
}
