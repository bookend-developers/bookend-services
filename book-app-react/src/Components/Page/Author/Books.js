import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import { Link } from 'react-router-dom';
import PopOverShelf from "../Home/PopOverShelf";
import TablePagination from "@material-ui/core/TablePagination";

export default class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            booksOfAuthor: [],
            page:0,
            rowsPerPage:10,
        };
    }

    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsAuthorId: props.data
        }
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/author/"+this.state.propsAuthorId, requestOptions)
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

    render() {

        if (!this.state.booksOfAuthor) {
            return <div>There is no book</div>;
        }

        return (
            <div style={{flexGrow: 1}}>
                    <Typography
                        style={{marginLeft:"10%",marginTop:"5%"}}
                    >The books of
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
                    <br/>
            </div>
        );
    }
}