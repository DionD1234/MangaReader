package com.fdmgroup.DionMangaReader.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.service.FavouriteService;
import com.fdmgroup.DionMangaReader.service.UserService;
@RestController
@RequestMapping("/favourites")
public class FavouriteController
{
private FavouriteService favouriteService;
private UserService userService;
	@Autowired
	public FavouriteController(FavouriteService favouriteService,
								UserService userService
								) {
		super();
		this.favouriteService = favouriteService;
		this.userService = userService;
	}
	
	@GetMapping
	public List<Favourite> firstEndpoint() {
		return favouriteService.findAll();
	}
	
	@GetMapping("/user/{userId}")
	public List<Integer> favouriteByUserId(@PathVariable int userId){
		List<Favourite> favouriteList = firstEndpoint();
		List<Integer> resultList = new ArrayList<>();
		for(Favourite favourite : favouriteList) {
			if (favourite.getUserId() == userId) {
				resultList.add(favourite.getBookId());
			}
		}
		return resultList;
	}
	

	@GetMapping("/{favId}")
	public Favourite findById(@PathVariable int favId) {
		return favouriteService.findById(favId);
	}
	@GetMapping("/top5")
	public List<Integer> findTop5(){
		List<Integer> bookIdList =  favouriteService.getByFavouriteCount();
		List<Integer> returnList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			returnList.add(bookIdList.get(i));
		}
		return returnList;
		
	}
	
	@GetMapping("/desc")
	public List<Integer> orderByDesc(){
		
		return favouriteService.getByFavouriteCount();

	}
	
	
	@PostMapping
	public void createNew(@RequestBody Favourite newFavourite)
	{
		System.out.println(newFavourite);
		favouriteService.save(newFavourite);
		User user = userService.findById(newFavourite.getUserId());
		user.addFavourite(favouriteService.findExactFavourite(
				newFavourite.getBookId(),
				newFavourite.getUserId()
				));
		userService.update(user);
	}
	@PutMapping
	public void updateFavourite(@RequestBody Favourite newFavourite)
	{
		System.out.println(newFavourite);
		favouriteService.update(newFavourite);
	}
	@DeleteMapping("/remove")
	public void deleteFavouriteByIds(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId) {
		System.out.println(bookId);
		System.out.println(userId);
		favouriteService.deleteByBookIdUserId(bookId, userId);
		
	}
	@DeleteMapping("/{favId}")
	public void deleteFavourite(@PathVariable int favId) {
		favouriteService.deleteById(favId);
	}
}
