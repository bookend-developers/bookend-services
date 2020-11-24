import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import {Typography} from "@material-ui/core";


export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            password: "",
            link:"",
        };
    }

    onChangeLink =(e)=> {
        this.setState({
            username: e.target.value
        });
    }

    onChangePassword =(e) =>{
        this.setState({
            password: e.target.value
        });
    }

    handleResetPassword =()=>{
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({"password":this.state.password});

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://"+this.state.link.trim(), requestOptions)
            .then(response => response.text())
            .then(result => {
                if(JSON.parse(result).error==="Not found"){
                    alert("Not valid token")
                }else{
                    alert(JSON.parse(result).message)
                }
            })

    }

    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"5%",marginLeft:"35%",width:"30%"}}><br/>
                        <Typography style={{textAlign:"center"}}>RESET PASSWORD</Typography>

                        <form onSubmit={this.handleLogin} style={{marginTop:"5%",marginLeft:"25%"}}>
                            <div className="form-group">
                                <TextField
                                    style={{backgroundColor:"white"}}
                                    required
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Link"
                                    value={this.state.link}
                                    onChange={this.onChangeLink}
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
                        <div><Button
                            //component={Link} to={"/Home"}
                            onClick={()=> {this.handleResetPassword()}}
                            style={{marginTop:"5%",marginLeft:"38%"}}
                            variant="outlined">Update</Button></div>
                        <div><Button
                            component={Link} to={"/"}
                            style={{marginTop:"5%",marginLeft:"32%"}}
                            variant="outlined">Back to Login</Button></div><br/>
                    </Paper>
                </div>
            </div>
        );
    }
}
