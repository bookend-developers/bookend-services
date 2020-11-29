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
import Table from "@material-ui/core/Table";

export default class Books extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            page:0,
            rowsPerPage:5,
            bookFromShelf: [],

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

        fetch("http://localhost:8082/api/book/user/shelf/"+this.props.location.state.shelfId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({bookFromShelf:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })

    }

    render() {

        if (!this.state.bookFromShelf) {
            return <div>didn't get a shelf</div>;
        }


        return (
            <div style={{flexGrow: 1}}>
                <Table style={{marginLeft:"42%",width:"20%",marginTop:"2%"}}>
                    <td> <Button
                        component={ Link } to="/home"
                        style={{backgroundColor:"#E5E7E9"}}
                    >Home</Button></td>
                    <td> <Button
                        component={ Link } to={"/profile/"+AuthService.getCurrentUserName()}
                        style={{backgroundColor:"#E5E7E9"}}
                    >Profile</Button></td>
                </Table>
                <Paper style={{marginLeft:"35%",minWidth:400,maxWidth: 500}}>
                    <Typography
                        style={{marginLeft:"30%",marginTop:"5%"}}
                    >{this.props.location.state.shelfName}</Typography>
                    <TableHead>
                        <TableCell>Book Name</TableCell>
                        <TableCell>Genre</TableCell>
                    </TableHead>
                    {(this.state.rowsPerPage > 0
                        ? this.state.bookFromShelf.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.bookFromShelf).map((row)=>
                        <TableRow >
                            <TableCell><div>{row.bookName}</div></TableCell>
                            <TableCell><div>{row.genre.genre}</div></TableCell>
                            <TableCell><div><Link
                                to={{
                                    pathname: "/book/"+row.id,
                                    state: { selectedBookId:row.id}
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
            </div>
        );
    }
}