import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import TableHead from "@material-ui/core/TableHead";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import LockIcon from '@material-ui/icons/Lock';

export default class ClubAndPost extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page:0,
            rowsPerPage:5,

        };
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

    handleJoin = (clubId) => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"clubId":clubId,
                "memberId":AuthService.getCurrentUserId()
            });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/new-member", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    console.log(result)
                    if(JSON.parse(result).message==="member added succesfully" && JSON.parse(result).status !==500){
                        alert("You are joined to the club")
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
            console.log(AuthService.getCurrentUserId())

    }

    render() {

        return (
            <div style={{flexGrow: 1}}>
                <Table style={{marginLeft:"42%",width:"20%",marginTop:"1%"}}>
                    <td> <Button
                        component={ Link } to="/home"
                        style={{backgroundColor:"#E5E7E9"}}
                    >Home</Button></td>
                    <td> <Button
                        component={ Link } to={"/profile/"+AuthService.getCurrentUserName()}
                        style={{backgroundColor:"#E5E7E9"}}
                    >Profile</Button></td>
                </Table>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"2%",minWidth:400,maxWidth: 500}}>
                        <Paper>
                            <Typography style={{textAlign:"center"}}>Club Name : {this.props.location.state.selectedClubName}</Typography>
                            <img
                                style={{marginLeft:"30%",width:"40%",marginTop:"3%"}}
                                src="https://ajsmithactionresearch.files.wordpress.com/2014/08/participants.jpg"
                                alt="book-img"
                                className="book-img-card"
                            />
                            <Table>
                            <TableRow>
                                <Typography style={{textAlign:"center"}}>Owner: {this.props.location.state.selectedOwner}</Typography><br/>
                            </TableRow>
                            <TableRow>{Boolean(this.props.location.state.private).toString() === "false" ?
                                <Typography style={{textAlign:"center"}}>
                                    <Button
                                        onClick={()=>{console.log(this.props.location.state.selectedClubId);
                                            this.handleJoin(this.props.location.state.selectedClubId)}}
                                        style={{
                                            backgroundColor: "#C0392B",
                                            color: "white",
                                        }}>Join</Button>
                                </Typography>:
                                <Typography style={{textAlign:"center"}}>
                                    <LockIcon/>
                                </Typography>
                            } </TableRow>
                            </Table>
                            <Typography>
                                <TableCell><Typography>Description: {this.props.location.state.selectedClubDescription}</Typography></TableCell>
                            </Typography>

                        </Paper>
                    </Grid>
                    <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                        <Paper style={{marginTop:"3.5%"}}>

                        </Paper>
                    </Grid>
                </Grid>

            </div>
        );
    }
}

