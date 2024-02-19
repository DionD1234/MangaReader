package com.fdmgroup.DionMangaReader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BookmarkedBook
{
	@GeneratedValue
	@Id
	private int bookmarkedBookId;
	private int bookId;
	private int userId;
	private int currentChapter;
	
	
	public BookmarkedBook(int bookId, int userId, int currentChapter)
	{
		super();
		
		this.bookId = bookId;
		this.userId = userId;
		this.currentChapter = currentChapter;
	}
	public BookmarkedBook()
	{
		super();
	}
	
	public int getBookmarkedBookId()
	{
		return bookmarkedBookId;
	}
	public void setBookmarkedBookId(int bookmarkedBookid)
	{
		this.bookmarkedBookId = bookmarkedBookid;
	}
	public int getBookId()
	{
		return bookId;
	}
	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public int getCurrentChapter()
	{
		return currentChapter;
	}
	public void setCurrentChapter(int currentChapter)
	{
		this.currentChapter = currentChapter;
	}
	@Override
	public String toString()
	{
		return "BookmarkedBook [bookmarkedBookid=" + bookmarkedBookId + ", bookId=" + bookId + ", userId=" + userId
		        + ", currentChapter=" + currentChapter + "]";
	}
	
	
	
	
}
