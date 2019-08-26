package com.zpself.module.system.entity;

import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
import com.zpself.module.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="S_EMPLOYEE")
@ApiModel
public class Employee extends BaseEntity {
    private String name;
    private int age;
    @OneToMany
    private List<User> Users;
    // Other code…
}