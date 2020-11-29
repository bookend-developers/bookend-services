import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import { Link } from 'react-router-dom';
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import MessageInbox from "./MessageInbox";

export default class MessageSent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            sent: [],
            page:0,
            rowsPerPage:6,
            chosen:""
        };
        this.handleDeleteSentMessage=this.handleDeleteSentMessage.bind(this);
        this.handleRefreshSentMessage=this.handleRefreshSentMessage.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleDeleteSentMessage(messageId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8087/api/message/delete/"+messageId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if (result.slice(2, 9) === "message") {
                        alert("It has been deleted. Please refresh..")
                        window.location.reload();
                    } else {
                        alert("Not found")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    handleRefreshSentMessage(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8087/api/message/sent", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({sent:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    componentDidMount() {
        this.handleRefreshSentMessage();
    }

    render() {

        if (!this.state.sent) {
            return <div>There is no message</div>;
        }
        if (this.state.chosen==="Inbox") {
            return (<div><MessageInbox/></div>);
        }

        return (
            <div style={{flexGrow: 1}}>
                <Table style={{marginLeft:"45%",width:"30%",marginTop:"1.5%"}}>
                    <td> <Button
                        onClick={()=>this.setState({chosen:"Inbox"})}
                        style={{backgroundColor:"#E5E7E9"}}
                    >Inbox</Button></td>
                </Table>
                <Paper style={{marginLeft:"20%",width:"60%",marginTop:"1%"}}>
                    <Typography style={{marginLeft:"38%"}}>SENT MESSAGES</Typography>
                    <Table>
                        <TableHead>
                            <TableCell>To</TableCell>
                            <TableCell>Subject</TableCell>
                            <TableCell>Message</TableCell>
                            <TableCell>Date</TableCell>
                            <TableCell>Time</TableCell>
                        </TableHead>
                        {(this.state.rowsPerPage > 0
                            ? this.state.sent.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                            : this.state.sent).map((row)=>
                            <TableRow >
                                <TableCell><div><Link
                                    to={{
                                        pathname: "/user/"+row.receiver,
                                        state: { selectedUser:row.receiver}
                                    }}>{row.receiver}
                                </Link></div></TableCell>
                                <TableCell><div>{row.subject}</div></TableCell>
                                <TableCell><div>{row.text}</div></TableCell>
                                <TableCell><div>
                                    {row.sendDate.split("T")[0]}</div></TableCell>
                                <TableCell><div>
                                    {row.sendDate.split("T")[1].split(".")[0]}</div></TableCell>
                                <TableCell><Link  to={{
                                    pathname: "/message/to/"+row.receiver,
                                    state: { userName:row.receiver}
                                }}>
                                    <Button style={{backgroundColor:"#5DADE2",color:"white"}}>Reply</Button>
                                </Link>
                                </TableCell>
                                <TableCell>
                                    <Button onClick={()=>this.handleDeleteSentMessage(row.id)}
                                            style={{backgroundColor:"#C0392B",color:"white"}}>Delete</Button>
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
                    </Table>
                </Paper>
            </div>
        );
    }
}