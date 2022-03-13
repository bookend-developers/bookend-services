import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";

export default class Rate extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            averageRate: 0,
            propsBookId:"",
            propsBookName:"",
            rate:null,
        };
        this.showAverageRate=this.showAverageRate.bind(this);
        this.giveRate=this.giveRate.bind(this);
        this.onChangeRate = this.onChangeRate.bind(this);
    }


    static getDerivedStateFromProps(props, state) {
        console.log(props.data)
        console.warn("hook",props,state)
        return {
            propsBookId: props.data.bookId,
            propsBookName: props.data.bookName
        }
    }

    onChangeRate(e){
        this.setState({
            rate: e.target.value
        });
    }

    giveRate() {

        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({
            "bookname":this.state.propsBookName,
            "bookId":this.state.propsBookId,
            "rate":this.state.rate
        });

        let requestOptions = {
             method: 'POST',
             headers: myHeaders,
             body: raw,
             redirect: 'follow'

    }

        fetch("http://localhost:8081/api/rate/new/", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if (JSON.parse(result).status !== 400) {
                        alert("The rate is added successfully")
                        window.location.reload()
                    } else {
                        alert(JSON.parse(result).message)
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Rate service temporarily is offline for maintenance.")
        })
    }

    showAverageRate() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8081/api/rate/book/"+this.state.propsBookId, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if (JSON.parse(result).status !== 500) {
                        this.setState({averageRate: result});
                    } else {
                        this.setState({averageRate: 0});
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Rate service temporarily is offline for maintenance.")
        })


    }
    componentDidMount() {
        this.showAverageRate();
    }

    render() {

        return (
            <div style={{flexGrow: 1}}>
                <TableRow>
                   <TableCell> <Typography
                        style={{marginTop:"5%"}}
                    >Average Rate:{this.state.averageRate}</Typography></TableCell>
                    <TableCell><form noValidate autoComplete="off">
                        <TextField
                            style={{backgroundColor:"white"}}
                            id="standard-basic"
                            label="Rate"
                            value={this.state.rate}
                            onChange={this.onChangeRate}
                        />
                    </form></TableCell>
                    <TableCell><Button onClick={()=>{if(this.state.rate<=5){this.giveRate()}else{alert("The rate must not be greater than 5")}}}
                                       style={{backgroundColor:"#E6B0AA"}}>Give</Button></TableCell>
                </TableRow>
            </div>
        );
    }
}
