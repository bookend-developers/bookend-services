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
import ExtraComment from "./ExtraComment";
import TableHead from "@material-ui/core/TableHead";

export default class ClubAndPost extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page:0,
            rowsPerPage:5,
            comments:[]
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

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/post/comment/"+ this.props.location.state.postId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token" ) {
                    console.log(result)
                    if(result!=="") {
                        this.setState({comments: JSON.parse(result)});
                    }else{
                        alert("There is no comment")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })

    }

    render() {
        if(!this.props.location.state.postId){
            return <div>There is no selected post</div>
        }
        if(this.state.comments===""){
            return <div>There is no comment</div>
        }
        return (
            <div style={{flexGrow: 1}}>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"7%",minWidth:400,maxWidth: 500}}>
                        <Paper>
                            <Typography style={{textAlign:"center"}}> Post Title : {this.props.location.state.title}</Typography>
                            <img
                                style={{marginLeft:"30%",width:"40%",marginTop:"3%"}}
                                src="data:image/jpeg;base64,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"
                                alt="book-img"
                                className="book-img-card"
                            />
                            <Table>
                                <TableRow>
                                    <Typography style={{textAlign:"center"}}>Writer: {this.props.location.state.owner}</Typography><br/>
                                </TableRow>
                            </Table>
                            <Typography>
                                <TableCell><Typography>Description: {this.props.location.state.text}</Typography></TableCell>
                            </Typography>

                        </Paper>
                    </Grid>
                    <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                        <Paper style={{marginTop:"3.5%"}}>
                            <td> <Typography
                                style={{marginLeft: "35%"}}>Comments</Typography></td>
                            <td><ExtraComment data={this.props.location.state.postId}/></td>
                            <Table>
                            <TableHead>
                                <TableCell>Username</TableCell>
                                <TableCell>Comment</TableCell>
                                <TableCell>Date</TableCell>
                                <TableCell>Time</TableCell>
                            </TableHead>
                            {(this.state.rowsPerPage > 0
                                ? this.state.comments.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                : this.state.comments).map((row)=>
                                <TableRow>
                                    <TableCell><div>{row.username}</div></TableCell>
                                    <TableCell>{row.comment}</TableCell>
                                    <TableCell>{row.date.split("T")[0]}</TableCell>
                                    <TableCell>{row.date.split("T")[1].split(".")[0]}</TableCell>
                                </TableRow>
                            )}</Table>
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


/*
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
 */
