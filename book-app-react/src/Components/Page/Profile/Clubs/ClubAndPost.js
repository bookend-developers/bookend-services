import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import Grid from "@material-ui/core/Grid";
import Table from "@material-ui/core/Table";
import LockIcon from '@material-ui/icons/Lock';
import AddPost from "./AddPost";
import Members from "./Members";

export default class ClubAndPost extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page:0,
            rowsPerPage:5,
            posts:[]
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

    handleCurrentUserId(){
        AuthService.handleUserId(AuthService.getCurrentUserName())
            .then((res)=>{
                if (res) {
                    console.log(AuthService.getCurrentUserName())
                }else{
                    alert("\n" +
                        "You entered an incorrect username and password")
                }
            })
    }

    handleJoin = (clubId) => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"clubId":clubId,
                "memberId":AuthService.getCurrentUserId()
            });

        console.log(AuthService.getCurrentUserId())
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
                    if(JSON.parse(result).message==="member added successfully" && JSON.parse(result).status !==400){
                        alert("You are joined to the club")
                    }else{
                        alert("You are already member")
                    }

                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    componentDidMount() {
        this.handleCurrentUserId();
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/"+ this.props.location.state.selectedClubId+"/posts", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({posts:JSON.parse(result)});
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })

    }

    render() {
            if(!this.props.location.state.selectedClubId){
                return <div>There is no selected club</div>
            }
        return (
            <div style={{flexGrow: 1}}>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"7%",minWidth:400,maxWidth: 500}}>
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
                                <Table style={{width:"20%",marginLeft:"25%"}}>
                                <TableRow>
                                   <TableCell> <Button
                                        onClick={()=>{console.log(this.props.location.state.selectedClubId);
                                            this.handleJoin(this.props.location.state.selectedClubId)}}
                                        style={{
                                            backgroundColor: "#C0392B",
                                            color: "white",
                                        }}>Join</Button></TableCell>
                                 <Members data={this.props.location.state.selectedClubId}/>
                               </TableRow></Table> :
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
                            <td> <Typography
                                style={{marginLeft: "35%"}}>Posts</Typography></td>
                        <td><AddPost data={this.props.location.state.selectedClubId}/></td>
                                {(this.state.rowsPerPage > 0
                                    ? this.state.posts.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                    : this.state.posts).map((row)=>
                                    <TableRow>
                                        <TableCell><div>Title: {row.title}</div></TableCell>
                                        <TableCell>by {row.writer.userName}</TableCell>
                                        <TableCell><div><Link
                                            to={{
                                                pathname: "/club-post/"+row.id,
                                                state: {
                                                    postId:row.id,
                                                    title: row.title,
                                                    owner: row.writer.userName,
                                                    text: row.text}
                                            }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
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
                    </Grid>
                </Grid>

            </div>
        );
    }
}

