package com.fdmgroup.DionMangaReader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Favourite
{
	@GeneratedValue
	@Id
	private int favId;
	private int bookId;
	private int userId;

	
	public Favourite(int bookId, int userId)
	{
		super();
		this.bookId = bookId;
		this.userId = userId;
	}
	public Favourite()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFavId()
	{
		return favId;
	}
	public void setFavId(int favId)
	{
		this.favId = favId;
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
	@Override
	public String toString()
	{
		return "Favourite [favId=" + favId + ", bookId=" + bookId + ", userId=" + userId + "]";
	}
	
}
