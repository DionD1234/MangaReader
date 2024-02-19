import React, { useState, useEffect } from 'react';
import { Button, FormControlLabel, Checkbox } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import GetUsername from '../Components/GetUsername'; // Importing the custom hook
import GetUser from '../Components/GetUser'; // Importing the custom hook

const AboutUser = () => {
  const [username, setUsername] = useState("");
  const [receivedUserId, setReceivedUserId] = useState(0);
  const navigate = useNavigate();
  const [receivedEmail, setReceivedEmail] = useState("");
  const [usernameChecked, setUsernameChecked] = useState(false);
  const [emailChecked, setEmailChecked] = useState(false);
  const [passwordChecked, setPasswordChecked] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      let receivedUsername = "";
      try {
        receivedUsername = await GetUsername();
        setUsername(receivedUsername);

        const receivedUser = await GetUser(receivedUsername);
        const { email, userId} = receivedUser;;
        setReceivedEmail(email);
        setReceivedUserId(userId);

         // Set the received username to state
      } catch (error) {
        console.error("Error:", error);
        if(error.response.status === 401){
          logout();
        }
      }
    };

    fetchData(); // Call the function to fetch username
  }); // Remove the dependency array to run the effect on every render

  const logout = () => {
    sessionStorage.setItem('token', null);
    navigate("/user");

  };

  const handleUsernameChange = (event) => {
    const isChecked = event.target.checked;
    setUsernameChecked(isChecked);
  };

  const handleEmailChange = (event) => {
    const isChecked = event.target.checked;
    setEmailChecked(isChecked);
  };

  const handlePasswordChange = (event) => {
    const isChecked = event.target.checked;
    setPasswordChecked(isChecked);
  };

  const handleChangeDetails = () => {
    // You can use the state variables here to determine
    // which checkboxes are checked and perform actions accordingly
    const queryParams = new URLSearchParams({
      userId: receivedUserId,
      usernameChecked: usernameChecked,
      emailChecked: emailChecked,
      passwordChecked: passwordChecked
    }).toString();

    navigate(`/user/update?${queryParams}`);
  };

  return (
    <div>
      <h1>Welcome {username}</h1>

      <h2>User Details</h2>
      <form>
      <FormControlLabel
          control={<Checkbox checked={usernameChecked} onChange={handleUsernameChange} />}
          label={username}
        />
        <br />
        <FormControlLabel
          control={<Checkbox checked={emailChecked} onChange={handleEmailChange} />}
          label={receivedEmail}
        />
        <br />
        <FormControlLabel
          control={<Checkbox checked={passwordChecked} onChange={handlePasswordChange} />}
          label="Password"
        />
      </form>
      <Button onClick={handleChangeDetails} disabled = { !(passwordChecked || emailChecked || usernameChecked) } variant="contained" color="primary" sx ={{marginRight: '1%', marginTop:'2%'}}>Change These Details?</Button>
      <Button onClick={logout} variant="contained" color="primary" sx ={{marginLeft: '1%', marginTop:'2%'}}>Logout</Button>
    </div>
  );
};

export default AboutUser;