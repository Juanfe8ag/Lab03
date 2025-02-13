package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.Library;
import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.user.User;
import edu.eci.cvds.tdd.library.loan.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LibraryTest {

    private final Library library = new Library();
    private final Book example = new Book("example","author1","111");
    private final User user = new User();

    @Test
    public void shouldAddABook(){
        assertTrue(library.addBook(example));
    }

    @Test
    public void shouldAddARepeatedBook(){
        library.addBook(example);
        library.addBook(example);
        assertEquals(library.searchBook(example),2);
    }

    @Test
    public void shouldNotAddABook(){
        assertFalse(library.addBook(null));
    }

    @Test
    void shouldAddAUser(){
        user.setId("101");
        assertTrue(library.addUser(user));
        assertEquals(user, library.searchUser("101"));
    }

    @Test
    public void shouldHaveALoan(){
        user.setId("101");
        library.addBook(example);
        library.addUser(user);
        assertNotNull(library.loanABook("101", "111"));
    }

    @Test
    public void shouldNotHaveALoan_userId(){
        user.setId("101");
        library.addBook(example);
        assertNull(library.loanABook("0", "111"));
    }

    @Test
    void shouldNotLoan_UnavailableBook() {
        user.setId("101");
        library.addUser(user);
        assertNull(library.loanABook("101", "112"));
    }

    @Test
    public void shouldNotHaveALoan_isbn(){
        user.setId("101");
        library.addBook(example);
        assertNull(library.loanABook("101", "1"));
    }

    @Test
    void testSearchUser(){
        user.setId("101");
        library.addUser(user);
        assertEquals(user, library.searchUser("101"));
        assertNull(library.searchUser("999"));
    }

    @Test
    void testSearchBook(){
        library.addBook(example);
        assertEquals(example, library.searchBook("111"));
        assertNull(library.searchBook("999"));
    }

    @Test
    void shouldCheckActiveLoans(){
        user.setId("101");
        User user2 = new User();
        user2.setId("102");
        library.addUser(user);
        library.addUser(user2);
        library.addBook(example);
        Loan loan = library.loanABook("101", "111");
        assertTrue(library.checkActiveLoans(user2, example));
        assertFalse(library.checkActiveLoans(user, example));
    }

    @Test
    void shouldLoanABook(){
        user.setId("101");
        library.addUser(user);
        library.addBook(example);
        Loan loan = library.loanABook("101", "111");
        assertNotNull(loan);
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        Loan duplicateLoan = library.loanABook("101", "111");
        assertNull(duplicateLoan);
    }

    @Test
    void ShouldLoanMultipleBooks() {
        user.setId("101");
        library.addUser(user);
        Book example2 = new Book("example2","author2","123");
        library.addBook(example);
        library.addBook(example2);
        Loan loan1 = library.loanABook("101", "111");
        Loan loan2 = library.loanABook("101", "123");
        assertNotNull(loan1);
        assertNotNull(loan2);
    }

    @Test
    void shouldNotLoanSameBook() {
        user.setId("101");
        library.addUser(user);
        library.addBook(example);
        library.addBook(example);
        Loan loan1 = library.loanABook("101", "111");
        Loan loan2 = library.loanABook("101", "111");
        assertNotNull(loan1);
        assertNull(loan2);
    }

    @Test
    void shouldReturnLoan(){
        user.setId("101");
        library.addUser(user);
        library.addBook(example);
        Loan loan = library.loanABook("101", "111");
        assertNotNull(loan);
        Loan returnedLoan = library.returnLoan(loan);
        assertNotNull(returnedLoan);
        assertEquals(LoanStatus.RETURNED, returnedLoan.getStatus());
    }

    @Test
    void shouldReturnNonexistentLoan() {
        Loan fakeLoan = new Loan();
        assertNull(library.returnLoan(fakeLoan));
    }

}