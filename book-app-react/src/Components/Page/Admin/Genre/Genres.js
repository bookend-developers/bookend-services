import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import TableHead from "@material-ui/core/TableHead";
import AuthService from "../../../../Service/AuthService";
import Table from "@material-ui/core/Table";
import UpdateGenre from "./UpdateGenre";
import NewGenre from "./NewGenre";


export default class Genres extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            page:0,
            rowsPerPage:5,
            genres: [],

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

        fetch("http://localhost:8082/api/book/admin/genres", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token" && result) {
                    this.setState({genres:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })

    }

    render() {

        if (!this.state.genres) {
            return <div>There is no genres.</div>;
        }


        return (
            <div style={{flexGrow: 1}}>
                <Paper style={{marginLeft:"35%",marginTop:"2%",minWidth:400,maxWidth: 500}}>
                    <TableCell><div><NewGenre/>
                    </div></TableCell>
                    {(this.state.rowsPerPage > 0
                        ? this.state.genres.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.genres).map((row)=>
                        <TableRow >
                            <TableCell><div>{row.genre}</div></TableCell>
                            <TableCell><div><UpdateGenre data={row.id}/>
                            </div></TableCell>
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