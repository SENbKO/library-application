package com.library.demo.model.membership_model;

public interface MembershipPolicy {
    int maxBorrowedBooks();
    int loanDurationDays();
}
