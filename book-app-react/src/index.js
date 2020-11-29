import React, {Fragment} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from "./App/App.js";
import * as serviceWorker from './Test/serviceWorker';
import {BrowserRouter as Router, BrowserRouter, Route} from "react-router-dom";
import Login from "./Components/Page/Login/Login";
import Home from "./Components/Page/Home/Home";
import Profile from "./Components/Page/Profile/Profile";
import Register from "./Components/Page/Register/Register";
import Books from "./Components/Page/Shelf/Books";
import Book from "./Components/Page/Book/Book";
import Author from "./Components/Page/Author/Author";
import UserProfile from "./Components/Page/AnotherUserProfile/UserProfile";
import AnotherUserBooks from "./Components/Page/Shelf/AnotherUserBooks";
import MessageWithUser from "./Components/Page/AnotherUserProfile/Chat";
import MessageInbox from "./Components/Page/Profile/Messages/MessageInbox";
import RateAndComments from "./Components/Page/Profile/RateAndComment/Rate";
import AllClubs from "./Components/Page/Profile/Clubs/AllClubs";
import ClubAndPost from "./Components/Page/Profile/Clubs/ClubAndPost";
import NewBook from "./Components/Page/Admin/Book/NewBook";
import Genres from "./Components/Page/Admin/Genre/Genres";
import UnverifiedBooks from "./Components/Page/Admin/Book/UnverifiedBooks";
import NewAuthor from "./Components/Page/Admin/Author/NewAuthor";
import AdminPage from "./Components/Page/Admin/AdminPage";
import Shelves from "./Components/Page/Profile/Shelves/Shelves";
import Post from "./Components/Page/Profile/Clubs/Post";
import Reset from "./Components/Page/ResetPassword/Reset";
import UserNewBook from "./Components/Page/Profile/NewBook/UserNewBook";

ReactDOM.render(
  <React.StrictMode>
      <BrowserRouter>
          <Router>
              <App/>
              <Route exact path="/" component={Login} />
              <Route path="/home" component={Home} />
              <Route path="/admin" component={AdminPage} />
              <Route path="/profile" component={Profile} />
              <Route path="/register" component={Register} />
              <Route path="/shelf" component={Books} />
              <Route path="/book" component={Book} />
              <Route path="/author" component={Author} />
              <Route path="/user" component={UserProfile} />
              <Route path="/shelves" component={Shelves} />
              <Route path="/user-shelf" component={AnotherUserBooks} />
              <Route path="/message" component={MessageWithUser} />
              <Route path="/inbox/sent/message" component={MessageInbox} />
              <Route path="/rate/comments" component={RateAndComments} />
              <Route path="/all-clubs" component={AllClubs} />
              <Route path="/admin-book-new" component={NewBook} />
              <Route path="/user-book-new" component={UserNewBook} />
              <Route path="/admin-unverified" component={UnverifiedBooks} />
              <Route path="/admin-author-new" component={NewAuthor} />
              <Route path="/admin-genres" component={Genres} />
              <Route path="/club" component={ClubAndPost} />
              <Route path="/club-post" component={Post} />
              <Route path="/reset-password" component={Reset} />
          </Router>
      </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
