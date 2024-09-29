package com.acma.properties.service;

public interface GroupService {

    boolean provisionUser(String groupId, String userId, String accessToken);

    boolean deProvisionUser(String groupId, String userId, String accessToken);

}
