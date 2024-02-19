import React, { useState, useEffect } from 'react';
import { TextField, Button, Alert } from '@mui/material';
import PasswordInput from '../Components/PasswordInput';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CreateUser = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmationPassword, setConfirmationPassword] = useState('');
  const [matchMessage, setMatchMessage] = useState("");
  const [emailMessage, setEmailMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");
  const [validEmail, setValidEmail] = useState(false);
  const [validPassword, setValidPassword] = useState(false);
  const [usernameMessage, setUsernameMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Check if passwords match
    if (password == confirmationPassword && validEmail && validPassword) {
      setMatchMessage("");
      const requestBody = {
        username: username,
        email: email,
        password: password
      };
      try{


        await axios.post(`http://localhost:8088/dionmangareader/users`, requestBody);
        navigate('/user');

      }
      catch(error){
        console.log("Error:", error.response.data.message);
        setUsernameMessage(error.response.data.message);

      }

    }

  };


  const handleEmailChange = (event) => {
    setEmail(event.target.value);
    const [message, isSuccess] = validateEmail(event.target.value);
    setEmailMessage(message);
    setValidEmail(isSuccess);

  };

  const handlePasswordChange = (event) => {
    const passwordInput = event.target.value;
    setPassword(passwordInput);
    const [message, isSuccess] = validatePassword(passwordInput);
    setPasswordMessage(message);
    setValidPassword(isSuccess);
    if(passwordInput !== confirmationPassword && confirmationPassword !== "" && passwordInput !== ""){
      const newMessage = "Passwords do not match";
      setMatchMessage(newMessage);
    } else {
      setMatchMessage("");
    }
  };
  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
    setUsernameMessage("");
  };

  const handleConfirmationPasswordChange = (event) => {
    const confirmationValue = event ? event.target.value : confirmationPassword;
    setConfirmationPassword(confirmationValue);

    if(password !== confirmationValue && confirmationValue !== "" && password !== ""){
      const newMessage = "Passwords do not match";
      setMatchMessage(newMessage);
    } else {
      setMatchMessage("");
    }
  };

  const validateEmail = (email) => {
    const pattern = /\S+@\S+\.\S+/;
    let isValid = true;
    let newMessage = "";

    if(email === "") {
      return [newMessage, false];
    }
    if(!pattern.test(email)){
      newMessage = "Email is invalid";
      isValid = false;
    } else {
      newMessage = "";
    }

    return [newMessage, isValid];
  };

  const validatePassword = (password) => {
        let isValid = true;
        let lengthMessage = "";

        if(password === "") {

          return ["", false];
        }

        if (password.length < 8) {
          lengthMessage = "Password is less than 8 characters";
          isValid = false;
        }
      return [isValid ? "" : lengthMessage, isValid];
    };

  return (
    <>
      <h1>Create New User</h1>
      <div style={{justifyContent: 'center', alignItems: 'center' }}>
        <div style={{ display: 'flex', flexDirection: 'row', width: '100%'}}>
          <form onSubmit={handleSubmit} style={{ display: 'flex', alignItems: 'center', flexDirection: 'column', width: '100%'}}>
            <TextField
              sx={{
                width: '520px',
                marginTop: '4%' ,
                marginBottom: '2%' ,
                '& label': {
                    color: 'white',
                },
                '& input': {
                    color: 'white',
              },
              }}
              label="Username"
              required={true}
              value={username}
              error={usernameMessage === "" ? false : true}
              helperText={usernameMessage == "" ? " " : usernameMessage}
              onChange={handleUsernameChange}
            />
            <TextField label="Email"
              value={email}
              onChange={handleEmailChange}
              error={emailMessage === "" ? false : true}
              helperText={emailMessage == "" ? " " : emailMessage}
              required={true}
              sx={{
                width: '520px',
                marginTop: '2%' ,
                marginBottom: '2%' ,
                  '& label': {
                    color: 'white',
                  },
                  '& input': {
                    color: 'white',
                  },
                }}
              />
              <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'row', marginBottom: '10%' }}>
                <PasswordInput
                  password={password}
                  handlePassword={handlePasswordChange}
                  matchedMessage={passwordMessage}
                  sx={{
                    width: '260px',
                    marginTop: '2%' ,
                    marginRight: '1%',
                    marginBottom: '2%',
                    '& label': {
                      color: 'white',
                    },
                    '& input': {
                      color: 'white',
                    },
                  }}
                />

                <PasswordInput
                  password={confirmationPassword}
                  label="Confirm Password"
                  handlePassword={handleConfirmationPasswordChange}
                  matchedMessage={matchMessage}
                  sx={{
                    width: '260px',
                    marginTop: '2%' ,
                    marginLeft: '1%',
                    marginBottom: '2%',
                    '& label': {
                      color: 'white',
                    },
                    '& input': {
                      color: 'white',
                    },
                  }}
                />
              </div>
              <div>
              </div>
              <Button type="submit" variant="contained" color="primary" sx ={{marginTop: '4%', width: '520px', justifyContent: 'center', alignItems: 'center' }}>
              Create User
              </Button>
          </form>

        </div>
      </div>
    </>
  );
};

export default CreateUser;
