package com.library.demo.model.membership_model;

import org.springframework.stereotype.Component;

@Component("PREMIUM")
public class PremiumMembershipPolicy implements MembershipPolicy{

    @Override
    public int maxBorrowedBooks() {
        return 6;
    }

    @Override
    public int loanDurationDays() {
        return 30;
    }
}
