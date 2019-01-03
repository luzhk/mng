package com.platform.springboot.web.repository;

import java.util.Set;

import com.platform.springboot.support.jpa.CustomJpaRepository;
import com.platform.springboot.web.entity.Author;

/**
 * <b>Author Repository</b><br>
 * You can use NamedQuery or Query annotation here.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public interface AuthorRepository extends CustomJpaRepository<Author, Long> {

	public Author findByAuthorName(String authorName);

	public Set<Author> findByAuthorBooks_Book_Id(Long bookId);
}
