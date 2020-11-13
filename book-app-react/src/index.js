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

ReactDOM.render(
  <React.StrictMode>
      <BrowserRouter>
          <Router>
              <App/>
              <Route exact path="/" component={Login} />
              <Route path="/home" component={Home} />
              <Route path="/profile" component={Profile} />
              <Route path="/register" component={Register} />
              <Route path="/shelf" component={Books} />
              <Route path="/book" component={Book} />
              <Route path="/author" component={Author} />
              <Route path="/user" component={UserProfile} />
              <Route path="/user-shelf" component={AnotherUserBooks} />
              <Route path="/message" component={MessageWithUser} />
              <Route path="/inbox/sent/message" component={MessageInbox} />
              <Route path="/rate/comments" component={RateAndComments} />
              <Route path="/all-clubs" component={AllClubs} />
              <Route path="/club" component={ClubAndPost} />
          </Router>
      </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
