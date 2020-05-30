package com.lian.mycollection;

import com.lian.mycollection.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 8:49
 */
@SpringBootTest
public class test {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setObject() {
         redisUtil.setObject("my_second_key",2);
        Object my_second_key = redisUtil.getObject("my_second_key");
        System.out.println(my_second_key);
    }

    @Test
    public void setString() {
        redisUtil.setString("my_string_key",3);
        String my_string_key = redisUtil.getString("my_string_key");
        System.out.println(my_string_key);
    }

    public void setRedisUtil(RedisUtil redisUtil) {

    }
}
