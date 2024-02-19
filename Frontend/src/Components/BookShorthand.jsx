import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
function ShorthandBook({bookId}) {
    const [book, setBook] = useState({
        bookId: bookId,
        chapter: null,
        coverUrl: null,
        title: null,
        description: null,
    });
    console.log(book.bookId);

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(`http://localhost:8088/dionmangareader/books/${book.bookId}`);
            setBook(response.data);
          } catch (error) {
            console.error('Error fetching data:', error);
          }
        };

        fetchData();
      }, []);

  return (

      <div>
        <Link to={`/manga/${book.bookId}`}>
          <h2>{book.title}</h2>
          <img src={book.coverUrl} alt={book.title} />
        </Link>
      </div>

  );
}


export default ShorthandBook;