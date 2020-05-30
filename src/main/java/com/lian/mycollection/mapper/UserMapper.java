package com.lian.mycollection.mapper;

import com.lian.mycollection.common.globalException.util.CopyNonNullUtils;
import com.lian.mycollection.common.constant.MyBusinessExceptionEnum;
import com.lian.mycollection.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/4 18:58
 */
@Repository
public class UserMapper {
    private static Map<Integer,User> userTable = new HashMap<Integer, User>();
     static {
         long now = System.currentTimeMillis();
         userTable.put(1,new User(1,"ted","123456","落葉吹雪",now,"1101"));
         userTable.put(2,new User(2,"kokoa","123456","ココア",now,"1102"));
         userTable.put(3,new User(3,"chihiro","123456","千尋",now,"1103"));
         userTable.put(4,new User(4,"chiya","123456","千夜",now,"1104"));
         userTable.put(5,new User(5,"koyuri","123456","小百合",now,"1105"));
    }

    public  User add(User user){
         user.setCreateTime(System.currentTimeMillis());
        User put = userTable.put(user.getId(), user);
        return put;
    }

    public  User upd(User user){
        User userExist = userTable.get(user.getId());
        MyBusinessExceptionEnum.USER_NOT_FOUND.assertNotNull(userExist);
        CopyNonNullUtils.copyNonNullProperties(user,userExist);
        userExist.setUpdateTime(System.currentTimeMillis());
        User put = userTable.put(userExist.getId(), userExist);
        return put;
    }

    public  User del(Integer id){
        User remove = userTable.remove(id);
        return remove;
    }

    public  User findOneByUserId(Integer id){
        User user = userTable.get(id);
        return user;
    }

    public  List<User> findUserList(){
        List<User> collect = userTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        return collect;
    }

    public List<User> findUserByIdCard(String idCard){
        List<User> collect = userTable.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(collect);
        List<User> existCollect = collect.stream().filter(e -> e.getIdCard().equals(idCard)).collect(Collectors.toList());
        return existCollect;
    }


    public static void main(String[] args) {

    }
}
