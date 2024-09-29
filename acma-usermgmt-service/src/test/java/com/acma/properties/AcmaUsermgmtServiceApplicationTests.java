package com.acma.properties;

import com.acma.properties.models.Users;
import com.acma.properties.outbound.AcmaUserOutBoundAPI;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AcmaUsermgmtServiceApplicationTests {

    static Users newUser = null;
    static String accessToken = null;
    static String propertyOwnerGroupId = null;
    static String brokersGroupId = null;
    static String agentsGroupId = null;


    @Autowired
    private AcmaUserOutBoundAPI usersAPI;


    @Test
    void contextLoads() {
    }

    @BeforeAll
    public static void init() {
        newUser = Users.builder()
                .firstName( "Omprakash" )
                .lastName( "Ornold" )
                .email( "OmprakashOrnold2025@yopmail.com" )
                .username( "OmprakashOrnold2025" )
                .build();
        accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWTHpMZXM5bW1FQ1hDeXU5cVlKcFRGa3NJVkNiMWhoeXFuYmRZZ25OeklvIn0.eyJleHAiOjE3Mjc1Mjk0NjAsImlhdCI6MTcyNzUyNzk2MCwianRpIjoiYTA5ZTM2NDMtZGZlZC00Y2FkLWE0OGItZDUwODFhNzM0MDI4IiwiaXNzIjoiaHR0cDovL2FtLmFjbWEuY29tOjgwODEvcmVhbG1zL2FjbWEiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImFjY291bnQiXSwic3ViIjoiMzA2NGY3ODQtNjgyNi00MjE1LTkwZTEtNTM3NzNmY2VkYWIwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3RhdGVfZm9ybV9zdmMiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIi8qIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWFjbWEiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtdXNlcnMiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImNsaWVudEhvc3QiOiIxNzIuMTguMC4xIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtc3RhdGVfZm9ybV9zdmMiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjE4LjAuMSIsImNsaWVudF9pZCI6InN0YXRlX2Zvcm1fc3ZjIn0.Gf9NgcwuyWCt5lAI7-zI7wFRqCjK7qyBpJHP50neoJ4Jb_naFIS9su72Yt6gaJssNxc5XxrrkvdFjPS6tMFGRXgrXjwnDagUChCH4N2rjjy7hgfMc6HzlmNkmqk6UYTwBpPuMi5_IxAmn-LD3aObT5ub6BtlarqrzRKCU10-KQjHynBYerrF0FK_OUXZdUiYAUhUDkqF7ArGCKTZoZB7XyITqY6ouDEkGK3qfy7sE3s-9RSJULY-ZmIgggC5K_uoQbnDb_kkzFBjEQUJKZFFbqUbK2wm-R64_kKc8gk3igpVNkq2NJGIJPLGX2t1ZiSTgyZPP273OzYaO_TT-Td_XA";
        propertyOwnerGroupId = "d8246eb5-4361-4ef4-9a8b-733cff85ac34";
        brokersGroupId = "54be4bfb-ef71-4df5-b61d-4de6756dc4f0";
        agentsGroupId = "52cbf801-4214-47cb-a747-b2c3eca8e740";
    }

    @Test
    @Order(value = 1)
    public void testCreatePropertyOwners() {
        String username = newUser.getUsername() + DateUtil.now().getTime();
        String email = newUser.getEmail() + DateUtil.now().getTime();
        newUser.setUsername( username );
        newUser.setEmail( email );
        newUser = usersAPI.createUser( newUser, accessToken );
        assertThat( newUser );
        assertThat( newUser.getGroupId() );
        assertThat( newUser.getUserId() );
    }

    @Test
    @Order(value = 2)
    public void testCreateBrokers() {
        String username = newUser.getUsername() + DateUtil.now().getTime();
        String email = newUser.getEmail() + DateUtil.now().getTime();
        newUser.setGroupId( brokersGroupId );
        newUser.setUsername( username );
        newUser.setEmail( email );
        newUser = usersAPI.createUser( newUser, accessToken );
        assertThat( newUser );
        assertThat( newUser.getGroupId() );
        assertThat( newUser.getUserId() );
    }

    @Test
    @Order(value = 3)
    public void testCreateAgents() {
        String username = newUser.getUsername() + DateUtil.now().getTime();
        String email = newUser.getEmail() + DateUtil.now().getTime();
        newUser.setGroupId( agentsGroupId );
        newUser.setUsername( username );
        newUser.setEmail( email );
        newUser = usersAPI.createUser( newUser, accessToken );
        assertThat( newUser );
        assertThat( newUser.getGroupId() );
        assertThat( newUser.getUserId() );
    }

    @Test
    @Order(value = 4)
    public void testGetAllUsers() {
        List<Users> usersList = usersAPI.getAllUsers( accessToken );
        Assertions.assertTrue( usersList.size() > 0 );
    }

    @Test
    @Order(value = 5)
    public void testGetAllPropertyOwners() {
        List<Users> propertyOwnersList= usersAPI.getUsersByGroupId( propertyOwnerGroupId, accessToken );
        Assertions.assertTrue( propertyOwnersList.size() > 0 );
    }

    @Test
    @Order(value = 6)
    public void testGetAllBrokers() {
        List<Users> brokersList =  usersAPI.getUsersByGroupId( brokersGroupId, accessToken );
        Assertions.assertTrue( brokersList.size() > 0 );
    }

    @Test
    @Order(value = 7)
    public void testGetAllAgents() {
        List<Users> agentsList = usersAPI.getUsersByGroupId( agentsGroupId, accessToken );
        Assertions.assertTrue( agentsList.size() > 0 );
    }


}
