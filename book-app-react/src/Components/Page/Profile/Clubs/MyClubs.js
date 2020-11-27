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
import MembershipClubs from "./MembershipClubs";
import DialogActions from "@material-ui/core/DialogActions";
import {Typography} from "@material-ui/core";
import InvitePerson from "./InivitePerson";
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



    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };


    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/"+AuthService.getCurrentUserName(), requestOptions)
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
        if(this.state.chosen==="Membership"){
            return(<div>
                <MembershipClubs/>
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
                    <Typography style={{marginLeft:"42%"}}>MY CLUBS</Typography>
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
                                </Link></TableCell>
                                    {row.private.toString()==="true"?
                                        <TableCell><InvitePerson data={row.id}/></TableCell>:
                                        <TableCell><Button
                                            variant={"outlined"}
                                            disabled>Invite </Button></TableCell>}
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
