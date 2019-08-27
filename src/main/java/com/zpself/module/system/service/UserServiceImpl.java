package com.zpself.module.system.service;

import com.github.wenhao.jpa.Specifications;
import com.zpself.jpa.repository.BaseRepository;
import com.zpself.jpa.service.impl.BaseServiceImpl;
import com.zpself.module.system.entity.Person;
import com.zpself.module.system.entity.User;
import com.zpself.module.system.repository.UserRepository;
import com.zpself.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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

        EntityManagerFactory emf = SpringUtil.getBean("EntityManagerFactory");
        Metamodel metamodel = emf.getMetamodel();
        EntityType<Person> pClass = metamodel.entity(Person.class);
        return null;
    }
    public  void  findAll(){
        EntityManager em = SpringUtil.getBean("EntityManager");
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<Person> r = cq.from(Person.class);

        Path<Double> balance = r.get("balance");
        Predicate and = cb.and(cb.greaterThan(balance, 100D), cb.lessThan(balance, 200D));
        cq.where(and);
        cq.select(cb.abs(r.get("balance")));
    }
}
