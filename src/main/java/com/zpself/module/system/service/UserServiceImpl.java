package com.zpself.module.system.service;

import com.github.wenhao.jpa.Specifications;
import com.zpself.jpa.repository.BaseRepository;
import com.zpself.jpa.service.impl.BaseServiceImpl;
import com.zpself.module.system.entity.User;
import com.zpself.module.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {
   private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BaseRepository<User, Long> getCommonRepository() {
        return userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        userRepository.findAll(Specifications.<User>and()
                .like(userName != null && !"".equals(userName), "userName", "%" + userName + "%")
                .build());
        new User().getUserName();
        //userRepository.findByUserName(userName);
        return null;
    }
}
