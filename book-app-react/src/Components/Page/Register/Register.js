import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import {Typography} from "@material-ui/core";
import ActivationLinkForRegister from "./DialogActivationLink";

export default class Register extends Component {

    constructor(props) {
        super(props);
        this.handleSignUp=this.handleSignUp.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);

        this.state = {
            user_email:"",
            user_name: "",
            user_password: "",
        };
    }

    handleSignUp(username,email,password) {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Basic bW9iaWxlOnBpbg==");
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"username":username,
                "password":password,
                "email":email});

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        return fetch("http://localhost:9191/oauth/sign-up", requestOptions)
            .then(response => response.text())
            .then(result => {
                if(result!=="user registered. need confirmation"){
                    alert("\n" +JSON.parse(result).message)
                }else{
                    alert(result)
                }
            })
    }

    onChangeUsername(e) {
        this.setState({
            user_name: e.target.value
        });
    }

    onChangeEmail(e) {
        this.setState({
            user_email: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            user_password: e.target.value
        });
    }

    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"5%",marginLeft:"35%",width:"30%"}}>
                        <Typography style={{textAlign:"center"}}>REGISTER</Typography>
                        <img
                            style={{marginLeft:"30%",width:"40%"}}
                            src="https://www.pngitem.com/pimgs/m/146-1468479_my-profile-icon-blank-profile-picture-circle-hd.png"
                            alt="profile-img"
                            className="profile-img-card"
                        />
                        <form onSubmit={this.handleSignUp} style={{marginTop:"5%",marginLeft:"25%"}}>
                            <div className="form-group">
                                <TextField
                                    style={{backgroundColor:"white"}}
                                    required
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Username"
                                    value={this.state.user_name}
                                    onChange={this.onChangeUsername}

                                />

                            </div><br/>

                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Email"
                                    value={this.state.user_email}
                                    onChange={this.onChangeEmail}
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
                                    value={this.state.user_password}
                                    onChange={this.onChangePassword}
                                />
                            </div>
                        </form>

                        <Button
                            //component={Link} to={"/Home"}
                            onClick={()=>{
                                if(this.state.user_name!=="" && this.state.user_email!=="" && this.state.user_password!=="")
                            {this.handleSignUp(this.state.user_name,this.state.user_email,this.state.user_password)}
                                else{
                                    alert("All fields must be filled!!")
                                }}}
                            style={{marginTop:"5%",marginLeft:"26%"}}
                            variant="outlined">Sign up</Button>
                        <Button
                            component={Link} to={"/"}
                            style={{marginTop:"5%",marginLeft:"5%"}}
                            variant="outlined"
                        >Sıgn In</Button>
                        <ActivationLinkForRegister/>
                    </Paper>
                </div>
            </div>
        );
    }
}
