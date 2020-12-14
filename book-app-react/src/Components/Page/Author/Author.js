import React, { Component } from 'react';
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import Paper from "@material-ui/core/Paper";
import Books from "./Books";
import TablePagination from "@material-ui/core/TablePagination";

export default class Author extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            selectedAuthor: [],
            booksOfAuthor: [],
            page:0,
            rowsPerPage:10,
        };
        this.handleSpecificAuthor=this.handleSpecificAuthor.bind(this);
        this.handleBookOfShelves=this.handleBookOfShelves.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleSpecificAuthor(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8085/api/author/"+this.props.location.state.selectedAuthorId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({selectedAuthor:JSON.parse(result)});
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Author service temporarily is offline for maintenance.")
        })
    }

    handleBookOfShelves(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/author/"+this.props.location.state.selectedAuthorId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({booksOfAuthor:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Book service temporarily is offline for maintenance.")
        })
    }

    componentDidMount() {
       this.handleSpecificAuthor();
       this.handleBookOfShelves();
    }

    render() {

        return (
            <div style={{flexGrow: 1}}>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"5%",minWidth:400,maxWidth: 500}}>
                        <Paper>
                            <Typography
                                style={{marginLeft:"35%",marginTop:"5%"}}
                            ><TableCell><div>{this.state.selectedAuthor.name}</div></TableCell></Typography>
                            <img
                                style={{marginLeft:"30%",width:"40%",marginTop:"2%"}}
                                src="https://thumbs.dreamstime.com/b/default-avatar-photo-placeholder-profile-picture-default-avatar-photo-placeholder-profile-picture-eps-file-easy-to-edit-125707135.jpg"
                                alt="book-img"
                                className="book-img-card"
                            />
                            <TableRow >
                                <TableCell><div>Birth Date : {this.state.selectedAuthor.birthDate}</div></TableCell>
                                <TableCell><div>Death Date : {this.state.selectedAuthor.dateOfDeath}</div></TableCell>
                            </TableRow>
                            <Typography>
                                <TableCell><div>Biography : {this.state.selectedAuthor.biography}</div></TableCell>
                            </Typography>
                        </Paper>
                    </Grid>
                    <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                        <Paper style={{marginTop:"3.5%"}}>
                            <Typography
                                style={{marginLeft:"10%",marginTop:"5%"}}
                            >The books of {this.state.selectedAuthor.name}
                            </Typography>
                            {(this.state.rowsPerPage > 0
                                ? this.state.booksOfAuthor.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                : this.state.booksOfAuthor).map((row)=>
                                <TableRow >
                                    <TableCell style={{marginLeft: "2%"}}>Book Name:</TableCell>
                                    <TableCell><div>{row.bookName}</div></TableCell>
                                    <TableCell><div><Link
                                        to={{
                                            pathname: "/book/"+row.id,
                                            state: { selectedBookId:row.id}
                                        }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
                                    </Link></div></TableCell>
                                </TableRow>
                            )}
                            <TablePagination
                                count={this.state.booksOfAuthor.length}
                                page={this.state.page}
                                onChangePage={this.handleChangePage}
                                rowsPerPage={this.state.rowsPerPage}
                                onChangeRowsPerPage={this.handleChangeRowsPerPage}
                            />
                        </Paper>
                    </Grid>
                </Grid>
            </div>
        );
    }
}
