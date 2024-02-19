import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { TextField, Button } from '@mui/material';
import { Link } from 'react-router-dom'; // Import Link from React Router
import PasswordInput from "../Components/PasswordInput"; // Import your PasswordInput component


const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [message, setMessage] = useState("");
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };


    const tryLogin = (e) => {
        e.preventDefault();
        const attempt = {username, password}
        console.log(attempt)
        const requestOptions = {
            auth:{
                username:username,
                password:password
            }
        }
        axios.post("http://localhost:8088/dionmangareader/auth/login",{},requestOptions)
            .then(response=>{
                sessionStorage.setItem('token', "Bearer " + response.data);
                navigate("/");

            }).catch(error => {
                console.error("Error:", error);
                if(error.response.status === 401){
                    setMessage("Invalid credentials");
                }
            });
    }


    return (
        <div>
            <h1>User Login</h1>
            <div>
                <form onSubmit={tryLogin}>
                    <div style={{ display: 'flex', flexDirection: 'row' }}>
                        <TextField
                            id="username"
                            label="Username"
                            variant="outlined"
                            required={true}
                            value={username}
                            onChange={handleUsernameChange}
                            sx={{
                                marginRight: '1%',
                                '& label': {
                                    color: 'white',
                                },
                                '& input': {
                                    color: 'white',
                                },
                            }}
                        />
                        {/* Use PasswordInput component for the password field */}
                        <PasswordInput
                            password={password}
                            handlePassword={handlePasswordChange}
                            sx={{
                                marginLeft: '1%',
                                '& label': {
                                    color: 'white',
                                },
                                '& input': {
                                    color: 'white',
                                },
                              }}
                        />
                    </div>
                    <p style={{color: 'red'}}>{message}</p>
                    <div style={{ display: 'flex', justifyContent: 'center', marginTop: '2%' }}>
                        <Button variant="contained" type="submit" sx={{marginRight: '1%'}}>Login</Button>
                        <Link to="/create-user">
                            <Button variant="contained" sx={{marginLeft: '1%', whiteSpace: 'nowrap'}}>Create New User</Button>
                        </Link>

                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;