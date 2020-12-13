import React, { Component } from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import TableHead from "@material-ui/core/TableHead";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import Table from "@material-ui/core/Table";
import Chip from '@material-ui/core/Chip';
import DoneIcon from '@material-ui/icons/Done';
import AddNewShelf from "./AddNewShelf";

export default class NewShelf extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            genres:[],
            genreName:"",
            shelvesData: [],
            newShelfName:"",
            page: 0,
            tags: [],
            rowsPerPage: 5,
        };
        this.onChangeShelfName=this.onChangeShelfName.bind(this);
        this.deleteShelf=this.deleteShelf.bind(this);
    }


    handleChange = (event) => {
        this.setState({genre:event.target.value});
    };

    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage: parseInt(event.target.value, 10)})
        this.setState({page: 0});
    };

    onChangeShelfName(event) {
        this.setState({
            newShelfName: event.target.value
        });
    }


    deleteShelf(shelfId){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/api/shelf/delete/"+shelfId, requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch((err)=> {
                alert("Shelf service temporarily is offline for maintenance.")
            })
    }


    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/api/shelf/user/", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({shelvesData:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Shelf service temporarily is offline for maintenance.")
        })
    }

    render() {

        if (!this.state.shelvesData) {
            return <div>didn't get a shelf</div>;
        }

        return (
            <div style={{flexGrow: 1}}>
                <Paper style={{marginLeft:"25%",width:"50%"}}>

                    <Table style={{marginLeft:"32%",width:"50%",marginTop:"2%"}}>
                        <td><Typography
                            style={{marginLeft:"10%",marginTop:"5%"}}
                        >Shelves
                        </Typography></td>
                        <td><AddNewShelf/></td>
                    </Table>
                    <TableHead>
                        <TableCell>Shelf Name</TableCell>
                        <TableCell>Tags</TableCell>
                    </TableHead>
                    {(this.state.rowsPerPage > 0
                        ? this.state.shelvesData.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.shelvesData).map((row)=>
                        <TableRow >
                            <TableCell><div>{row.shelfname}</div></TableCell>
                            <TableCell><div>{row.tags.map(tag =>
                                <Chip label={tag.tag}/>
                                )}</div></TableCell>
                            <TableCell><div><Link
                                to={{
                                    pathname: "/shelf/"+AuthService.getCurrentUserName()+"/"+row.shelfname,
                                    state: { shelfId:row.id,shelfName:row.shelfname}
                                }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
                            </Link></div></TableCell>
                            <TableCell><Button
                                onClick={()=>this.deleteShelf(row.id)}
                                style={{backgroundColor:"#C0392B",color:"white"}}>Delete</Button></TableCell>

                        </TableRow>
                    )}<TablePagination
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

/*  <FormControl >
                            <InputLabel id="demo-mutiple-checkbox-label">Tag</InputLabel>
                            <Select
                                labelId="demo-mutiple-checkbox-label"
                                id="demo-mutiple-checkbox"
                                multiple
                                value={genreName}
                                onChange={}
                                input={<Input />}
                                renderValue={(selected) => selected.join(', ')}
                                MenuProps={MenuProps}
                            >
                                {genres.map((name) => (
                                    <MenuItem key={name} value={name}>
                                        <Checkbox checked={personName.indexOf(name) > -1} />
                                        <ListItemText primary={name} />
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl><FormControl >
                            <InputLabel id="demo-mutiple-checkbox-label">Tag</InputLabel>
                            <Select
                                labelId="demo-mutiple-checkbox-label"
                                id="demo-mutiple-checkbox"
                                multiple
                                value={this.state.genreName}
                                onChange={this.handleChange}
                                input={<Input />}
                                renderValue={(selected) => selected.join(', ')}
                                MenuProps={MenuProps}
                            >
                                {genres.map((name) => (
                                    <MenuItem key={name} value={name}>
                                        <Checkbox checked={personName.indexOf(name) > -1} />
                                        <ListItemText primary={name} />
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl> */