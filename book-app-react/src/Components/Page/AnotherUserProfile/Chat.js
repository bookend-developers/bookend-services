import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import { Link } from 'react-router-dom';
import Grid from "@material-ui/core/Grid";
import TablePagination from "@material-ui/core/TablePagination";

export default class FetchRandomUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            text:"",
            subject:"",
            chat:[],
            page:0,
            rowsPerPage:4,
        };
        this.sendMessage=this.sendMessage.bind(this);
        this.onChangeSubject=this.onChangeSubject.bind(this);
        this.onChangeText=this.onChangeText.bind(this);
        this.handleMessageRefresh=this.handleMessageRefresh.bind(this);
    }

    onChangeText(e) {
        this.setState({
            text: e.target.value
        });
    }

    onChangeSubject(e) {
        this.setState({
            subject: e.target.value
        });
    }
    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    sendMessage(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({"text":this.state.text,"subject":this.state.subject});

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8087/api/message/new/"+this.props.location.state.userName, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    alert(result);
                    window.location.reload();
            }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
        }).catch((err)=> {
            alert("Message service temporarily is offline for maintenance.")
        })
    }

    handleMessageRefresh(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8087/api/message/chat/"+this.props.location.state.userName, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({chat:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Message service temporarily is offline for maintenance.")
        })
    }

    componentDidMount() {
       this.handleMessageRefresh();
    }

    render() {
        return (
            <div style={{flexGrow: 1}}>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"2%",minWidth:400,maxWidth: 500}}>
                        <Paper>
                    <Typography
                        style={{marginLeft:"40%",marginTop:"10%"}}
                    >MESSAGE
                    </Typography>
                    <Typography
                        style={{marginLeft:"5%",marginTop:"5%"}}
                    >From: {AuthService.getCurrentUserName()}
                    </Typography><br/>
                    <Typography
                        style={{marginLeft:"5%"}}
                    >To: {this.props.location.state.userName}
                    </Typography><br/>
                    <form>
                        <Typography style={{marginLeft:"5%"}}>Subject:</Typography>
                        <textarea style={{marginLeft:"5%"}} rows="2" cols="50" value={this.state.subject} onChange={this.onChangeSubject} />
                        <br/><br/>
                        <Typography
                            style={{marginLeft:"5%"}}>Message:</Typography>
                        <textarea  style={{marginLeft:"5%"}} rows="7" cols="50" value={this.state.text} onChange={this.onChangeText} />

                    </form><br/>
                    <Button
                        style={{backgroundColor:"#CB4335",color:"white",marginLeft:"40%"}}
                        onClick={()=>{if(this.state.text!=="" && this.state.subject!==""){this.sendMessage()}else{alert("Text and subject cannot be empty")}}}>Send</Button>
                    </Paper>
                </Grid>
                    <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                        <Paper style={{marginTop:"3.5%"}}>
                            <Table>
                            <TableRow><TableCell><Typography style={{marginTop:"3%",marginLeft:"5%"}}>Chat</Typography></TableCell>

                            </TableRow>
                            </Table>

                            {(this.state.rowsPerPage > 0
                                ? this.state.chat.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                : this.state.chat).map((row)=>
                               <TableRow>
                                   <TableCell>
                                       <tr>
                                  <tr> {row.sender} said to {row.receiver}</tr>
                                           <tr>Date and Time: {row.sendDate.split("T")[0]+ " and "+ row.sendDate.split("T")[1].split(".")[0]}</tr>
                               </tr>
                                       <tr>
                                           Subject: {row.subject}
                                       </tr>
                                   <tr>
                                   Message: {row.text}
                                   </tr>
                                   </TableCell>
                               </TableRow>
                        )}
                            <TablePagination
                                count={this.state.chat.length}
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