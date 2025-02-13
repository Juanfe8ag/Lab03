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
    public void shouldNotHaveALoan_isbn(){
        user.setId("101");
        library.addBook(example);
        assertNull(library.loanABook("101", "1"));
    }
}