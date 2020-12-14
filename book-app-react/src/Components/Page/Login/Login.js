import React, { Component } from "react";
import AuthService from "../../../Service/AuthService";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import {Typography} from "@material-ui/core";
import ForgetPassword from "./ForgetPassword";

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
            newLog:false,
            redirect:false,
        };
    }

    handleProfileUserName(){
        AuthService.handleUserName(AuthService.getCurrentUser())
            .then(response => response.json());
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
        //this.handleProfileUserName();
        //this.handleCurrentUserId();
    }

    handleLogin() {
        if(this.state.password==="" || this.state.username===""){
            alert("Password and username fields must be filled.")
        }else {
            AuthService.login(this.state.username, this.state.password).then((res) => {
                if (JSON.parse(res).error === "invalid_grant") {
                    alert("\n" +
                        "You entered an incorrect username and password.");
                } else if(JSON.parse(res).error === "unauthorized"){
                   alert("Email confirmation is required.")
                }else{
                    console.log(res)
                    if(res.includes("ROLE_USER")){
                        this.props.history.push("/home");
                        window.location.reload();
                    }else {
                        this.props.history.push("/admin");
                        window.location.reload();
                    }
                }
            }).catch((err)=> {
                alert("Authorization service temporarily is offline for maintenance.")
            })
        }

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
                        onClick={()=> {this.handleTwoMethods()}}
                        style={{marginTop:"5%",marginLeft:"27%"}}
                        variant="outlined">LOGIN</Button>
                    <Button
                        component={Link} to={"/register"}
                        style={{marginTop:"5%",marginLeft:"5%"}}
                        variant="outlined"
                    >Sıgn Up</Button>
                      <ForgetPassword/>
                    </Paper>
                </div>
            </div>
        );
    }
}
