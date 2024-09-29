package com.acma.properties.service;

import com.acma.properties.beans.UserBean;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {

    UserBean createUser(UserBean userBeanm,String accessToken);
    Boolean deleteUser(String userId,String accessToken);
    UserBean getUserById(String userId,String accessToken);
    List<UserBean> getAllUsers(String accessToken);
    List<UserBean> getUsersByGroupId(String groupId,String accessToken);

}
