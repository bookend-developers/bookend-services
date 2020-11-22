import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import {Typography} from "@material-ui/core";
import TableCell from "@material-ui/core/TableCell";
import AuthorList from "./AuthorList";
import AuthService from "../../../../Service/AuthService";


export default class NewBook extends Component {

    constructor(props) {
        super(props);
        this.handleNewBook=this.handleNewBook.bind(this);
        this.onChangeAuthorName = this.onChangeAuthorName.bind(this);
        this.onChangeBookName = this.onChangeBookName.bind(this);
        this.onChangePageNumber = this.onChangePageNumber.bind(this);
        this.onChangeISBN = this.onChangeISBN.bind(this)
        this.onChangeGenre = this.onChangeGenre.bind(this)
        this.onChangeAuthorId = this.onChangeAuthorId.bind(this)


        this.state = {
            book_name:"",
            author_name: "",
            author_id: "",
            genre: "",
            page: "",
            description: "",
            ISBN: "",
        };
    }
    componentDidMount(){
        console.log(this.props.location.state.selectedAuthorName)

    }
    handleNewBook(page,genre,description,bookName,author,author_id,ISBN) {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"page":page,
                "genre":genre,
                "description":description,
                "bookName":bookName,
                "author":author,
                "ISBN":ISBN,
                "authorid":author_id});

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        return fetch("http://localhost:8082/api/book/admin/new", requestOptions)
            .then(response => response.text())
            .then(result => {
                if(JSON.parse(result).bookName===bookName){
                    alert("Added successfully.")
                }else{
                    alert("Something went wrong.")
                }
            })
    }

    onChangeBookName(e) {
        this.setState({
            book_name: e.target.value
        });
    }

    onChangeAuthorName(e) {
        this.setState({
            author_name: e.target.value
        });
    }

    onChangePageNumber(e) {
        this.setState({
            page: e.target.value
        });
    }
    onChangeGenre(e) {
        this.setState({
            genre: e.target.value
        });
    }
    onChangeDescription(e) {
        this.setState({
            description: e.target.value
        });
    }
    onChangeAuthorId(e) {
        this.setState({
            author_id: e.target.value
        });
    }
    onChangeISBN(e) {
        this.setState({
            ISBN: e.target.value
        });
    }


    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"2%",marginLeft:"25%",width:"50%"}}>
                        <Typography style={{textAlign:"center"}}>New Book</Typography>

                        <form onSubmit={this.handleSignUp} style={{marginTop:"5%",marginLeft:"30%"}}>
                            <div className="form-group">
                                <TextField
                                    style={{backgroundColor:"white"}}
                                    required
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="BookName"
                                    value={this.state.book_name}
                                    onChange={this.onChangeBookName}

                                />

                            </div>

                            {this.props.location.state.selectedAuthorName !== "" ?
                            <td><div className="form-group">

                                <TextField
                                    id="standard-read-only-input"
                                    style={{backgroundColor:"white"}}
                                    label="Author"
                                    variant="outlined"
                                    value={this.props.location.state.selectedAuthorName}
                                    defaultValue={"Author"}
                                    InputProps={{
                                        readOnly: true,
                                    }}
                                />
                            </div></td>: <td><div className="form-group">

                                    <TextField
                                        id="standard-read-only-input"
                                        style={{backgroundColor:"white"}}
                                        label="Author"
                                        variant="outlined"
                                        defaultValue={"Author"}

                                        InputProps={{
                                            readOnly: true,
                                        }}
                                    />
                                </div></td>}

                            <td><div ><AuthorList/></div></td><br/>

                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="ISBN13"
                                    value={this.state.ISBN}
                                    onChange={this.onChangeISBN}
                                />
                            </div><br/>
                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Genre"
                                    value={this.state.genre}
                                    onChange={this.onChangeGenre}
                                />
                            </div><br/>
                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Page"
                                    value={this.state.page}
                                    onChange={this.onChangePageNumber}
                                />
                            </div><br/>


                        </form>
                        <Typography
                            style={{marginLeft:"22%"}}>Description:</Typography>
                        <textarea  style={{marginLeft:"22%"}} rows="7" cols="50" value={this.state.description} onChange={this.onChangeDescription} />
                        <Button
                            onClick={()=>{
                                if(this.state.book_name!=="" && this.state.genre!=="" && this.state.page!=="" && this.state.ISBN!=="" && this.state.author!=="" && this.state.description!=="")
                                {this.handleNewBook(this.state.book_name,this.state.genre,this.state.page,this.state.ISBN,this.props.location.state.selectedAuthorName,this.props.location.state.selectedAuthorId,this.state.description)}
                                else{
                                    alert("All fields must be filled!!")
                                }}}
                            style={{marginTop:"5%",marginLeft:"26%"}}
                            variant="outlined">Add</Button>
                        <Button
                            component={Link} to={"/admin"}
                            style={{marginTop:"5%",marginLeft:"5%"}}
                            variant="outlined"
                        >Cancel</Button>

                    </Paper>
                </div>
            </div>
        );
    }
}
