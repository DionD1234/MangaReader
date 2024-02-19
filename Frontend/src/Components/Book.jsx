import axios from "axios";
import { useState, useEffect} from "react";
import { Button, Select, MenuItem} from "@mui/material";
import GetUsername from "./GetUsername";
import RemoveBookmark from "./RemoveBookmark";
import GetUser from "./GetUser";
import RemoveFavourite from "./RemoveFavourite";

const Book = ({bookId}) => {
    const [bookmarkValue, setBookmarkValue] = useState("");
    const token = sessionStorage.getItem('token');
    const [options, setOptions] = useState([]);
    const requestOptions = {
        headers: {
            Authorization: token
        }
    }
    const [username, setUsername] = useState("");
    const isDisabled = username === "" ? true : false;
    const [bookmarkButton, setBookmarkButton] = useState("Add to Bookmarks");
    const [favouriteButton, setFavouriteButton] = useState("Add to Favourites");
    const [favouriteColor, setFavouriteColor] = useState("success");
    const [bookmarkColor, setBookmarkColor] = useState("success");

    const [book, setBook] = useState({
        bookId: parseInt(bookId),
        chapter: 0,
        coverUrl: "",
        title: "",
        description: "",
    });


    const changeBookmarkButton = () => {
        setBookmarkButton(bookmarkButton === "Add to Bookmarks" ? "Remove from Bookmarks" : "Add to Bookmarks");
        setBookmarkColor(bookmarkColor === "success" ? "error" : "success");
    };

    const changeFavouriteButton = () => {
        setFavouriteButton(favouriteButton === "Add to Favourites" ? "Remove from Favourites" : "Add to Favourites");
        setFavouriteColor(favouriteColor === "success" ? "error" : "success");
    };
    useEffect(() => {
        const fetchBookmarkData = async () => {
            try{
            const recievedUsername = await GetUsername();
            const userOb = await GetUser(recievedUsername);
            const userId = userOb.userId;
            const reponseBookmark = await axios.get(`http://localhost:8088/dionmangareader/bookmarks/users?bookId=${bookId}&userId=${userId}`);
            const reponseBook = await axios.get(`http://localhost:8088/dionmangareader/books/${bookId}`);
            const maxNumber = reponseBook.data.chapter + 1;
            setOptions(Array.from({ length: maxNumber}, (_, index) => index));
            setBookmarkValue(reponseBookmark.data.currentChapter);
            } catch (error) {
                console.error('Error fetching data:', error);
            }


        };
        fetchBookmarkData();
    }, [bookmarkColor, isDisabled]);




    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const username = await GetUsername();
                setUsername(username);
                const response = await axios.get(`http://localhost:8088/dionmangareader/users/?username=${username}`, requestOptions);
                const bookmarkList = response.data.bookmarkedBooks.map((book) => book.bookId);
                const favouriteList = response.data.favourites.map((book) => book.bookId);
                if (bookmarkList.includes(parseInt(bookId))) {
                    setBookmarkButton("Remove from Bookmarks");
                    setBookmarkColor("error");
                }
                if (favouriteList.includes(parseInt(bookId))) {
                    setFavouriteButton("Remove from Favourites");
                    setFavouriteColor("error");
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchUserData();

        const fetchBookData = async () => {
          try {
            const response = await axios.get(`http://localhost:8088/dionmangareader/books/${book.bookId}`);
            setBook({
                bookId: book.bookId,
                chapter: response.data.chapter,
                coverUrl: response.data.coverUrl.toString().replace(".256.", ".512."),
                title: response.data.title,
                description: response.data.description,
            });

          } catch (error) {
            console.error('Error fetching data:', error);
          }
        };
        fetchBookData();

    }, []);

    const handleChange = async (event) => {
        setBookmarkValue(event.target.value);
        const userOb = await GetUser(username);
        const userId = userOb.userId;
        const responseBookmark = await axios.get(`http://localhost:8088/dionmangareader/bookmarks/users?bookId=${bookId}&userId=${userId}`);
        const requestBody = {
            ...responseBookmark.data,
            currentChapter: event.target.value,
        };
        try {
            const response = await axios.put('http://localhost:8088/dionmangareader/bookmarks', requestBody, requestOptions);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

            const handleSubmitFavourite = async () => {
        const userOb = await GetUser(username);
        const userId = userOb.userId;

        const input = {
            bookId: bookId,
            userId: userId,
            command: favouriteButton === "Remove from Favourites" ?  true : false
        };
        console.log(input);

        changeFavouriteButton();
        RemoveFavourite(input)

    };
    const handleSubmitBookmark = async () => {
        const userOb = await GetUser(username);
        const userId = userOb.userId;

        const input = {
            bookId: bookId,
            userId: userId,
            command: bookmarkButton === "Remove from Bookmarks" ?  true : false
        };
        changeBookmarkButton();

        RemoveBookmark(input);

    };
//


  return (
    <div style={{ display: "flex", justifyItems: "center" }}>
        <div style = {{marginTop: '2%'}}>
            <img src={book.coverUrl} alt={book.title} />
        </div>
        <div style = {{ marginTop: '5%'}}>
            <h1>{book.title}</h1>
            <div style = {{ marginTop: '20%'}}>
                <div style = {{display: "flex", flexDirection: "row"}}>
                <   h3 style = {{ left: '0', marginLeft: '5%'}}>Description:</h3>
                <p style = {{marginLeft: '5%', marginRight: '10%'}}>{book.description}</p>
                </div>
                <div style = {{display: "flex", flexDirection: "row"}}>
                    <h3 style = {{ left: '0', marginLeft: '5%'}}>Chapters:</h3>
                    <p style = {{marginTop: '22px', marginBottom: '10px', marginLeft: '10px', marginRight: '10%'}}>{book.chapter}</p>
                        {(bookmarkColor == "error" && !isDisabled) &&(
                            <>
                        <h3 style = {{ left: '5%', marginLeft: '5%'}}>Your Current Chapter:</h3>
                        <Select value={bookmarkValue} onChange={handleChange} sx={{ maxHeight: '200px', overflowY: 'auto', color: "white" , marginLeft: "2%"}}>
                            {options.map((option, index) => (
                                <MenuItem key={index} value={option}>
                                    {option}
                                </MenuItem>
                            ))}
                        </Select>
                        </>)}
                </div>
            </div>
            <div style = {{marginTop: '10%', marginLeft: '30%', display: "flex", flexDirection: "row"}}>
                <Button variant="contained" onClick={handleSubmitFavourite} disabled = {isDisabled} color = {favouriteColor} style = {{marginLeft: '5%', marginRight: '10%', width:'200px'}}>{favouriteButton}</Button>
                <Button variant="contained" onClick={handleSubmitBookmark} disabled = {isDisabled} color = {bookmarkColor} style = {{marginLeft: '5%', marginRight: '10%', width:'200px'}}>{bookmarkButton}</Button>
            </div>
        </div>
    </div>
  );
}


export default Book;