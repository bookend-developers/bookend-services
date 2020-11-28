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

export default class NewGenre extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            new_genre: "",
            open: false,
        };
    }


    handleClickOpen = () => {
        this.setState({open: true});
    };
    onChangeNewGenre = (e) => {
        this.setState({new_genre: e.target.value})
    }



    handleClose = () => {
        this.setState({open: false});
    };


    handleGenre = () => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/admin/new/genre?genre="+this.state.new_genre, requestOptions)
            .then(response => response.text())
            .then(result => {
                if(JSON.parse(result).genre === this.state.new_genre){
                    alert("Added successfully")
                    window.location.reload();
                }else{
                    alert(JSON.parse(result).message)
                }
            })
    }




    render() {


        return (
            <div>
                <td><Typography style={{marginLeft:"10%"}}>Genres</Typography></td>
                <td> <Button onClick={this.handleClickOpen} style={{marginLeft:"35%"}}> <AddBoxIcon color="primary"/> </Button></td>
                <Dialog
                    fullWidth={"xs"}
                    maxWidth={"xs"}
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <TableCell><form noValidate autoComplete="off">
                            <TextField
                                style={{backgroundColor:"white"}}
                                id="standard-basic"
                                label="New Genre"
                                value={this.state.new_genre}
                                onChange={this.onChangeNewGenre}
                            />
                        </form></TableCell>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={()=>this.handleGenre()} color="primary">
                            Send
                        </Button>
                        <Button onClick={this.handleClose} color="primary">
                            Close
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}