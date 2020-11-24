import React, {useEffect} from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import PopOverShelf from "./PopOverShelf";
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import AllAuthor from "./AllAuthor";
import TextField from "@material-ui/core/TextField";
import Table from "@material-ui/core/Table";
import App from "../../../App/App";

export default class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page:0,
            rowsPerPage:5,
            book: [],
            user: "",
            anchorEl:null,
            searchCategory:"",
            bookSearch:""
        };

        this.handleOnClickProfile=this.handleOnClickProfile.bind(this);
        this.handleClose=this.handleClose.bind(this);
        this.handleSearchCategory=this.handleSearchCategory.bind(this);
        this.onChangeBookSearch = this.onChangeBookSearch.bind(this);
        this.handleSearchByGenre = this.handleSearchByGenre.bind(this);
        this.handleCurrentUserName = this.handleCurrentUserName.bind(this);
        this.handleListBook = this.handleListBook.bind(this);

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

    onChangeBookSearch(e){
        this.setState({
            bookSearch: e.target.value
        });
    }

    handleOnClickProfile = () => {
        this.props.history.push("/profile/"+AuthService.getCurrentUserName());
        window.location.reload();
    }

    handleChangePage = (event, newPage) => {
        this.setState({page:newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage:parseInt(event.target.value, 10)})
        this.setState({page:0});
    };

    handleSearchByGenre(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book?genre="+this.state.bookSearch, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if(result!=="[]") {
                        this.setState({book: JSON.parse(result)});
                        console.log(JSON.parse(result))
                    }else{
                        alert("Book is not found for given title.")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    handleListBook(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({book:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }

    handleCurrentUserName(){
        AuthService.handleUserName(AuthService.getCurrentUser()).then((res)=>{
            if (res) {
                console.log(AuthService.getCurrentUserName())
            }else{
                alert("\n" +
                    "You entered an incorrect username and password")
            }
        })
    }


    componentDidMount() {
        this.handleCurrentUserName();
        this.handleListBook();
    }

    render() {

        if (!this.state.book) {
            return <div>didn't get a book</div>;
        }
        if(this.state.searchCategory==="Author Name"){
            return(<div>
                <AllAuthor/>
            </div>)
        }
        if(this.state.searchCategory==="Book Name"){
            return(<div>
                <Home/>
            </div>)
        }


        return (
            <div style={{flexGrow: 1}}>
                <Button
                    //component={ Link } to="/Profile"
                    onClick={this.handleOnClickProfile}
                    style={{marginLeft:"48%",marginTop:"1%",backgroundColor:"#E5E7E9"}}
                >Profile</Button>
                <Table style={{marginLeft:"35%",width:"30%"}}>
                    <TableRow >
                        <TableCell><Button
                            style={{backgroundColor:"#FAE5D3"}} aria-controls="simple-menu" aria-haspopup="true" onClick={this.handleClick}>
                            Category
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
                                value={this.state.bookSearch}
                                onChange={this.onChangeBookSearch}
                            />
                        </form></TableCell>
                        <TableCell><Button style={{backgroundColor:"#FAE5D3"}} onClick={this.handleSearchByBookName}>Search</Button></TableCell>
                    </TableRow>
                </Table>
                <Paper style={{marginLeft:"27%",minWidth:400,maxWidth: 700}}>
                    <Typography
                        style={{marginLeft:"30%",marginTop:"5%"}}
                    >Recommendations
                    </Typography>
                    {(this.state.rowsPerPage > 0
                        ? this.state.book.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.book).map((row)=>
                        <TableRow >
                            <TableCell><div>Book Name: {row.bookName}</div></TableCell>
                            <TableCell style={{marginLeft: "2%"}}>Genre: {row.genre.genre}</TableCell>
                            <TableCell><PopOverShelf data={row.id}/></TableCell>
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