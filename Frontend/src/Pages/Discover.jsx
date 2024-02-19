import { Routes, Route, Outlet } from "react-router-dom";
import '../Styles/Discover.css';
import React from "react";


const Discover = () => {
    return (
        <div>
            <h1>Discover Page</h1>

            <Outlet />
        </div>
    );
}

export default Discover;