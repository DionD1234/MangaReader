package com.fdmgroup.DionMangaReader.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Book
{
	@Id
	@GeneratedValue
	private int bookId;
	private int chapter;
	private String title;
	private String coverUrl;
	@Column(length = 1000)
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BookmarkedBook> bookmarkedBooks;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Favourite> favourites;
	
	public Book(int chapter, String coverUrl, String title, String description)
	{
		super();
		this.chapter = chapter;
		this.title = title;
		this.coverUrl = coverUrl;
		this.description = description;
		this.bookmarkedBooks = new ArrayList<>();
		this.favourites = new ArrayList<>();
	}

	public Book()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public List<BookmarkedBook> getBookmarkedBooks()
	{
		return bookmarkedBooks;
	}
	public void removeBookmarkedBookByUser(int userId)
	{
		for(int i = 0; i < bookmarkedBooks.size(); i++)
		{
			if (bookmarkedBooks.get(i).getUserId() == userId) {
				bookmarkedBooks.remove(i);
			}
		}
	}

	public void setBookmarkedBooks(List<BookmarkedBook> bookmarkedBooks)
	{
		this.bookmarkedBooks = bookmarkedBooks;
	}
	public void addBookmarkedBook(BookmarkedBook bookmarkedBook)
	{
		bookmarkedBooks.add(bookmarkedBook);
	}
	public void removeFavouriteByUser(int userId)
	{
		for(int i = 0; i < favourites.size(); i++)
		{
			if (favourites.get(i).getUserId() == userId) {
				favourites.remove(i);
			}
		}
	}
	public List<Favourite> getFavourites()
	{
		return favourites;
	}

	public void setFavourites(List<Favourite> favourites)
	{
		this.favourites = favourites;
	}
	public void addFavourite(Favourite favourite)
	{
		favourites.add(favourite);
	}
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getBookId()
	{
		return bookId;
	}

	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}

	public int getChapter()
	{
		return chapter;
	}

	public void setChapter(int chapter)
	{
		this.chapter = chapter;
	}

	public String getCoverUrl()
	{
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl)
	{
		this.coverUrl = coverUrl;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "Book [bookId=" + bookId + ", chapter=" + chapter + ", coverUrl=" + coverUrl + ", description="
		        + description + "]";
	}
}
