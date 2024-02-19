package com.fdmgroup.DionMangaReader.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.FavouriteRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.exceptions.NotFoundException;
import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.model.User;

@Service
public class FavouriteService
{
	private FavouriteRepository favouriteRepository;
	private BookRepository bookRepository;
	private UserRepository userRepository;
	
	@Autowired
	public FavouriteService(
			FavouriteRepository favouriteRepository, 
			UserRepository userRepository, 
			BookRepository bookRepository) 
	{
		super();
		this.favouriteRepository = favouriteRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}
	
	public List<Favourite> findAll(){
		return this.favouriteRepository.findAll();
	}
	
	public List<Favourite> findByBookId(int bookId) 
	{
		
		return favouriteRepository.findByBookId(bookId);
	}
	public Favourite findExactFavourite(int bookId, int userId) {
		return favouriteRepository.findExactFavourite(bookId, userId);
	}
	public Favourite findById(int favId) {
		
		return favouriteRepository.findById(favId)
				.orElseThrow(()->new NotFoundException("Favourite with Id: "+ favId + "cannot be found."));
	}
	
	public void save(Favourite newFavourite)
	{
		this.favouriteRepository.save(newFavourite);
	}

	public void update(Favourite newFavourite)
	{
		if(favouriteRepository.existsById(newFavourite.getFavId()))
		{
			favouriteRepository.save(newFavourite);
			return;
		}
		
		throw new RuntimeException("Must provide a user id for 'put' mapping");
	}
	public List<Integer> getByFavouriteCount() {
		List<Favourite> allBookmarks = findAll();
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        
        for (Favourite favourite : allBookmarks) {
            int bookId = favourite.getBookId(); // Assuming getBookId() retrieves the book_id from each object
            frequencyMap.put(bookId, frequencyMap.getOrDefault(bookId, 0) + 1);
        }
        List<Integer> sortedBookIds = frequencyMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedBookIds;

        
    }
	public void deleteById(int favId)
	{
		delete(findById(favId));
		
	}
	public void delete(Favourite favourite)
	{
		int bookId = favourite.getBookId();
		int userId = favourite.getUserId();
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(()->new NotFoundException("Book with Id: "+ bookId + " cannot be found."));
		User user = userRepository.findById(userId)
				.orElseThrow(()->new NotFoundException("User with Id: "+ userId + " cannot be found."));
		
		List<Favourite> newBookmarkedBookBookList = book.getFavourites();
		for (int i = 0; i<newBookmarkedBookBookList.size(); i++) {
			if(book.getFavourites().get(i).getFavId() == favourite.getFavId()) {
				newBookmarkedBookBookList.remove(i);
				break;
			}
		}
		book.setFavourites(newBookmarkedBookBookList);
		bookRepository.save(book);

		List<Favourite> newBookmarkedBookUserList = user.getFavourites();
		for (int i = 0; i<newBookmarkedBookUserList.size(); i++) {
			if(user.getFavourites().get(i).getFavId() == favourite.getFavId()) {
				newBookmarkedBookUserList.remove(i);
				break;
			}
		}
		user.setFavourites(newBookmarkedBookUserList);
		userRepository.save(user);
		favouriteRepository.delete(favourite);
		
		
	}
	
	public void deleteByBookIdUserId(int bookId, int userId)
	{
		
		delete(findExactFavourite(bookId, userId));
		
	}

}


