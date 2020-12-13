import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import AddBoxIcon from "@material-ui/icons/AddBox";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";

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

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };


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
            }).catch((err)=> {
            alert("Comment service temporarily is offline for maintenance.")
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
            }).catch((err)=> {
            alert("Comment service temporarily is offline for maintenance.")
        })
    }
    componentDidMount() {
        this.showComment();
    }

    render() {

        return (
            <div style={{flexGrow: 1}}>
                <td><Typography style={{marginLeft:"10%"}}>Comments</Typography></td>
                <td> <Button onClick={this.handleClickOpen} style={{marginLeft:"35%"}}> <AddBoxIcon color="primary"/> </Button></td>
                <Dialog
                    fullWidth={"xs"}
                    maxWidth={"xs"}
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <Typography
                            style={{marginLeft:"35%"}}>Adding Comment</Typography><br/>

                        <Typography
                            style={{marginLeft:"5%"}}>Comment</Typography>
                        <textarea  style={{marginLeft:"5%"}} rows="8" cols="45"
                                   value={this.state.comment} onChange={this.onChangeComment} />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={()=>{
                            if(this.state.comment===""){
                                alert("The comment field must be filled")}
                            else{
                                this.giveComment();
                            }}} color="primary">
                            Add
                        </Button>
                        <Button onClick={this.handleClose} color="primary">
                            Close
                        </Button>
                    </DialogActions>
                </Dialog>
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
                    count={this.state.commentData.length}
                    page={this.state.page}
                    onChangePage={this.handleChangePage}
                    rowsPerPage={this.state.rowsPerPage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </div>
        );
    }
}