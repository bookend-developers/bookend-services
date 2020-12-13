import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import TableHead from "@material-ui/core/TableHead";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";


export default class UserProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            page:0,
            rowsPerPage:5,
            userSelected:"",
            userShelves:[]
        };
        this.handleChangePage=this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage=this.handleChangeRowsPerPage.bind(this);
        this.handleUserInfo=this.handleUserInfo.bind(this);
        this.handleUserShelves=this.handleUserShelves.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleUserShelves(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/api/shelf/user/"+this.props.location.state.selectedUser, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({userShelves:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Shelf service temporarily is offline for maintenance.")
        })
    }

    handleUserInfo(){
        let requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("http://localhost:9191/api/profile/full/"+this.props.location.state.selectedUser, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({userSelected:JSON.parse(result)});
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Authorization service temporarily is offline for maintenance.")
        })
    }

    componentDidMount() {
      this.handleUserInfo();
      this.handleUserShelves();

    }

    render() {

        return (
            <div style={{flexGrow: 1}}>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"7%",minWidth:400,maxWidth: 500}}>
                        <Paper>
                    <img
                        style={{marginLeft:"30%",width:"40%",marginTop:"3%"}}
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQe1a5YOLMjJ-tqDQJn-rb998hJRWNX4KTwfg&usqp=CAU"
                        alt="book-img"
                        className="book-img-card"
                    />
                    <TableRow>
                    <TableCell><div>Firstname : {this.state.userSelected.firstname}</div></TableCell>
                    <TableCell><div>Lastname : {this.state.userSelected.lastname}</div></TableCell>
                    </TableRow>
                    <TableRow>
                    <TableCell><div>Username : {this.state.userSelected.username}</div></TableCell>
                    <TableCell><div>Email : {this.state.userSelected.email}</div></TableCell>
                    </TableRow>
                    <TableRow><TableCell><div>About Me : {this.state.userSelected.aboutMe}</div></TableCell></TableRow>
                            <Link
                                to={{
                                    pathname: "/message/to/"+this.state.userSelected.username,
                                    state: { userName:this.state.userSelected.username}
                                }}><Button style={{marginLeft:"37%",marginTop:"3%",backgroundColor:"#C0392B",color:"white"}}>Message</Button>
                            </Link>
                </Paper>
                    </Grid>
                    <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                        <Paper style={{marginTop:"3.5%"}}>
                            <TableCell><Typography style={{marginTop:"3%"}}>The Shelves of {this.state.userSelected.username}</Typography>
                            </TableCell>{this.state.userShelves.map((row)=>
                                <TableRow >
                                    <TableCell style={{marginLeft: "2%"}}><div>Shelf Name: {row.shelfname}</div></TableCell>
                                    <TableCell><div><Link
                                        to={{
                                            pathname: "/user-shelf/"+this.state.userSelected.username+"/"+row.shelfname,
                                            state: { shelfId:row.id,shelfName:row.shelfname}
                                        }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
                                    </Link></div></TableCell>

                                </TableRow>
                            )}
                        </Paper>
                    </Grid>
                </Grid>

            </div>
        );
    }
}

//https://st4.depositphotos.com/1000507/24489/v/450/depositphotos_244890858-stock-illustration-user-profile-picture-isolate-background.jpg