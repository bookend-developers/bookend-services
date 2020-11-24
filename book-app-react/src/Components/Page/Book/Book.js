import React, { Component } from 'react';
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import {Link, Redirect} from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import Table from "@material-ui/core/Table";
import Paper from "@material-ui/core/Paper";
import Rate from "./Rate";
import Comment from "./Comment";

export default class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            selectedBook: [],
            bookGenre:[]

        };
    }


    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/"+this.props.location.state.selectedBookId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    result=JSON.parse(result);
                    if (result.status !== 500 || result.status !== 400) {
                        this.setState({selectedBook: result});
                        this.setState({bookGenre: result.genre});
                    } else {
                        alert(result.message)
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })

    }

    render() {


        return (
            <div style={{flexGrow: 1}}>
                <Grid container spacing={3}>
                <Grid item xs={12} sm={6} style={{marginLeft:"10%",minWidth:400,maxWidth: 500}}>
                    <Paper>
                    <Typography
                        style={{marginLeft:"30%",marginTop:"5%"}}
                    ><TableCell><div>{this.state.selectedBook.bookName}</div></TableCell></Typography>
                    <img
                        style={{marginLeft:"30%",width:"40%"}}
                        src="https://www.virago.co.uk/wp-content/uploads/2018/07/missingbook.png"
                        alt="book-img"
                        className="book-img-card"
                    />

                        <TableRow >
                            <TableCell><div>Author : {this.state.selectedBook.authorid}</div></TableCell>
                            <TableCell><div>Genre : {this.state.bookGenre.genre}</div></TableCell>
                            <TableCell><div>Page : {this.state.selectedBook.page}</div></TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell><div>ISBN : {this.state.selectedBook.isbn}</div></TableCell>
                            <TableCell><div>Verified : {this.state.selectedBook.verified}</div></TableCell>
                        </TableRow>
                            <Typography>
                                <TableCell><div>{this.state.selectedBook.description}</div></TableCell>
                            </Typography>
                        <Rate data={{bookId:this.state.selectedBook.id,bookName: this.state.selectedBook.bookName}}/>
                    </Paper>
                </Grid>
                <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                    <Paper style={{marginTop:"3.5%"}}>
                    <Typography>
                   <Comment data={{bookId:this.state.selectedBook.id,bookName: this.state.selectedBook.bookName}}/>
                    </Typography>
                    </Paper>
                    </Grid>
                </Grid>
            </div>
        );
    }
}
