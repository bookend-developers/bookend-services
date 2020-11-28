import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import {Typography} from "@material-ui/core";
import TableCell from "@material-ui/core/TableCell";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import TableRow from "@material-ui/core/TableRow";
import TablePagination from "@material-ui/core/TablePagination";
import DialogActions from "@material-ui/core/DialogActions";
import AuthService from "../../../../Service/AuthService";


export default class Member extends Component {

    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            rowsPerPage: 5,
            members: [],
            open: false,
        };
    }

    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsClubId: props.data
        }
    }

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };


    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage: parseInt(event.target.value, 10)})
        this.setState({page: 0});
    };

    loadAuthors = () => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " +AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/"+this.state.propsClubId+"/members", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(JSON.parse(result).status !== 500){
                        this.setState({members: JSON.parse(result)})
                    }
                    else{
                        alert("Something went wrong.")
                    }

                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }
    componentDidMount(){
        this.loadAuthors();
    }

    render() {

        return (
            <div ><TableCell><Button
                onClick={this.handleClickOpen}
                style={{marginLeft: "17%", backgroundColor: "#E5E7E9"}}
            >Members</Button></TableCell>
                <Dialog
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <Typography
                            style={{marginLeft: "35%"}}>Members</Typography>
                        <Paper>
                            {(this.state.rowsPerPage > 0
                                ? this.state.members.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                : this.state.members).map((row)=>
                                <TableRow>
                                    <TableCell><div><Link
                                        to={{
                                            pathname: "/user/"+row.userName,
                                            state: { selectedUser:row.userName}
                                        }}>{row.userName}
                                    </Link></div></TableCell>
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
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            Close
                        </Button>
                    </DialogActions>
                </Dialog></div>
        );
    }
}
