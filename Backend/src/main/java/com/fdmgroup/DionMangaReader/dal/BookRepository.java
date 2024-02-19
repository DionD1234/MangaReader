package com.fdmgroup.DionMangaReader.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.User;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer>
{
	List<Book> findByTitle(String title);
	
	@Query("SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :title, '%')")
	List<Book> findPartialMatch(String title);
}
