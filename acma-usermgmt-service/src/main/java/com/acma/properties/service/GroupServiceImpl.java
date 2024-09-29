package com.acma.properties.service;

import com.acma.properties.outbound.AcmaUserOutBoundAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-28
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements  GroupService{

    private  final AcmaUserOutBoundAPI acmaUserOutBoundAPI;

    @Override
    public boolean provisionUser(String groupId, String userId, String accessToken) {
        return acmaUserOutBoundAPI.provisionUserUnderAGroup( groupId, userId, accessToken );
    }

    @Override
    public boolean deProvisionUser(String groupId, String userId, String accessToken) {
        return acmaUserOutBoundAPI.deProvisionUserUnderAGroup( groupId, userId, accessToken );
    }
}
