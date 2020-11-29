import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import { Link } from 'react-router-dom';
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";

export default class Comments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: [],
            page:0,
            rowsPerPage:6,
        };
        this.handleDeleteComment=this.handleDeleteComment.bind(this);
        this.handleRefreshComment=this.handleRefreshComment.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleDeleteComment(commentId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/comment/delete/"+commentId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if (result.slice(2, 9) === "message") {
                        console.log(result.slice(2, 10))
                        alert("\n" + JSON.parse(result).message)
                        window.location.reload();
                    } else {
                        alert("Not found")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })


    }

    handleRefreshComment(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");
        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/comment/user", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({comments:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    componentDidMount() {
        this.handleRefreshComment();
    }

    render() {

        if (!this.state.comments) {
            return <div>There is no comment</div>;
        }

        return (
            <div style={{flexGrow: 1}}>
                        <Paper>
                            <Table>
                                <TableHead>
                                    <TableCell>Book Name</TableCell>
                                    <TableCell>Comment</TableCell>
                                </TableHead>
                                {(this.state.rowsPerPage > 0
                                    ? this.state.comments.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                    : this.state.comments).map((row)=>
                                    <TableRow >
                                        <TableCell><div>{row.book.bookname}</div></TableCell>
                                        <TableCell><div>{row.comment}</div></TableCell>
                                        <TableCell>
                                            <Button onClick={()=>this.handleDeleteComment(row.commentId)}
                                                    style={{backgroundColor:"#C0392B",color:"white"}}>Delete</Button>
                                        </TableCell>
                                    </TableRow>
                                )}
                                <TablePagination
                                    count={50}
                                    page={this.state.page}
                                    onChangePage={this.handleChangePage}
                                    rowsPerPage={this.state.rowsPerPage}
                                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                                />
                            </Table>
                        </Paper>
            </div>
        );
    }
}