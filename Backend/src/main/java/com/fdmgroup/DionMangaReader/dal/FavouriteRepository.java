package com.fdmgroup.DionMangaReader.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Integer>
{
	List<Favourite> findByBookId(int bookId);
	
	@Query("SELECT f FROM Favourite f WHERE f.bookId = :bookId AND f.userId = :userId")
    Favourite findExactFavourite(int bookId, int userId);

	@Query("SELECT f FROM Favourite f WHERE f.bookId IN (SELECT f2.bookId FROM Favourite f2 GROUP BY f2.bookId ORDER BY COUNT(f2) DESC)")
	List<Favourite> orderByDesc();
}
