package com.lian.mycollection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:liantengda
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String userName;
    private String passWord;
    private String realName;
    private Long updateTime;
    private Long createTime;
    private String idCard;
    public User(Integer id,String userName,String passWord,String realName,Long createTime,String idCard){
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
        this.createTime = createTime;
        this.idCard = idCard;
    }
}