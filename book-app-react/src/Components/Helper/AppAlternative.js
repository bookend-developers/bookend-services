import React, {Fragment} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Menu from '@material-ui/core/Menu';
import Profile from '../Components/Page/Profile/Profile.js';
import Home from '../Components/Page/Home/Home.js';
import Login from '../Components/Page/Login/Login';
import Books from "../Components/Page/Shelf/Books.js";
import Register from "../Components/Page/Register/Register";
import Book from "../Components/Page/Book/Book";
import Author from "../Components/Page/Author/Author";
import UserProfile from "../Components/Page/AnotherUserProfile/UserProfile";
import MessageWithUser from "../Components/Page/AnotherUserProfile/Chat";
import MessageInbox from "../Components/Page/Profile/Messages/MessageInbox";
import RateAndComments from "../Components/Page/Profile/RateAndComment/Rate";
import AnotherUserBooks from "../Components/Page/Shelf/AnotherUserBooks";


export default class ButtonAppBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            auth:false,
            anchorEl:null,
            open : Boolean(this.anchorEl)
        };
    }

    handleChange = (event) => {
        this.setState({auth:event.target.checked});
    };

    handleMenu = (event) => {
        this.setState({anchorEl:event.currentTarget});
    };

    handleClose = () => {
        this.setState({anchorEl:null});
    };
    render(){
        return (
            <div style={{flexGrow: 1}}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" >
                            Book Application
                        </Typography>
                        {this.props.location.state.appearance_button && (
                            <div>
                                <IconButton
                                    aria-label="account of current user"
                                    aria-controls="menu-appbar"
                                    aria-haspopup="true"
                                    onClick={this.handleMenu}
                                    color="inherit"
                                >
                                    <AccountCircle />
                                </IconButton>
                                <Menu
                                    id="menu-appbar"
                                    anchorEl={this.state.anchorEl}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    open={this.state.open}
                                    onClose={this.handleClose}
                                >
                                    <MenuItem onClick={this.handleClose}>Profile</MenuItem>
                                    <MenuItem onClick={this.handleClose}>My account</MenuItem>
                                </Menu>
                            </div>
                        )}
                    </Toolbar>
                </AppBar>
                <Router>
                    <Route exact path="/login" component={Login} />
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
                </Router>
            </div>
        );
    }
}

//render={() => <div style={{textAlign:"center"}}>Book Page</div>}

