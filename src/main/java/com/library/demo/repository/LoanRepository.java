package com.library.demo.repository;

import com.library.demo.model.loan_model.Loan;
import com.library.demo.model.loan_model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByUserIdAndStatus(Long userId, LoanStatus status);

    Long countByUserIdAndStatus(Long userId, LoanStatus status);
}
