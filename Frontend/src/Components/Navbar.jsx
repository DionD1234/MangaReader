import { React, useEffect, useState }  from 'react';
import '../Styles/Navbar.css';
import { Link, useNavigate } from 'react-router-dom';
import { Button, Input } from '@mui/material';
import GetUsername from './GetUsername';
const Navbar = () => {
    const navigate = useNavigate();
    const token = sessionStorage.getItem('token');
    const [username, setUsername] = useState("");
    const tokenValid = token && token !== "null";
    useEffect(() => {
        const getUserData = async () => {
            if (tokenValid) {
                const username = await GetUsername();
                setUsername(username);
            } else {
                setUsername("");
            }

        }
        getUserData();
    });

    const search = (event) => {
        event.preventDefault();
        const title = event.target.elements.title.value;
        navigate(`/search/${title}`);
    };

    return (
        <div className="navbar-container">
            <div className="topnav">
                <Link to="/" className="active">Home</Link>
                <Link to="/discover">Discover</Link>
                {tokenValid && <Link to="/mysaved">Saved Manga</Link>}
            </div>
            <div className="search-container">
                <div className="user">
                    <Link to= {tokenValid ? "/user/aboutuser" : "/user"} className="login" style={{ whiteSpace: 'nowrap' }}>{username == "" ? "Log in" : username }</Link>
                </div>
                <form onSubmit={search}>
                    <div style={{ display: 'flex', gap: '10px' }}>
                        <Input type="text" sx={{ backgroundColor: 'white' }} placeholder=" Search..." name="title" />
                        <Button type="submit" sx={{ backgroundColor: 'white' }}>Submit</Button>
                    </div>
                </form>

            </div>

        </div>
    );
}

export default Navbar;