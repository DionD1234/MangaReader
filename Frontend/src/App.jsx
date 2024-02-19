import { Route, Routes } from 'react-router-dom'
import './App.css'
import Navbar from './Components/Navbar.jsx'
import Search from './Pages/Search.jsx'
import Home from './Pages/Home.jsx'
import { React, useEffect, useState } from 'react'
import Discover from './Pages/Discover.jsx'
import Top5 from './Pages/Top5.jsx'
import Bookmark from './Pages/Bookmark.jsx'
import Favourite from './Pages/Favourite.jsx'
import Login from './Pages/Login.jsx'
import User from './Pages/User.jsx'
import AboutUser from './Pages/AboutUser.jsx'
import CreateUser from './Pages/CreateUser.jsx'
import UpdateUser from './Pages/UpdateUser.jsx'
import Manga from './Pages/Manga.jsx'
import Saved from './Pages/Saved.jsx'
function App() {
  return (
    <>
      <Navbar style={{ position: 'fixed', top: 0, width: '100%' }} />
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/news" element={<h1>News</h1>} />
          <Route path="/discover/*" element={<Discover/>}>
            <Route index element={<Top5 />} />
            <Route path="bookmark" element={<Bookmark />} />
            <Route path="favourite" element={<Favourite />} />
          </Route>
          <Route path="/manga/:mangaId" element={<Manga />} />
          <Route path="/search/:title" element={<Search />} />
          <Route path="/user/*" element={<User/>}>
            <Route index element={<Login/>} />
            <Route path="aboutuser" element={<AboutUser />}/>
            <Route path="update" element={<UpdateUser />} />
          </Route>
          <Route path="/create-user" element={<CreateUser/>} />
          <Route path="/mysaved" element={<Saved/>} />
          <Route path="*" element={<h1>Page Not Found</h1>} />
        </Routes>
    </>
  )
}

export default App
