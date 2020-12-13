import React, {useEffect} from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import Home from './Home.js';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from "@material-ui/core/TextField";
import Table from "@material-ui/core/Table";
import Genre from "./Genre";

export default class AllAuthor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page:0,
            rowsPerPage:5,
            author: [],
            user: "",
            anchorEl:null,
            authorSearch:""
        };
        this.handleOnClickProfile=this.handleOnClickProfile.bind(this);
        this.handleClose=this.handleClose.bind(this);
        this.handleSearchCategory=this.handleSearchCategory.bind(this);
        this.onChangeAuthorSearch = this.onChangeAuthorSearch.bind(this);
        this.handleSearchByAuthorName = this.handleSearchByAuthorName.bind(this);
    }

    onChangeAuthorSearch(e){
        this.setState({
            authorSearch: e.target.value
        });
    }

    handleSearchCategory = (category)=>{
        this.setState({searchCategory:category})
    }

    handleClick =(e)=> {
        this.setState({anchorEl:e.currentTarget});
    };

    handleClose = () => {
        this.setState({anchorEl:null});
    };


    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleOnClickProfile = () => {
        this.props.history.push("/profile/"+AuthService.getCurrentUserName());
        window.location.reload();
    }

    handleSearchByAuthorName(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8085/api/author?title="+this.state.authorSearch, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if(result!=="[]") {
                        this.setState({author: JSON.parse(result)});
                        console.log(JSON.parse(result))
                    }else{
                        alert("Author is not found for given title.")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Author service temporarily is offline for maintenance.")
        })
    }

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8085/api/author", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({author:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Author service temporarily is offline for maintenance.")
        })

    }

    render() {

        if (!this.state.author) {
            return <div>didn't get an author</div>;
        }
        if(this.state.searchCategory==="Book Name"){
            return(<div>
                <Home/>
            </div>)
        }if(this.state.searchCategory==="Genre"){
            return(<div>
                <Genre/>
            </div>)
        }

        return (
            <div style={{flexGrow: 1}}>
                <Table style={{marginLeft:"35%",width:"30%"}}>
                    <TableRow >
                        <TableCell><Button
                            style={{backgroundColor:"#FAE5D3"}} aria-controls="simple-menu" aria-haspopup="true" onClick={this.handleClick}>
                            Author
                        </Button>
                            <Menu
                                id="simple-menu"
                                anchorEl={this.state.anchorEl}
                                keepMounted
                                open={Boolean(this.state.anchorEl)}
                                onClose={this.handleClose}
                            >
                                <MenuItem onClick={()=>this.handleSearchCategory("Book Name")}>Book</MenuItem>
                                <MenuItem onClick={()=>this.handleSearchCategory("Genre")}>Genre</MenuItem>
                                <MenuItem onClick={()=>this.handleSearchCategory("Author Name")}>Author</MenuItem>
                            </Menu></TableCell>
                        <TableCell><form noValidate autoComplete="off">
                            <TextField
                                style={{backgroundColor:"white"}}
                                id="standard-basic"
                                label="Title"
                                value={this.state.authorSearch}
                                onChange={this.onChangeAuthorSearch}
                            />
                        </form></TableCell>
                        <TableCell><Button style={{backgroundColor:"#FAE5D3"}} onClick={this.handleSearchByAuthorName}>Search</Button></TableCell>
                    </TableRow>
                </Table>

                <Paper style={{marginLeft:"35%",minWidth:400,maxWidth: 500}}>
                    <Typography
                        style={{marginLeft:"30%",marginTop:"5%"}}
                    >All Authors
                    </Typography>
                    {(this.state.rowsPerPage > 0
                        ? this.state.author.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.author).map((row)=>
                        <TableRow >
                            <TableCell style={{marginLeft: "2%"}}>Author Name:</TableCell>
                            <TableCell><div>{row.name}</div></TableCell>
                            <TableCell><div><Link
                                to={{
                                    pathname: "/author/"+row.id,
                                    state: { selectedAuthorId:row.id}
                                }}><Button style={{backgroundColor: "#5499C7", color: "white"}}>Show</Button>
                            </Link></div></TableCell>
                        </TableRow>
                    )}
                    <TablePagination
                        count={this.state.author.length}
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