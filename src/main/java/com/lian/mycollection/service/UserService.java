package com.lian.mycollection.service;

import com.lian.mycollection.model.User;

import java.util.List;

/**
 *
 */
public interface UserService {
   /**
    * @param id 用户信息主键
    * @return
    */
   User sel(int id);

   /**
    * 获取用户信息列表
    * @return
    */
   List<User> list();

   /**
    * 添加用户
    * @param user
    * @return
    */
   User add(User user);
   /**
    * 更新用户
    * @param user
    * @return
    */
   User upd(User user);

   /**
    * 删除用户
     * @param id
    * @return
    */
   User del(Integer id);
}
