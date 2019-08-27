package com.zpself.module.system.modle;

import com.zpself.module.system.entity.Person;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@javax.persistence.metamodel.StaticMetamodel(Person.class)
public class Person_ {
  public static volatile SingularAttribute< Person,Long> ssn;
  public static volatile SingularAttribute< Person,String> name;
  public static volatile SingularAttribute< Person,Integer> age;
  public static volatile CollectionAttribute< Person, List> address;
}