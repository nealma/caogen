package com.caogen;

import com.caogen.dao.SysUserMapper;
import com.caogen.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;

/**
 * Created by neal on 11/6/16.
 */
@SpringBootConfiguration
public class MyBatisTest {

    @Autowired
    private static SysUserMapper sysUserMapper;

    public static void findUserTest(){
        SysUser user = sysUserMapper.selectByPK(1L);

        if(user != null)
        System.out.println(user.getUsername());
    }

    public static void main(String[] args) {
        findUserTest();
    }
}
