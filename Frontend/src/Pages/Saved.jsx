import React, { useEffect, useState } from 'react';
import axios from 'axios';
import GetUser from '../Components/GetUser';
import GetUsername from '../Components/GetUsername';
import { Table, TableHead, TableRow, TableCell } from '@mui/material';
import BookShorthand from '../Components/BookShorthand';

const Saved = () => {
    const [bookmarks, setBookmarks] = useState([]);
    const [favourites, setFavourites] = useState([]);
    const [favouriteRows, setFavouriteRows] = useState([]);
    const [bookmarkRows, setBookmarkRows] = useState([]);
    const [username, setUsername] = useState("");
    useEffect(() => {
        const fetchBookmarkData = async () => {
          try {
            let responseUsername = ""
            if (username === "") {
                responseUsername = await GetUsername();
                setUsername(responseUsername);
            }else {
                responseUsername = username;
            }
            const responseUserId = await GetUser(responseUsername);
            const foundUserId = responseUserId.userId;
            console.log("1 ",foundUserId)
            const response = await axios.get(`http://localhost:8088/dionmangareader/bookmarks/user/${foundUserId}`);
            console.log(response.data);
            const dataArray = response.data.map((bookId) => bookId);
            console.log(dataArray);
            setBookmarks(dataArray);
            const arrayLength = dataArray.length;

            setBookmarkRows(Array.from({ length: Math.ceil(arrayLength / 5) }, (_, index) => index * 5));
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchBookmarkData();

        const fetchFavouriteData = async () => {
            try {
                let responseUsername = ""
                if (username === "") {
                    responseUsername = await GetUsername();
                    setUsername(responseUsername);
                }else {
                    responseUsername = username;
                }

                // let foundUserId = 0;
                // if (userId === 0 ) {
                //     const responseUserId = await GetUser(responseUsername);
                //     foundUserId = responseUserId.userId;
                //     setUserId(foundUserId);
                // }else{
                //     foundUserId = userId;
                // }
                const responseUserId = await GetUser(responseUsername);
                const foundUserId = responseUserId.userId;
                console.log("2 ", foundUserId)

                const response = await axios.get(`http://localhost:8088/dionmangareader/favourites/user/${foundUserId}`);
                const dataArray = response.data.map((bookId) => bookId);
                setFavourites(dataArray);
                const arrayLength = dataArray.length;
                const increment = 5; // or any other desired increment

                setFavouriteRows(Array.from({ length: Math.ceil(arrayLength / increment) }, (_, index) => index * increment));
              } catch (error) {
                  console.error('Error fetching data:', error);
              }
          };

          fetchFavouriteData();
      }, []);






    return (
        <div>
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <h1>Saved Manga</h1>
        </div>
            <div style ={{  alignContent: 'center' }}>
                    <h2>Bookmarked</h2>
            </div>
            <Table sx ={{backgroundColor:'white', marginLeft: '0'}}>
                <TableHead>
                    <TableRow>
                        {bookmarkRows.map((startRowIndex) => (
                        <TableRow key={startRowIndex}>
                            {bookmarks.slice(startRowIndex, startRowIndex + 5).map((bookId, index) => (
                                <TableCell key={index}>
                                <BookShorthand
                                bookId={bookId} />
                            </TableCell>
                            ))}
                        </TableRow>
                        ))}
                    </TableRow>
                </TableHead>
            </Table>
            <div style={{ paddingTop: "20px" }}>

                    <h2>Favourites</h2>

            </div>
            <Table sx ={{backgroundColor:'white'}}>
                <TableHead>
                    <TableRow>
                        {favouriteRows.map((startRowIndex) => (
                        <TableRow key={startRowIndex}>
                            {favourites.slice(startRowIndex, startRowIndex + 5).map((bookId, index) => (
                                <TableCell key={index}>
                                <BookShorthand
                                bookId={bookId} />
                            </TableCell>
                            ))}
                        </TableRow>
                        ))}
                    </TableRow>
                </TableHead>
            </Table>
        </div>
    );
}
export default Saved;