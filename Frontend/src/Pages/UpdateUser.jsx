import React, { useState, useEffect } from 'react';
import { TextField, Button, Alert } from '@mui/material';
import { useNavigate, useLocation} from 'react-router-dom';
import PasswordInput from '../Components/PasswordInput';
import axios from 'axios';

const UpdateUser = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmationPassword, setConfirmationPassword] = useState("");
  const navigate = useNavigate();
  const userId = queryParams.get('userId');
  const usernameChecked = queryParams.get('usernameChecked') === 'true';
  const emailChecked = queryParams.get('emailChecked') === 'true';
  const passwordChecked = queryParams.get('passwordChecked') === 'true';
  const [usernameMessage, setUsernameMessage] = useState("");
  const [emailMessage, setEmailMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");
  const [validEmail, setValidEmail] = useState(!emailChecked);
  const [validPassword, setValidPassword] = useState(!passwordChecked);
  const [matchMessage, setMatchMessage] = useState("");


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
    let newMessage = "";
    setConfirmationPassword(confirmationValue);
    if(password !== confirmationValue && confirmationValue !== "" && password !== ""){
      newMessage = "Passwords do not match";
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


        console.log("Password is valid: " + "'" +lengthMessage + "'");
      return [isValid ? "" : lengthMessage, isValid];
  };



  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log("submitting form")
    console.log(validPassword);
    if (password == confirmationPassword && validEmail && validPassword) {
      const requestBody = {
        userId: userId
      };

      if (emailChecked) {
        requestBody.email = email;
      }

      if (usernameChecked) {
        requestBody.username = username;
      }

      if (passwordChecked) {
        requestBody.password = password;
      }
      console.log("request body", requestBody.username);
      try{
        await axios.put(`http://localhost:8088/dionmangareader/users`, requestBody)

        sessionStorage.setItem('token', null);

      }catch(error){
        console.log("Error:", error.response.data.message);
        if(error.response.status === 401){
          sessionStorage.setItem('token', null);
          navigate("/user");
        }else if (error.response.status === 400){
          setUsernameMessage(error.response.data.message);
        }
      }
    }



  };

  return (
    <div>
      <div style={{ position: 'relative', width: '100%', height: '100px' }}>
        <Button
          variant="contained"
          onClick={() => navigate("/user/aboutuser")}
          sx = {{ position: 'absolute', top: 0, left: 0 }}>

          Back to User Details
        </Button>

      </div>
        <h1>Update User Details</h1>
      <div style={{ display: 'flex', flexDirection: 'row', width: '100%' }}>
        <form onSubmit={handleSubmit} style={{ display: 'flex', alignItems: 'center', flexDirection: 'column', width: '100%'}}>
          {usernameChecked ? (
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
          ) : null}
            {emailChecked ? (
            <TextField
            label="Email"
            value={email}
            error={emailMessage === "" ? false : true}
            helperText={emailMessage == "" ? " " : emailMessage}
            onChange={handleEmailChange}
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
              }} />
          ): null}
            {passwordChecked ?(
              <div style={{ display: 'flex', flexDirection: 'row' }}>
                <PasswordInput
                  password={password}
                  required = {true}
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

            ) : null}

          <Button type="submit" variant="contained" color="primary">
            Save Changes
          </Button>
        </form>
      </div>
    </div>
  );
};

export default UpdateUser;