package com.zpself.module.system.service;

import com.github.wenhao.jpa.Specifications;
import com.zpself.jpa.repository.BaseRepository;
import com.zpself.jpa.service.impl.BaseServiceImpl;
import com.zpself.module.system.entity.Person;
import com.zpself.module.system.entity.User;
import com.zpself.module.system.repository.UserRepository;
import com.zpself.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
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

    @Override
    public void testEntityManage() {
        //1.em简单查询
        System.out.println("em的简单查询-----------------------------------------------------");
        User user = this.em.find(User.class, 1L);
        System.out.println(user);

        //2.HPQL【JPQL】 query —— createQuery()
        System.out.println("JPQL查询-----------------------------------------------------");
        Query query = em.createQuery("select u from User u where u.id=1L");
        List result = query.getResultList();
        System.out.println(result);

        //3.SQL query —— createNaiveQuery()
        System.out.println("SQL查询-----------------------------------------------------");
        Query query3 = em.createNativeQuery("select * from S_USER u where u.id=1", User.class);
        List result3 = query3.getResultList();
        System.out.println(result3);

        //4.判断实体是否收到em的管理
        System.out.println("em的管理对象判断 contians()-----------------------------------------------------");
        User user2 = this.em.find(User.class, 1L);
        User user1 = new User();
        if(em.contains(user1)){
            System.out.println("此对象正在被管制！");
        }else{
            System.out.println("未被管制！");
        }
        System.out.println(user2);

        //5.em.clear  ：清理前需要flush ,不然改了不会生效
        user2.setPassWord("1");
        em.merge(user2);
        em.clear();


        //获取环境变量
        Environment env = SpringUtil.getBean("environment");
        String property = env.getProperty("spring.thymeleaf.encoding");
        System.out.println(property);
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
