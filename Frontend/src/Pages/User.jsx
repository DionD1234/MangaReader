import { Outlet, useNavigate } from "react-router-dom";
import { useEffect } from "react";

const User = () => {
  const navigate = useNavigate();
  useEffect(() => {
    const token = sessionStorage.getItem('token');

    if (token && token !== "null") {
      navigate('/user/aboutuser');
      console.log("valid");
    }

  }, []);

  return (
    <>
      <Outlet />
    </>
  );
};

export default User;