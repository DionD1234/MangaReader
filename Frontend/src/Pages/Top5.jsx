import React, { useEffect } from 'react';
import { Link } from "react-router-dom";
import { Table, TableHead, TableRow, TableCell, TableBody } from '@mui/material';
import { useState } from 'react';
import BookShorthand from '../Components/BookShorthand';
import axios from 'axios';

const Top5 = () => {
    const [bookmarkTop5Row, setBookmarkTop5Row] = useState("");
    const [favouriteTop5Row, setFavouriteTop5Row] = useState("");

    useEffect(() => {
        const getBookmarkTop5 = async () => {
            try {
                const response = await axios.get('http://localhost:8088/dionmangareader/bookmarks/top5');
                const data = response.data;
                setBookmarkTop5Row(data.map((bookId) =>
                    <TableCell key={bookId}>
                        <BookShorthand
                        bookId={bookId} />
                    </TableCell>
                ));
            } catch (error) {
                console.error(error);
            }
        };

        const getFavouriteTop5 = async () => {
            try {
                const response = await axios.get('http://localhost:8088/dionmangareader/favourites/top5');
                const data = response.data;
                setFavouriteTop5Row(data.map((bookId) =>
                    <TableCell key={bookId}>
                        <BookShorthand
                        bookId={bookId} />
                    </TableCell>
                ));
            } catch (error) {
                console.error(error);
            }
        };

        getFavouriteTop5();
        getBookmarkTop5();

    }, []);



    return (
        <div>
            <div style ={{  alignContent: 'center' }}>
                <Link to="/discover/bookmark">
                    <h2>Most Read</h2>
                </Link>
            </div>
            <Table sx ={{backgroundColor:'white', marginLeft: '0'}}>
                <TableHead>
                    <TableRow>
                        {bookmarkTop5Row}
                    </TableRow>
                </TableHead>
            </Table>
            <div style={{ paddingTop: "20px" }}>
                <Link to="/discover/favourite">
                    <h2>Highest Favourite</h2>
                </Link>
            </div>
            <Table sx ={{backgroundColor:'white'}}>
                <TableBody>
                    <TableRow>
                        {favouriteTop5Row}
                    </TableRow>
                </TableBody>
            </Table>
        </div>
    );
};
export default Top5;