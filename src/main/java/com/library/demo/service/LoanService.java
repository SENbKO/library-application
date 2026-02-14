package com.library.demo.service;

import com.library.demo.exception.loan_exception.LoanException;
import com.library.demo.model.book_model.BookCopy;
import com.library.demo.model.book_model.BookCopyStatus;
import com.library.demo.model.loan_model.Loan;
import com.library.demo.model.loan_model.LoanStatus;
import com.library.demo.model.membership_model.Membership;
import com.library.demo.model.user_model.Status;
import com.library.demo.model.user_model.User;
import com.library.demo.repository.BookCopyRepository;
import com.library.demo.repository.LoanRepository;
import com.library.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final BookCopyRepository bookCopyRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Transactional
    public Loan borrowBook(User user, String isbn){

        if(user.getStatus() != Status.ACTIVE){
            throw new IllegalStateException("User is not active");
        }

        Membership membership = user.getMembership();
        if(membership.getEndDate() != null && membership.getEndDate().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Membership expired");
        }

        long activeLoans = loanRepository.countByUserIdAndStatus(user.getId(), LoanStatus.ACTIVE);
        if(activeLoans >= 3){
            throw new IllegalStateException("Loan limit reached");
        }

        System.out.println("doing something");
        BookCopy bookCopy = bookCopyRepository.findFirstAvailableBookForUpdate(isbn)
                .orElseThrow(() ->new IllegalStateException("No book available right now"));
        bookCopy.setCopyStatus(BookCopyStatus.BORROWED);

        Loan loan = Loan.builder()
                .user(user)
                .loanDate(LocalDate.now())
                .bookCopy(bookCopy)
                .returnDate(LocalDate.now().plusDays(3))
                .status(LoanStatus.ACTIVE)
                .build();
        return loanRepository.save(loan);

    }

    @Transactional
    public void returnBook(long userId, long loanId){


        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new LoanException("No such a loan"));
        if(loan.getStatus()==LoanStatus.RETURNED){
            throw new LoanException("The loan is already returned");
        }
        if(!loan.getUser().getId().equals(userId)){
            throw new LoanException("This user did not make that loan");
        }
        loan.setStatus(LoanStatus.RETURNED);
        loan.setActualDateOfReturn(LocalDate.now());
        BookCopy bookCopy = loan.getBookCopy();
        bookCopy.setCopyStatus(BookCopyStatus.AVAILABLE);
    }

}
