package com.fdmgroup.DionMangaReader.dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%', :username, '%')")
	List<User> findPartialMatch(String username);
	
}
