import React, {useEffect} from 'react';
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import TextField from "@material-ui/core/TextField";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import Radio from '@material-ui/core/Radio';
import AddBoxIcon from "@material-ui/icons/AddBox";
import {Link} from "react-router-dom";
import TablePagination from "@material-ui/core/TablePagination";
import Paper from "@material-ui/core/Paper";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';

export default class AuthorList  extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            rowsPerPage: 5,
            authors: [],
            open: false,


        };

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


    componentDidMount() {
        console.log(AuthService.getCurrentUserId())
        this.loadAuthors();
    }


    loadAuthors = () => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " + AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8085/api/author", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(JSON.parse(result).status !== 500){
                        this.setState({authors: JSON.parse(result)})
                        console.log(this.state.authors)
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




    render() {

        return (
            <div>
                <td><Button
                    onClick={this.handleClickOpen}
                    style={{marginLeft: "17%",marginTop: "35%", backgroundColor: "#E5E7E9"}}
                >Authors</Button></td>
                <Dialog

                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <Typography
                            style={{marginLeft: "35%"}}>Authors</Typography>
                        <Paper>
                            {(this.state.rowsPerPage > 0
                                ? this.state.authors.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                : this.state.authors).map((row)=>
                                <TableRow>
                                    <TableCell><div><Link
                                        to={{
                                            pathname: "/admin-book-new",
                                            state: { selectedAuthorId:row.id
                                                ,selectedAuthorName:row.name }
                                        }}>{row.name}
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
                </Dialog>
            </div>
        );
    }
}
