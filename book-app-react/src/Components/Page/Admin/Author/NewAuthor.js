import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import {Typography} from "@material-ui/core";
import AuthService from "../../../../Service/AuthService";

export default class NewAuthor extends Component {

    constructor(props) {
        super(props);
        this.handleNewAuthor=this.handleNewAuthor.bind(this);
        this.onChangeAuthorName = this.onChangeAuthorName.bind(this);
        this.onChangeBiography = this.onChangeBiography.bind(this);
        this.onChangeBirthDate = this.onChangeBirthDate.bind(this);
        this.onChangeDateOfDeath = this.onChangeDateOfDeath.bind(this)


        this.state = {
            author_name:"",
            biography: "",
            birthDate: "",
            dateOfDeath: "",

        };
    }

    handleNewAuthor(author_name,birthDate,biography,dateOfdate) {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"name":author_name,
                "biography":biography,
                "birthDate":birthDate,
                "dateOfDeath":dateOfdate,
            });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        return fetch("http://localhost:8085/api/author/admin/new", requestOptions)
            .then(response => response.text())
            .then(result => {
                if(JSON.parse(result).name === author_name){
                    alert("Added successfully.")
                }else{
                    alert("Author already exists.")
                }
            })
    }



    onChangeAuthorName(e) {
        this.setState({
            author_name: e.target.value
        });
    }

    onChangeBirthDate(e) {
        this.setState({
            birthDate: e.target.value
        });
    }
    onChangeDateOfDeath(e) {
        this.setState({
            dateOfDeath: e.target.value
        });
    }
    onChangeBiography(e) {
        this.setState({
            biography: e.target.value
        });
    }



    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"2%",marginLeft:"25%",width:"50%"}}>
                        <Typography style={{textAlign:"center"}}>New Author</Typography>

                        <form onSubmit={this.handleSignUp} style={{marginTop:"5%",marginLeft:"30%"}}>
                            <div className="form-group">
                                <TextField
                                    style={{backgroundColor:"white"}}
                                    required
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Author Name"
                                    value={this.state.author_name}
                                    onChange={this.onChangeAuthorName}

                                />

                            </div><br/>



                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    type= "date"
                                    id="date"

                                    label="BirthDate"
                                    value={this.state.birthDate}
                                    onChange={this.onChangeBirthDate}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </div><br/>
                            <div className="form-group">
                                <TextField
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="date"
                                    type= "date"
                                    label="Date Of Death"
                                    value={this.state.dateOfDeath}
                                    onChange={this.onChangeDateOfDeath}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </div><br/>


                        </form>
                        <Typography
                            style={{marginLeft:"22%"}}>Biography:</Typography>
                        <textarea  style={{marginLeft:"22%"}} rows="7" cols="50" value={this.state.biography} onChange={this.onChangeBiography} />
                        <Button
                            onClick={()=>{
                                if(this.state.author_name!=="" && this.state.birthDate!=="" && this.state.biography!=="" )
                                {this.handleNewAuthor(this.state.author_name,this.state.birthDate,this.state.biography,this.state.dateOfDeath)}
                                else{
                                    alert("All fields must be filled!!")
                                }}}
                            style={{marginTop:"5%",marginLeft:"33%",backgroundColor:"#148F77",color:"white"}}
                            variant="outlined">Add</Button>
                        <Button
                            component={Link} to={"/admin"}
                            style={{marginTop:"5%",marginLeft:"5%",backgroundColor:"#C0392B",color:"white"}}
                            variant="outlined"
                        >Cancel</Button>

                    </Paper>
                </div>
            </div>
        );
    }
}