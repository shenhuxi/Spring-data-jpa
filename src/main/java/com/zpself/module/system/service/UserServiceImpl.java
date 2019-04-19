package com.zpself.module.system.service;

import com.zpself.jpa.repository.BaseRepository;
import com.zpself.jpa.service.impl.BaseServiceImpl;
import com.zpself.module.system.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {
   /* @Autowired
    UserRepository userRepository;*/

    @Override
    public BaseRepository<User, Long> getCommonRepository() {
        return null;
    }

    @Override
    public User findByUserName(String userName) {
        return null;//userRepository.findByUserName(userName);
    }
}
