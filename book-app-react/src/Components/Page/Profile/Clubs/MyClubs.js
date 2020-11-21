import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import { Link } from 'react-router-dom';
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import AddBoxIcon from '@material-ui/icons/AddBox';
import AllClubs from "./AllClubs";
import AddClub from "./AddClub";
import Invitations from "./Invitations";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import UserShelves from "../Shelves/Shelves";
import DialogActions from "@material-ui/core/DialogActions";
export default class MyClubs extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            my_clubs: [],
            page:0,
            rowsPerPage:6,
            chosen:"",
            userName:"",
            open:false,
        };

    }

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    onChangeUserName = (event) =>{
        this.setState({userName:event.target.value})
    };

    handleInvitePerson(clubId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"clubId":clubId,
                  "invitedPersonUserName":this.state.userName
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
                    if(JSON.parse(result).message==="request sended successfully" && JSON.parse(result).status !==500){
                        alert("The invitation is sent")
                    }else{
                        alert("Sorry, there is a problem. Try again..")
                    }

                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }


    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/"+AuthService.getCurrentUserId(), requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({my_clubs:JSON.parse(result)});
                    console.log(AuthService.getCurrentUserId())
                    console.log(this.state.my_clubs)
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    render() {
        if (!this.state.my_clubs) {
            return <div>didn't get a club</div>;
        }
        if(this.state.chosen==="All"){
            return(<div>
                <AllClubs/>
            </div>)
        }

        return (
            <div style={{flexGrow: 1}}>
                <Table style={{marginLeft:"32%",width:"35%",marginTop:"2%"}}>
                    <td> <Button
                        onClick={()=>this.setState({chosen:"All"})}
                        style={{backgroundColor:"#E5E7E9"}}
                    >All Clubs</Button></td>
                    <td> <Button
                        onClick={()=>this.setState({chosen:"Membership"})}
                        style={{marginLeft:"13%",backgroundColor:"#E5E7E9"}}
                    >Membership Clubs</Button></td>
                    <td><Invitations/></td>
                    <td><AddClub/></td>
                </Table>
                <Paper style={{marginLeft:"25%",width:"50%",marginTop:"1%"}}>
                    <Table >
                        <TableHead>
                            <TableCell>Club Name</TableCell>
                            <TableCell>Owner Username</TableCell>
                            <TableCell>Private</TableCell>

                        </TableHead>
                        {(this.state.rowsPerPage > 0
                            ? this.state.my_clubs.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                            : this.state.my_clubs).map((row)=>
                            <TableRow >
                                <TableCell><div>{row.clubName}</div></TableCell>
                                <TableCell><div>{row.owner.userName}</div></TableCell>
                                <TableCell><div>
                                    {Boolean(row.private).toString()==="true"?"Yes":"No"}</div></TableCell>
                                <TableCell><Link
                                    to={{
                                        pathname: "/club/"+row.id,
                                        state: { selectedClubId:row.id,
                                            selectedClubName: row.clubName,
                                            selectedOwner: row.owner.userName,
                                            private: row.private,
                                            selectedClubDescription: row.description}
                                    }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
                                </Link>

                                <Button
                                    style={{marginLeft:"10%",backgroundColor:"#9B59B6", color:"white"}}
                                    onClick={this.handleClickOpen}>Invite</Button>
                                <Dialog
                                    fullWidth={"xs"}
                                    maxWidth={"xs"}
                                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                                    <DialogContent>
                                        <TableCell><form noValidate autoComplete="off">
                                            <TextField
                                                style={{backgroundColor:"white"}}
                                                id="standard-basic"
                                                label="User Name"
                                                value={this.state.userName}
                                                onChange={this.onChangeUserName}
                                            />
                                        </form></TableCell>
                                    </DialogContent>
                                    <DialogActions>
                                        <Button onClick={()=>this.handleInvitePerson(row.id)} color="primary">
                                            Send
                                        </Button>
                                        <Button onClick={this.handleClose} color="primary">
                                            Close
                                        </Button>
                                    </DialogActions>
                                </Dialog></TableCell>
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

/* {Boolean(row.private).toString() === "true" ?
                                    <TableCell>
                                        <Button
                                            onClick={()=>this.handleJoin(row.id)}
                                            style={{
                                                backgroundColor: "#C0392B",
                                                color: "white",
                                            }}>Join</Button>
                                    </TableCell>:
                                    <TableCell>
                                        <Button variant="contained" disabled>Join</Button>
                                    </TableCell>
                                } */