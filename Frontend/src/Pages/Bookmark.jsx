import React from 'react';
import { Table, TableHead, TableRow, TableCell } from '@mui/material';
import BookShorthand from '../Components/BookShorthand';
import axios from 'axios';
import { useState, useEffect } from 'react';

const Bookmark = () => {
  const [bookmarks, setBookmarks] = useState([]);



  useEffect(() => {
    const getBookmarks = async () => {
      try {
        const response = await axios.get('http://localhost:8088/dionmangareader/bookmarks/desc');
        const data = response.data;

        setBookmarks(data.map((bookId) => bookId));
      } catch (error) {
          console.error(error);
      }
    }
    getBookmarks();
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
export default Bookmark;
