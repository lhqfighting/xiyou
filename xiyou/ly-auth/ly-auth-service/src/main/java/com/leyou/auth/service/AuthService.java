package com.leyou.auth.service;

import com.leyou.auth.Client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties prop;

    public String login(String username, String password) {
        try {
            //根据用户名和密码查询
            User user = userClient.queryUser(username, password);
            //判断user
            if (user == null){
                return null;
            }
            //jwtUtils生成jwt类型的token
            UserInfo userInfo = new UserInfo();
            user.setId(user.getId());
            user.setPassword(user.getPassword());
            String token = JwtUtils.generateToken(userInfo, prop.getPrivateKey(), prop.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
