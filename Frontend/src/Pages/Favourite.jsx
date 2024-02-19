import React from 'react';
import { Table, TableHead, TableRow, TableCell } from '@mui/material';
import BookShorthand from '../Components/BookShorthand';
import axios from 'axios';
import { useState, useEffect } from 'react';

const Favourite = () => {
  const [bookmarks, setFavourites] = useState([]);



  useEffect(() => {
    const getFavourites = async () => {
      try {
        const response = await axios.get('http://localhost:8088/dionmangareader/favourites/desc');
        const data = response.data;
        console.log(data);
        console.log(data.map((bookId) => bookId));

        setFavourites(data.map((bookId) => bookId));
      } catch (error) {
          console.error(error);
      }
    }
    getFavourites();
  }, []);

  return(
    <div>
      <h2>Most Read</h2>
      <Table sx ={{backgroundColor:'white', marginLeft: '0'}}>
          <TableHead>
              <TableRow>
                {[0, 5, 10, 15, 20, 25].map((startRowIndex) => (
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
    </div>
  )
};
export default Favourite;