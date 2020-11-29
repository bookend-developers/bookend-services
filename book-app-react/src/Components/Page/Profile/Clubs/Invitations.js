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
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';

export default class Invitations extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            page: 0,
            rowsPerPage: 5,
            invitations: [],
            open: false,
            answer:"",
            chosen:false,
            value:""

        };
        this.replyInvitation = this.replyInvitation.bind(this);
    }


    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleChange = (event) => {
        this.setState({answer: event.target.value});
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
        this.loadInvitationData();
    }


    loadInvitationData = () => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " + AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/"+AuthService.getCurrentUserName()+"/invitations", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {

                    this.setState({invitations: JSON.parse(result)})
                    console.log(this.state.invitations)
                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }


    replyInvitation = (invitationId, answer) => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " + AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "invitationId": invitationId,
            "eInvitationReply": answer
        });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        console.log(invitationId)
        console.log(this.state.answer)
        fetch("http://localhost:8089/api/club/reply-invitation", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    console.log(result)
                    if(JSON.parse(result).message==="request sent successfully" && JSON.parse(result).status !==500){
                        alert("Your answer is sent")
                        window.location.reload();
                    }else{
                        alert("Sorry, there is a problem. Try again..")
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
                <td><Button
                    onClick={this.handleClickOpen}
                    style={{marginLeft: "17%", backgroundColor: "#E5E7E9"}}
                >Invitations</Button></td>
                <Dialog
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <Typography
                            style={{marginLeft: "35%"}}>Invitations</Typography>
                        <Paper>
                            {(this.state.rowsPerPage > 0
                                ? this.state.invitations.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                : this.state.invitations).map((row)=>
                                <TableRow>
                                    <TableCell>
                                        <div>Club Name:{row.club.clubName}</div>
                                    </TableCell>
                                    <TableCell>
                                        <div>Owner: {row.club.owner.userName}</div>
                                    </TableCell>
                                    <TableRow><TableCell>
                                    <RadioGroup row aria-label="position" name="position" defaultValue="top">
                                       <FormControlLabel
                                            value="ACCEPT"
                                            onChange={()=>this.setState({answer:"ACCEPT"})}
                                            control={<Radio color="primary" />}
                                            label="Accept"
                                            labelPlacement="Accept"
                                        />
                                        <FormControlLabel
                                            value="REJECT"
                                            onChange={()=>this.setState({answer:"REJECT"})}
                                            control={<Radio color="primary" />}
                                            label="Reject"
                                            labelPlacement="Reject"
                                        />
                                    </RadioGroup></TableCell></TableRow>
                                    <TableCell>
                                        <Button
                                            style={{backgroundColor:"#117A65",color:"white"}}
                                            onClick={()=>this.replyInvitation(row.id,this.state.answer)}>Send</Button>
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
