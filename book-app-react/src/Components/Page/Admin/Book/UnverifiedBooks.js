import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import TableHead from "@material-ui/core/TableHead";
import AuthService from "../../../../Service/AuthService";
import Table from "@material-ui/core/Table";

export default class UnverifiedBooks extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            page:0,
            rowsPerPage:5,
            unverifiedBooks: [],

        };
        this.handleChangePage=this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage=this.handleChangeRowsPerPage.bind(this);
        this.handleDeleteBook=this.handleDeleteBook.bind(this);
        this.handleVerifyBook=this.handleVerifyBook.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleDeleteBook(bookId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/admin/"+bookId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(result===""){
                        alert("Deleted successfully")
                        window.location.reload();
                    }
                    else {
                        alert("There is a problem")
                    }
                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
            .catch(error => console.log('error', error));
    }
    handleVerifyBook(bookId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/admin/verify/"+bookId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(JSON.parse(result).status !==401 && JSON.parse(result).status!==500){
                        alert("Verified successfully")
                        window.location.reload();
                    }
                    else {
                        alert("There is a problem")
                    }
                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
            .catch(error => console.log('error', error));
    }

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/admin/unverified", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token" && result) {
                    this.setState({unverifiedBooks:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })

    }

    render() {

        if (!this.state.unverifiedBooks) {
            return <div>There is no unverified book.</div>;
        }


        return (
            <div style={{flexGrow: 1}}>
                <Paper style={{marginLeft:"33%",marginTop:"2%",minWidth:400,maxWidth: 500}}>
                    <TableHead>
                        <TableCell>Book Name</TableCell>
                        <TableCell>Genre</TableCell>
                    </TableHead>
                    {(this.state.rowsPerPage > 0
                        ? this.state.unverifiedBooks.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.unverifiedBooks).map((row)=>
                        <TableRow >
                            <TableCell><div>{row.bookName}</div></TableCell>
                            <TableCell><div>{row.genre.genre}</div></TableCell>
                            <TableCell><div><Link
                                to={{
                                    pathname: "/book/"+row.id,
                                    state: { selectedBookId:row.id}
                                }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
                            </Link></div></TableCell>
                            <TableCell><div><Button
                                onClick={()=>this.handleVerifyBook(row.id)}
                                style={{backgroundColor:"#148F77",color:"white"}}>Verify</Button>
                            </div></TableCell>
                            <TableCell><div><Button
                                onClick={()=>this.handleDeleteBook(row.id)}
                                style={{backgroundColor: "#C0392B", color: "white"}}>Delete</Button>
                            </div></TableCell>

                        </TableRow>
                    )}
                    <TablePagination
                        count={50}
                        page={this.state.page}
                        onChangePage={this.handleChangePage}
                        rowsPerPage={this.state.rowsPerPage}
                        onChangeRowsPerPage={this.handleChangeRowsPerPage}
                    />
                </Paper>
            </div>
        );
    }
}