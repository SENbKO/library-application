package com.library.demo.service;

import com.library.demo.model.membership_model.MembershipPolicy;
import com.library.demo.model.membership_model.MembershipType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MembershipPolicyResolver {
    private final Map<String, MembershipPolicy> policies;

    public MembershipPolicyResolver(Map<String, MembershipPolicy> policies) {
        this.policies = policies;
    }

    public MembershipPolicy resolve(MembershipType type){
        return policies.get(type.name());
    }
}
