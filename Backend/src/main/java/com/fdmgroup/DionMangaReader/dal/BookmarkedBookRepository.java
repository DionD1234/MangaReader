package com.fdmgroup.DionMangaReader.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;

@Repository
public interface BookmarkedBookRepository extends JpaRepository<BookmarkedBook,Integer>
{
	List<BookmarkedBook> findByBookId(int bookid);
	
	
	@Query("SELECT bb FROM BookmarkedBook bb WHERE bb.bookId = :bookId AND bb.userId = :userId")
    BookmarkedBook findExactBookmark(int bookId, int userId);
	
}
