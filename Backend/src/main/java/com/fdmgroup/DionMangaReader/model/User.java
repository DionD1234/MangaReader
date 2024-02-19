package com.fdmgroup.DionMangaReader.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User
{
	@GeneratedValue
	@Id
	private int userId;
	private String email;
	private String username;
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BookmarkedBook> bookmarkedBooks;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Favourite> favourites;
	
	public List<BookmarkedBook> getBookmarkedBooks()
	{
		return bookmarkedBooks;
	}
	public void setBookmarkedBooks(List<BookmarkedBook> bookmarkedBook)
	{
		this.bookmarkedBooks = bookmarkedBook;
	}
	public void addBookmarkedBook(BookmarkedBook bookmarkedBook) {
		bookmarkedBooks.add(bookmarkedBook);
	}
	public List<Favourite> getFavourites()
	{
		return favourites;
	}
	public void removeFavouriteByBook(int bookId)
	{
		for(int i = 0; i < favourites.size(); i++)
		{
			if (favourites.get(i).getBookId() == bookId) {
				favourites.remove(i);
			}
		}
	}
	public void removeBookmarkedBookByBook(int bookId)
	{
		for(int i = 0; i < bookmarkedBooks.size(); i++)
		{
			if (bookmarkedBooks.get(i).getBookId() == bookId) {
				bookmarkedBooks.remove(i);
			}
		}
	}
	public Favourite getFavBook(int bookId) {
		for(Favourite favBook : favourites) {
			if (favBook.getBookId( )== bookId){
				return favBook;
			}
		}
		return new Favourite();
	}
	public void addFavourite(Favourite favBook) {
		favourites.add(favBook);
	}
	
	public void setFavourites(List<Favourite> favBooks)
	{
		this.favourites = favBooks;
	}
	public User(String email, String username, String password)
	{
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.bookmarkedBooks = new ArrayList<>();
		this.favourites = new ArrayList<>();
	}
	public User()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", email=" + email + ", username=" + username + ", password=" + password
		        + "]";
	}
	
	
}
