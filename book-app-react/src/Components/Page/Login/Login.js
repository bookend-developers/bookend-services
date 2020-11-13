import React, { Component } from "react";
import AuthService from "../../../Service/AuthService";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import {Typography} from "@material-ui/core";

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.handleLogin=this.handleLogin.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.handleProfileUserName=this.handleProfileUserName.bind(this);
        this.handleCurrentUserId = this.handleCurrentUserId.bind(this);

        this.state = {
            username: "",
            password: "",
            newLog:"",
            redirect:false,
        };
    }

    handleProfileUserName(){
        AuthService.handleUserName(AuthService.getCurrentUser()).then(r => console.log(r));

    }

    handleCurrentUserId(){
        AuthService.handleUserId(AuthService.getCurrentUserName())
            .then((res)=>{
                if (res) {
                    console.log(AuthService.getCurrentUserName())
                }else{
                    alert("\n" +
                        "You entered an incorrect username and password")
                }
            })
    }

    handleTwoMethods(){
        this.handleLogin();
        this.handleProfileUserName();
        this.handleCurrentUserId();
    }

    handleLogin() {
        AuthService.login(this.state.username,this.state.password).then((res)=>{
            if(res.slice(2,7)!=="error") {
                console.log(res)
                this.props.history.push("/Home");
                window.location.reload();
            }else{
                alert("\n" +
                    "You entered an incorrect username and password")
            }
        })
    }

    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"5%",marginLeft:"35%",width:"30%"}}>
                        <Typography style={{textAlign:"center"}}>LOGIN</Typography>
                        <img
                            style={{marginLeft:"30%",width:"40%"}}
                            src="https://www.pngitem.com/pimgs/m/146-1468479_my-profile-icon-blank-profile-picture-circle-hd.png"
                            alt="profile-img"
                            className="profile-img-card"
                        />
                    <form onSubmit={this.handleLogin} style={{marginTop:"5%",marginLeft:"25%"}}>
                        <div className="form-group">
                            <TextField
                                style={{backgroundColor:"white"}}
                                required
                                variant="outlined"
                                id="input-with-icon-textfield"
                                label="Username"
                                value={this.state.username}
                                onChange={this.onChangeUsername}

                            />

                        </div><br/>

                        <div className="form-group">
                            <TextField
                                required
                                style={{backgroundColor:"white"}}
                                variant="outlined"
                                id="standard-password-input"
                                label="Password"
                                type="password"
                                autoComplete="current-password"
                                value={this.state.password}
                                onChange={this.onChangePassword}
                            />
                        </div>
                    </form>
                       <Button
                        //component={Link} to={"/Home"}
                        onClick={()=>this.handleTwoMethods()}
                        style={{marginTop:"5%",marginLeft:"30%"}}
                        variant="outlined">LOGIN</Button>
                    <Button
                        component={Link} to={"/register"}
                        style={{marginTop:"5%",marginLeft:"5%"}}
                        variant="outlined"
                    >Sıgn Up</Button>
                    </Paper>
                </div>
            </div>
        );
    }
}
