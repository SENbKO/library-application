package com.library.demo.model.membership_model;

import org.springframework.stereotype.Component;

@Component("BASIC")
public class BasicMembershipPolicy implements MembershipPolicy{
    @Override
    public int maxBorrowedBooks() {
        return 3;
    }

    @Override
    public int loanDurationDays() {
        return 14;
    }
}
