import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../Service/AuthService";
import {Typography} from "@material-ui/core";
import BookFromShelf from "./BookFromShelf";
import TextField from "@material-ui/core/TextField";
import { Link } from 'react-router-dom';

export default class FetchRandomUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            shelvesData: [],
            newShelfName:"",
        };
        this.onChangeShelfName=this.onChangeShelfName.bind(this);
        this.createNewShelf=this.createNewShelf.bind(this);
        this.deleteShelf=this.deleteShelf.bind(this);
        this.handleBook=this.handleBook.bind(this);
    }

    onChangeShelfName(event) {
        this.setState({
            newShelfName: event.target.value
        });
    }

    createNewShelf(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/shelf/new?name="+this.state.newShelfName, requestOptions)
            .then(response => response.json())
            .then(result => {

                if(result.status===400){
                    alert("\n" +result.message)
                }
            })
            .catch(error => console.log('error', error));
    }

    deleteShelf(shelfId){
        var myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        var requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/shelf/delete/"+shelfId, requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }
    handleBook(){
        this.setState({loading:true});
    }

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/shelves", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result) {
                    this.setState({shelvesData:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }
            })
    }

    render() {

        if (!this.state.shelvesData) {
            return <div>didn't get a shelf</div>;
        }

        return (
            <div style={{flexGrow: 1}}>
                <Paper >
                    <Typography
                        style={{marginLeft:"10%",marginTop:"5%"}}
                    >Shelves
                    </Typography>
                    {this.state.shelvesData.map((row)=>
                        <TableRow >
                            <TableCell style={{marginLeft: "2%"}}>Shelf Name:</TableCell>
                            <TableCell><div>{row.shelfname}</div></TableCell>
                            <TableCell><div><BookFromShelf data ={row.id}/></div></TableCell>
                            <TableCell><Button
                                onClick={()=>this.deleteShelf(row.id)}
                                style={{backgroundColor:"#C0392B",color:"white"}}>Delete</Button></TableCell>

                        </TableRow>
                    )}
                    <br/>
                    <TableRow>
                    <TableCell><form noValidate autoComplete="off">
                        <TextField
                            style={{backgroundColor:"white"}}
                            id="standard-basic"
                            label="ShelfName"
                            value={this.state.newShelfName}
                            onChange={this.onChangeShelfName}
                        />
                    </form></TableCell>
                    <TableCell><Button
                        onClick={this.createNewShelf}
                        style={{color:"white",backgroundColor:"#BB8FCE",marginTop:"10%"}} >Add New Shelf</Button></TableCell>
                    </TableRow>
                </Paper>
            </div>
        );
    }
}