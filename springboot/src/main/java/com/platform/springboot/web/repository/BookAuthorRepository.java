package com.platform.springboot.web.repository;

import com.platform.springboot.support.jpa.CustomJpaRepository;
import com.platform.springboot.web.entity.Author;
import com.platform.springboot.web.entity.Book;
import com.platform.springboot.web.entity.BookAuthor;

/**
 * <b>BookAuthor Repository</b><br>
 * You can use NamedQuery or Query annotation here.<br>
 * 
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public interface BookAuthorRepository extends CustomJpaRepository<BookAuthor, Long> {

	public BookAuthor findByBookAndAuthor(Book book, Author author);

	public void deleteByAuthor(Author author);

	public void deleteByBook(Book book);
}
