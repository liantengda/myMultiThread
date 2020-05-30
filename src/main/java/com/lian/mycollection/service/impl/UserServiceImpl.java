package com.lian.mycollection.service.impl;


import com.lian.mycollection.common.constant.MyBusinessExceptionEnum;
import com.lian.mycollection.mapper.UserMapper;
import com.lian.mycollection.model.User;
import com.lian.mycollection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User sel(int id){
        User user = userMapper.findOneByUserId(id);
        MyBusinessExceptionEnum.USER_NOT_FOUND.assertNotNull(user);
        return user;
    }

    @Override
    public List<User> list() {
        List<User> userList = userMapper.findUserList();
        return userList;
    }

    @Override
    public User add(User user) {
        List<User> existCollect = userMapper.findUserByIdCard(user.getIdCard());
        MyBusinessExceptionEnum.USER_ALREADY_EXIST.assertEquals(existCollect.size(),0);
        User add = userMapper.add(user);
        return add;
    }

    @Override
    public User upd(User user) {
        User upd = userMapper.upd(user);
        return upd;
    }

    @Override
    public User del(Integer id) {
        User user = userMapper.findOneByUserId(id);
        MyBusinessExceptionEnum.USER_ALREADY_DELETED.assertNotNull(user);
        User del = userMapper.del(id);
        return del;
    }

}
