package com.zpself.module.system.entity;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class Person {
  @Id
  private long ssn;
  private String name;
  private int age;
  private Double balance;

}