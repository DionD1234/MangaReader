import {useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Book from '../Components/Book';
import { Button } from '@mui/material';
import  GetUsername  from '../Components/GetUsername';

const Manga = () => {
    const {mangaId} = useParams();


    return (
        <div>
            {mangaId && <Book bookId={mangaId} />}
        </div>
    )

};

export default Manga;