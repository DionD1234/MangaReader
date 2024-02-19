import React, { useEffect, useState} from "react";
import { useParams} from "react-router-dom"
import axios from 'axios';
import { Table, TableHead, TableRow, TableCell } from '@mui/material';
import BookShorthand from '../Components/BookShorthand';

const Search = () => {
    const [bookArray, setBookArray] = useState([]);
    const [bookRow, setBookRow] = useState([]);

    const {title} = useParams();
    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log("title: ", title)
                const response = await axios.get(`http://localhost:8088/dionmangareader/books/search?title=${title}`);
                const dataArray = response.data.map((book) => book.bookId);
                setBookArray(dataArray);
                const arrayLength = dataArray.length;

                setBookRow(Array.from({ length: Math.ceil(arrayLength / 5) }, (_, index) => index * 5));

            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchData();

    }, [title]);



    return (
        <div>
            <h1>Search Page</h1>
            <h2>Result: {title}</h2>
            <Table sx ={{backgroundColor:'white', marginLeft: '0'}}>
                <TableHead>
                    <TableRow>
                        {bookRow.map((startRowIndex) => (
                        <TableRow key={startRowIndex}>
                            {bookArray.slice(startRowIndex, startRowIndex + 5).map((bookId, index) => (
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

export default Search;
