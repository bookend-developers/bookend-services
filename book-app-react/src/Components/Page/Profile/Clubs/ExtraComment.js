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
import Table from "@material-ui/core/Table";
import Paper from "@material-ui/core/Paper";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';

export default class Invitations extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            page: 0,
            rowsPerPage: 5,
            title:"",
            text:"",
            open:false,
        };
        this.handleAddComment = this.handleAddComment.bind(this);
    }

    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsPostId: props.data
        }
    }


    onChangeText = (e) => {
        this.setState({text:e.target.value});
    };

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };



    handleAddComment = (postId) => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "comment":this.state.text,
            "postID":postId,
        });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/post/comment", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    console.log(result)
                    if(JSON.parse(result).message && JSON.parse(result).status !==403){
                        alert("New comment shared")
                        window.location.reload();
                    }else{
                        alert("Sorry, you are not a member")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    render() {

        return (
            <div>
                <td> <Button onClick={this.handleClickOpen} style={{marginLeft:"65%"}}> <AddBoxIcon color="primary"/> </Button></td>
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
                                   value={this.state.text} onChange={this.onChangeText} />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={()=>
                        {if(this.state.text !=="")
                            this.handleAddComment(this.state.propsPostId);
                        else{
                            alert("The comment field must be filled")
                        }}} color="primary">
                            Add
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
