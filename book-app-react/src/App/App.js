import React, {Fragment} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import TableCell from '@material-ui/core/TableCell';
import Button from '@material-ui/core/Button';
import {Link, Location, Redirect} from "react-router-dom";
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Menu from '@material-ui/core/Menu';
import AuthService from "../Service/AuthService";


export default class ButtonAppBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userReady:false,
            anchorEl:null,
            open : Boolean(this.anchorEl),
            redirect:false,
            user:""
        };
        this.handleClose=this.handleClose.bind(this);
        this.handleMenu = this.handleMenu.bind(this);
    }


    handleMenu = (event) => {
        this.setState({anchorEl:event.currentTarget});
    };

    handleClose = () => {
        this.setState({anchorEl:null});
    };

    componentDidMount() {
        const currentUser = AuthService.getCurrentUser();
        const currentUserName =AuthService.getCurrentUserName();
        console.log(currentUser)
        if (currentUser===null){ this.setState({ userReady:false });}else{
        this.setState({userReady: true })}
        if(currentUserName!=="undefined"){
            this.setState({user:currentUserName})
        }
    }

    render(){
        console.log(this.state.userReady)
      return (
          <div style={{flexGrow: 1}}>
              <AppBar position="static">
                  <Toolbar>
                      <Typography variant="h6" style={{textAlign:"center"}}>
                      Book Application
                  </Typography>
                      {this.state.userReady ? (
                          <div style={{marginLeft:"85%"}}>

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
                                  id="simple-menu"
                                  anchorEl={this.state.anchorEl}
                                  keepMounted
                                  open={Boolean(this.state.anchorEl)}
                                  onClose={this.handleClose}
                              >
                                  <div style={{marginLeft:"12%",fontWeight:"bold"}}>{AuthService.getCurrentUserName()}</div>
                                  <MenuItem><Button component={Link} to={"/home"}>Home</Button></MenuItem>
                                  <MenuItem><Button component={Link} to={"/profile/"+AuthService.getCurrentUserName()}>Profile</Button></MenuItem>
                              <MenuItem> <Button
                                  component={Link} to={"/"}
                                  onClick={()=>{AuthService.logout();this.setState({userReady:false})}}
                                  >Logout</Button></MenuItem>
                              </Menu>
                          </div>
                      ):null}
                  </Toolbar>
              </AppBar>

          </div>
      );
    }
}

//render={() => <div style={{textAlign:"center"}}>Book Page</div>}

