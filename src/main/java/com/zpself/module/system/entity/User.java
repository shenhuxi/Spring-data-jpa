package com.zpself.module.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zpself.module.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 用户
 * @author shixh
 */
@JsonIgnoreProperties({"lockState","mistakeNums"})
@Entity
@ApiModel
@Table(name="S_USER")
@DynamicInsert	/*插入时只插入非null属性，其他取数据库默认值*/
@DynamicUpdate
public class User extends BaseEntity {
    public static final String INITPASSWORD = "666666";
    private static final long serialVersionUID = -1703630040908311405L;
    @NotNull
    @Column(unique=true)
    @ApiModelProperty("登陆名 4A账号")
    private String userName;

    @ApiModelProperty("密码")
    private String passWord;

    @NotNull
    @ApiModelProperty("用户状态:0-停用,1-启用,2-注销")
    @Column(columnDefinition="int default 0",nullable=false)
    private Integer userState = 1;

    @ApiModelProperty("锁定状态:0-no,1-yes")
    @Transient
    private int lockState;  //0-no,1-yes  redis存储(后期换ＭＱ)

    @ApiModelProperty("密码输入错误次数")
    @Transient
    private int mistakeNums;//密码输入错误次数  redis存储(后期换ＭＱ)

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String heardImg;

    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public int getLockState() {
        return lockState;
    }

    public void setLockState(int lockState) {
        this.lockState = lockState;
    }

    public int getMistakeNums() {
        return mistakeNums;
    }

    public void setMistakeNums(int mistakeNums) {
        this.mistakeNums = mistakeNums;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeardImg() {
        return heardImg;
    }

    public void setHeardImg(String heardImg) {
        this.heardImg = heardImg;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @ApiModelProperty("身份证")
    private String idCard;


}
