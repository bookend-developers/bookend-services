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
import Grid from "@material-ui/core/Grid";
import Comments from "./Comment";

export default class RateAndComments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            rates: [],
            page:0,
            rowsPerPage:6,
            bookInfo:[]
        };
        this.handleDeleteRate=this.handleDeleteRate.bind(this);
        this.handleRefreshRate=this.handleRefreshRate.bind(this);
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleDeleteRate(rateId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/rate/delete/"+rateId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if (result.slice(2, 9) === "message") {
                        alert("\n" + JSON.parse(result).message)
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

    handleRefreshRate(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");
        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/rate/user", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({rates:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    componentDidMount() {
        this.handleRefreshRate();

    }

    render() {

        if (!this.state.rates) {
            return <div>There is no rate</div>;
        }

        return (
            <div style={{flexGrow: 1}}>
                <Typography style={{marginTop:"2%",marginLeft:"43%"}}>Rate and Comments</Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6} style={{marginLeft:"10%",marginTop:"2%",minWidth:400,maxWidth: 500}}>
                        <Paper>
                    <Table>
                        <TableHead>
                            <TableCell>Book Name</TableCell>
                            <TableCell>Rate</TableCell>
                        </TableHead>
                        {(this.state.rowsPerPage > 0
                            ? this.state.rates.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                            : this.state.rates).map((row)=>
                            <TableRow >
                                <TableCell><div>{row.bookId.bookname}</div></TableCell>
                                <TableCell><div>{row.rate}</div></TableCell>
                                <TableCell>
                                    <Button onClick={()=>{this.handleDeleteRate(row.rateId);}}
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
                    </Grid>
                    <Grid item xs={16} sm={8} style={{marginLeft:"5%",minWidth:400,maxWidth: 700,maxHeight:600}}>
                        <Paper style={{marginTop:"4%"}}>
                            <Comments/>
                        </Paper>
                    </Grid>
                </Grid>
            </div>
        );
    }
}