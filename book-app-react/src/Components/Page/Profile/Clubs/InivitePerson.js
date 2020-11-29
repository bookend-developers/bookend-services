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
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import Input from "@material-ui/core/Input";
import MenuItem from "@material-ui/core/MenuItem";
import Checkbox from "@material-ui/core/Checkbox";
import ListItemText from "@material-ui/core/ListItemText";

export default class InvitePerson extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            user:"",
            open:false
        };

    }
    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsClubId: props.data
        }
    }

    onChangeUser = (event) =>{
        this.setState({user:event.target.value})
    };

    handleInvitePerson(clubId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"clubId":clubId,
                "invitedPersonUserName":this.state.user
            });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/invite-person", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    console.log(result)
                    if(JSON.parse(result).message==="request sent successfully" && JSON.parse(result).status !==400){
                        alert("The invitation is sent")
                        window.location.reload();
                    }else{
                        alert("You must not sent an invitation to same person or member does not exist ")
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
                <td> <Button onClick={this.handleClickOpen} style={{marginLeft:"17%",backgroundColor:"#AF7AC5",color:"white"}}> Invite</Button></td>
                <Dialog
                    fullWidth={"xs"}
                    maxWidth={"xs"}
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <Typography
                            style={{marginLeft:"35%"}}>Invite User</Typography><br/>

                        <form noValidate autoComplete="off" style={{marginLeft:"25%",width:"50%"}}>
                            <TextField
                                style={{backgroundColor:"white"}}
                                id="standard-basic"
                                label="Username"
                                value={this.state.user}
                                onChange={this.onChangeUser}
                            />
                        </form>
                        <br/>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={()=>this.handleInvitePerson(this.state.propsClubId)} color="primary">
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