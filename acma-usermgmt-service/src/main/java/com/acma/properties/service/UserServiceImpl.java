package com.acma.properties.service;

import com.acma.properties.beans.UserBean;
import com.acma.properties.models.Users;
import com.acma.properties.outbound.AcmaUserOutBoundAPI;
import com.acma.properties.utility.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AcmaUserOutBoundAPI acmaUserOutBoundAPI;

    private final MapperUtils mapperUtils;

    @Override
    public UserBean createUser(UserBean userBean, String accessToken) {
        log.info( "::: Entering createUser method with userBean: {}", userBean );
        Users users = null;
        Users createdUser = null;
        try {
            users = mapperUtils.map( userBean, Users.class );
            createdUser = acmaUserOutBoundAPI.createUser( users, accessToken );
            log.info( "Successfully created user with username: {}", createdUser.getUsername() );
            return mapperUtils.map( createdUser, UserBean.class );
        } catch (Exception e) {
            log.error( "Error occurred while creating user", e );
            throw new RuntimeException( "Failed to create user", e );
        }
    }

    @Override
    public Boolean deleteUser(String userId, String accessToken) {
        log.info( "::: Entering deleteUser method with userId: {}", userId );
        try {
            return acmaUserOutBoundAPI.deleteUserById( userId, accessToken );
        } catch (Exception e) {
            log.error( "Error occurred while deleting user with userId: {}", userId, e );
            throw new RuntimeException( "Failed to delete  user with userId: {}" + userId, e );
        }
    }

    @Override
    public UserBean getUserById(String userId, String accessToken) {
        log.info( "::: Entering getUserById method with userId: {}", userId );
        try {
            Users users = acmaUserOutBoundAPI.getUserById( userId, accessToken );
            if (!ObjectUtils.isEmpty( users )) {
                return mapperUtils.map( users, UserBean.class );
            }
        } catch (Exception e) {
            log.error( "Error occurred while retrieving user with userId: {}", userId, e );
            throw new RuntimeException( "Failed to retrieve  user with userId: {}" + userId, e );
        }
        return null;
    }

    @Override
    public List<UserBean> getAllUsers(String accessToken) {
        log.info( "::: Entering getAllUsers method ::: " );
        List<UserBean> userBeans = null;
        try {
            List<Users> usersList = acmaUserOutBoundAPI.getAllUsers( accessToken );
            if (!CollectionUtils.isEmpty( usersList )) {
                userBeans = usersList.stream().map( users -> mapperUtils.map( users, UserBean.class ) ).collect( Collectors.toList() );
                log.info( "Successfully retrieved {} users", userBeans.size() );
            } else {
                log.warn( "No users found" );
            }
            return userBeans;
        } catch (Exception e) {
            log.error( "Error occurred while retrieving users", e );
            throw new RuntimeException( "Failed to retrieve users", e );
        }
    }

    @Override
    public List<UserBean> getUsersByGroupId(String groupId, String accessToken) {
        log.info( "::: Entering getUsersByGroupId method ::: " );
        List<UserBean> userBeans = null;
        try {
            List<Users> usersList = acmaUserOutBoundAPI.getUsersByGroupId( groupId, accessToken );
            if (!CollectionUtils.isEmpty( usersList )) {
                userBeans = usersList.stream().map( users -> mapperUtils.map( users, UserBean.class ) ).collect( Collectors.toList() );
                log.info( "Successfully retrieved {} users for group id: {}", userBeans.size(), groupId );
            } else {
                log.warn( "No users found with this group id: {}", groupId );
            }
            return userBeans;
        } catch (Exception e) {
            log.error( "Error occurred while retrieving  users for group id: {}. Cause: {}", groupId, e );
            throw new RuntimeException( "Failed to retrieve users for group id: {}" + groupId, e );
        }
    }
}
