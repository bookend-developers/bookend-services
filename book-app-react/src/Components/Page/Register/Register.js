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
            check_password:""
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

        return fetch("http://localhost:9191/api/oauth/sign-up", requestOptions)
            .then(response => response.text())
            .then(result => {
                if(JSON.parse(result).message==="user registered. need confirmation"){
                    alert("\n" +JSON.parse(result).message)
                }else{
                    alert(JSON.parse(result).message)
                }
            }).catch((err)=> {
                alert("Authorization service temporarily is offline for maintenance.")
            })
    }

    handleControl=()=>{
        if(this.state.user_name!=="" && this.state.user_email!=="" && this.state.user_password!=="" && this.state.check_password!=="") {
            if(!(this.state.user_email.includes(".com",0) && this.state.user_email.includes("@",0))){
                alert("Invalid email");
            }
            if(this.state.user_password.length>=8){
                if(/[A-Z]/.test(this.state.user_password) && /\d/.test(this.state.user_password)){
                    if(this.state.user_password === this.state.check_password){
                        this.handleSignUp(this.state.user_name,this.state.user_email,this.state.user_password)
                    }else{
                        alert("The passwords you entered did not match, please try again..")
                    }
                }else{
                    alert("Your password must be at least 8 characters and also must contains at least one upper letter and one number")
                }
            }else{
                alert("Your password must be at least 8 characters and also must contains at least one upper letter and one number")
            }
        }
        else{
            alert("All fields must be filled!!");
        }

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

    onChangeCheckPassword =(e)=> {
        this.setState({
            check_password: e.target.value
        });
    }

    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"2%",marginLeft:"35%",width:"30%"}}>
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
                            </div><br/>
                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="standard-password-input"
                                    label="Confirm Password"
                                    type="password"
                                    autoComplete="current-password"
                                    value={this.state.check_password}
                                    onChange={this.onChangeCheckPassword}
                                />
                            </div>
                        </form>

                        <Button
                            //component={Link} to={"/Home"}
                            onClick={()=> this.handleControl()}
                            style={{marginTop:"5%",marginLeft:"20%"}}
                            variant="outlined">Sign up</Button>
                        <Button
                            component={Link} to={"/"}
                            style={{marginTop:"5%",marginLeft:"5%"}}
                            variant="outlined"
                        >Back to Sıgn In</Button>
                        <ActivationLinkForRegister/>
                    </Paper>
                </div>
            </div>
        );
    }
}
