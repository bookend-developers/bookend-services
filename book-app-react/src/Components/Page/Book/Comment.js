import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import TableHead from "@material-ui/core/TableHead";
import Box from "@material-ui/core/Box";
import TextField from "@material-ui/core/TextField";

export default class Comment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            bookId:"",
            bookName:"",
            commentData:[],
            comment:"",
            page:0,
            rowsPerPage:6,
        };
        this.showComment=this.showComment.bind(this);
        this.onChangeComment = this.onChangeComment.bind(this);
        this.giveComment=this.giveComment.bind(this);
        this.handleChangePage=this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage=this.handleChangeRowsPerPage.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    static getDerivedStateFromProps(props, state) {
        console.log(props.data)
        console.warn("hook",props,state)
        return {
            bookId: props.data.bookId,
            bookName: props.data.bookName
        }
    }

    onChangeComment(e){
        this.setState({
            comment: e.target.value
        });
    }

    giveComment(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "bookname":this.state.bookName,
            "bookID":this.state.bookId,
            "comment":this.state.comment
        });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'

        }

        fetch("http://localhost:8081/api/comment/new/", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if (JSON.parse(result).status !== 400 || JSON.parse(result).status !== 401 || JSON.parse(result).status !== 404) {
                        alert("The comment is added successfully")
                        window.location.reload()
                    } else {
                        alert(JSON.parse(result).message)
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    showComment(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/comment/"+this.state.bookId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({commentData:JSON.parse(result)});
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }
    componentDidMount() {
        this.showComment();
    }

    render() {

        return (
            <div style={{flexGrow: 1}}>
                <TableRow>
                    <TableCell> <Typography
                        style={{marginTop:"5%"}}
                    >Comments</Typography></TableCell>
                    <TableCell><form noValidate autoComplete="off">
                        <TextField
                            style={{backgroundColor:"white"}}
                            id="standard-basic"
                            label="Comment"
                            value={this.state.comment}
                            onChange={this.onChangeComment}
                        />
                    </form></TableCell>
                    <TableCell><Button onClick={this.giveComment} style={{backgroundColor:"#E6B0AA"}}>Add Comment</Button></TableCell>
                </TableRow>
                {(this.state.rowsPerPage > 0
                    ? this.state.commentData.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                    : this.state.commentData).map((row)=>
                    <TableRow >
                        <TableCell><div>Username:<Link  to={{
                            pathname: "/user/"+row.username,
                            state: { selectedUser:row.username}
                        }}> {row.username}</Link></div></TableCell>
                        <TableCell><div>Comment: {row.comment}</div></TableCell>

                    </TableRow>
                )}
                <TablePagination
                    count={50}
                    page={this.state.page}
                    onChangePage={this.handleChangePage}
                    rowsPerPage={this.state.rowsPerPage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </div>
        );
    }
}