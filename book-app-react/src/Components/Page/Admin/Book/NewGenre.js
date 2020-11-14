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
            genre: "",
            open: false,


        };

    }


    handleClickOpen = () => {
        this.setState({open: true});
    };
    onChangeGenre = (e) => {
        this.setState({genre: e.target.value})
    }



    handleClose = () => {
        this.setState({open: false});
    };





    componentDidMount() {
        console.log(AuthService.getCurrentUserId())
        this.handleGenre();
    }


    handleGenre = (genre) => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");



        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
        };

       fetch("http://localhost:8082/api/book/admin/new/genre?genre="+this.state.genre, requestOptions)
            .then(response => response.text())
            .then(result => {
                if(result!==""){
                    alert("\n" +JSON.parse(result).message)
                }else{
                    alert(result)
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
                            style={{marginLeft: "35%"}}>New Genre</Typography>
                        <Paper>


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
