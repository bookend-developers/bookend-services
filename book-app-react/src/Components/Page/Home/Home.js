import React, {useEffect} from 'react';
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {TableHead, Typography} from "@material-ui/core";
import { Link } from 'react-router-dom';
import TablePagination from "@material-ui/core/TablePagination";
import PopOverShelf from "./PopOverShelf";
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import AllAuthor from "./AllAuthor";
import TextField from "@material-ui/core/TextField";
import Table from "@material-ui/core/Table";
import Select from '@material-ui/core/Select';
import Genre from "./Genre";

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
            title:"",
            genre:"",
            rateSort:false,
            commentSort:false,
            sort:"",
        };

        this.handleOnClickProfile=this.handleOnClickProfile.bind(this);
        this.handleClose=this.handleClose.bind(this);
        this.handleSearchCategory=this.handleSearchCategory.bind(this);
        this.onChangeBookSearch = this.onChangeBookSearch.bind(this);
        this.onChangeGenre = this.onChangeGenre.bind(this);
        this.handleSearchByBookName = this.handleSearchByBookName.bind(this);
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


    handleSortChange =(e)=>{
        this.setState({sort:e.target.value});
    }

    onChangeBookSearch(e){
        this.setState({
            title: e.target.value
        });
    }

    onChangeGenre(e){
        this.setState({
            genre: e.target.value
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

    handleSearchByBookName(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };
        console.log(this.state.rateSort)
        console.log(this.state.commentSort)
        fetch("http://localhost:8082/api/book?title="+this.state.title+"&genre="+this.state.genre+"&rateSort="+this.state.rateSort+"&commentSort="+this.state.commentSort, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    if(JSON.parse(result).status!==404) {
                        this.setState({book: JSON.parse(result)});
                        console.log(JSON.parse(result))
                    }else{
                        alert("There is no match")
                    }
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Book service temporarily is offline for maintenance.")
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
            }).catch((err)=> {
            alert("Book service temporarily is offline for maintenance.")
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

        return (
            <div style={{flexGrow: 1}}>
                <Table style={{marginLeft:"22%",width:"30%"}}>
                <TableRow >
                    <TableCell><Button
                        style={{backgroundColor:"#FAE5D3"}} aria-controls="simple-menu" aria-haspopup="true" onClick={this.handleClick}>
                    Book
                </Button>
                <Menu
                    id="simple-menu"
                    anchorEl={this.state.anchorEl}
                    keepMounted
                    open={Boolean(this.state.anchorEl)}
                    onClose={this.handleClose}
                >
                    <MenuItem onClick={()=>this.handleSearchCategory("Book Name")}>Book</MenuItem>

                    <MenuItem onClick={()=>this.handleSearchCategory("Author Name")}>Author</MenuItem>
                </Menu></TableCell>
                <TableCell><form noValidate autoComplete="off">
                    <TextField
                        style={{backgroundColor:"white"}}
                        id="standard-basic"
                        label="Book Title"
                        value={this.state.title}
                        onChange={this.onChangeBookSearch}
                        style={{width:150}}
                    />
                </form></TableCell>
                    <TableCell><form noValidate autoComplete="off">
                        <TextField
                            style={{backgroundColor:"white"}}
                            id="standard-basic"
                            label="Genre"
                            value={this.state.genre}
                            onChange={this.onChangeGenre}
                            style={{width:150}}
                        />
                    </form></TableCell>
                    <TableCell><Button style={{backgroundColor:"#FAE5D3"}} onClick={this.handleSearchByBookName}>Search</Button></TableCell>
                       <TableCell>
                           <Select
                            labelId="demo-customized-select-label"
                            id="demo-customized-select"
                            value={this.state.sort}
                            onChange={this.handleSortChange}
                            style={{width:150}}
                        >
                            <MenuItem value="" onClick={()=>{this.setState({rateSort:false,commentSort:false});}}>
                                <em>None</em>
                            </MenuItem>
                            <MenuItem value={"rate"} onClick={()=>{this.setState({rateSort:true,commentSort:false});}}>Sort By Rate</MenuItem>
                            <MenuItem value={"comment"} onClick={()=>{this.setState({rateSort:false,commentSort:true});}}>Sort By Comment</MenuItem>
                        </Select>
                      </TableCell>
                    <TableCell><Button
                        style={{backgroundColor:"#FAE5D3"}} aria-controls="simple-menu" aria-haspopup="true" onClick={this.handleSearchByBookName}>
                        Sort
                    </Button></TableCell>
                </TableRow>
                </Table>
                <Paper style={{marginLeft:"27%",minWidth:400,maxWidth:800}}>
                    <Typography
                        style={{marginLeft:"30%",marginTop:"5%"}}
                    >Recommendations
                        </Typography>
                    <TableHead>
                        <TableCell>Book Name</TableCell>
                        <TableCell>Genre</TableCell>
                        <TableCell>Rating</TableCell>
                        <TableCell>Number of Comments</TableCell>
                    </TableHead>
                    {(this.state.rowsPerPage > 0
                        ? this.state.book.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                        : this.state.book).map((row)=>
                        <TableRow >
                            <TableCell><div>{row.bookName}</div></TableCell>
                            <TableCell>{row.genre.genre}</TableCell>
                            <TableCell>{row.rate}</TableCell>
                            <TableCell>{row.comments.length}</TableCell>
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
                        count={this.state.book.length}
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

