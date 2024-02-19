import { Visibility, VisibilityOff } from "@mui/icons-material";
import { IconButton, InputAdornment, TextField } from "@mui/material";
import { useState } from "react";

const PasswordInput = ({ password, handlePassword, sx, label, matchedMessage}) => {
  const [showPassword, setShowPassword] = useState(false);
  const message = matchedMessage == undefined || matchedMessage == null ? "" : matchedMessage;

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <TextField
      type={showPassword ? "text" : "password"}
      label={label ? label : "Password"}
      value={password}
      error={message == "" ? false : true}
      helperText={message == "" ? " " : message}
      onChange={handlePassword}
      required={true}
      sx = {sx}
      InputProps={{
        endAdornment: (
          <InputAdornment position="end">
            <IconButton
              aria-label="toggle password visibility"
              onClick={handleClickShowPassword}
              edge="end"
            >
              {showPassword ? <VisibilityOff /> : <Visibility />}
            </IconButton>
          </InputAdornment>
        ),
      }}
    />
  );
};

export default PasswordInput;