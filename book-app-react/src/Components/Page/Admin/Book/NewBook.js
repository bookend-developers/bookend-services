import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Paper from "@material-ui/core/Paper";
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import {Typography} from "@material-ui/core";
import TableCell from "@material-ui/core/TableCell";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import TableRow from "@material-ui/core/TableRow";
import TablePagination from "@material-ui/core/TablePagination";
import DialogActions from "@material-ui/core/DialogActions";
import AuthService from "../../../../Service/AuthService";


export default class NewBook extends Component {

    constructor(props) {
        super(props);
        this.state = {
            book_name:"",
            author_name: "",
            author_id: "",
            genre: "",
            book_page: 0,
            description: "",
            ISBN: "",
            page: 0,
            rowsPerPage: 5,
            authors: [],
            open: false,
        };
        this.handleNewBook=this.handleNewBook.bind(this);
        this.onChangeAuthorName = this.onChangeAuthorName.bind(this);
        this.onChangeBookName = this.onChangeBookName.bind(this);
        this.onChangePageNumber = this.onChangePageNumber.bind(this);
        this.onChangeISBN = this.onChangeISBN.bind(this);
        this.onChangeGenre = this.onChangeGenre.bind(this)
        this.onChangeAuthorId = this.onChangeAuthorId.bind(this)
    }

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };


    handleChangePage = (event, newPage) => {
        this.setState({page: newPage});
    };

    handleChangeRowsPerPage = (event) => {
        this.setState({rowsPerPage: parseInt(event.target.value, 10)})
        this.setState({page: 0});
    };

    loadAuthors = () => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer " + AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8085/api/author", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(JSON.parse(result).status !== 500){
                        this.setState({authors: JSON.parse(result)})
                        console.log(this.state.authors)
                    }
                    else{
                        alert("Something went wrong.")
                    }

                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
    }
    componentDidMount(){
        this.loadAuthors();
        
    }
    handleNewBook(page,genre,description,bookName,author,author_id,ISBN) {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {
                "page":page,
                "genre":genre,
                "description":description,
                "bookName":bookName,
                "author":author,
                "isbn":ISBN,
                "authorid":author_id});

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

       fetch("http://localhost:8082/api/book/admin/new", requestOptions)
            .then(response => response.text())
            .then(result => {
                if(JSON.parse(result).bookName===bookName){
                    alert("Added successfully.")
                }else{
                    alert("Something went wrong:"+JSON.parse(result).message)
                }
            })
    }

    onChangeBookName(e) {
        this.setState({
            book_name: e.target.value
        });
    }

    onChangeAuthorName(e) {
        this.setState({
            author_name: e.target.value
        });
    }

    onChangePageNumber(e) {
        this.setState({
            book_page: e.target.value
        });
    }
    onChangeGenre(e) {
        this.setState({
            genre: e.target.value
        });
    }
    onChangeDescription = (e)=> {
        this.setState({
            description: e.target.value
        });
    }
    onChangeAuthorId(e) {
        this.setState({
            author_id: e.target.value
        });
    }
    onChangeISBN(e) {
        this.setState({
            ISBN: e.target.value
        });
    }

    checkValidation =()=>{
        if(this.state.book_name!=="" && this.state.genre!=="" && this.state.book_page!=="" && this.state.ISBN!=="" && this.state.author!=="" && this.state.description!=="") {
            if ((/^\d+$/.test(this.state.ISBN)) && (/^\d+$/.test(this.state.book_page))) {
                if(this.state.ISBN.length===13) {
                    this.handleNewBook(this.state.book_page, this.state.genre, this.state.description, this.state.book_name, this.state.author_name, this.state.author_id, this.state.ISBN)
                }else{
                    alert("The length of the ISBN must be 13")
                }
            }else{
                alert("ISBN or book page must contain only digits.")
            }

        }else{
            alert("All fields must be filled!!")
        }
    }

    render() {

        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <Paper style={{backgroundColor:"#F8F9F9",marginTop:"1.5%",marginLeft:"25%",width:"50%"}}>
                        <Typography style={{marginLeft:"40%"}}>New Book</Typography>
                        <form style={{marginTop:"5%",marginLeft:"30%"}}>
                            <div className="form-group">
                                <TextField
                                    style={{backgroundColor:"white"}}
                                    required
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="BookName"
                                    value={this.state.book_name}
                                    onChange={this.onChangeBookName}

                                />

                            </div>

                            <td><div className="form-group">

                                <TextField
                                    id="standard-read-only-input"
                                    style={{backgroundColor:"white"}}
                                    label="Author"
                                    variant="outlined"
                                    value = {this.state.author_name}
                                    defaultValue={"Author"}
                                    InputProps={{
                                        readOnly: true,
                                    }}
                                />
                            </div></td>

                            <td><div ><td><Button
                                onClick={this.handleClickOpen}
                                style={{marginLeft: "17%",marginTop: "35%", backgroundColor: "#E5E7E9"}}
                            >Authors</Button></td>
                                <Dialog
                                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                                    <DialogContent>
                                        <Typography
                                            style={{marginLeft: "35%"}}>Authors</Typography>
                                        <Paper>
                                            {(this.state.rowsPerPage > 0
                                                ? this.state.authors.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                                : this.state.authors).map((row)=>
                                                <TableRow>
                                                    <TableCell><div><Link
                                                        onClick={()=>this.setState({author_name:row.name,author_id:row.id})}>{row.name}
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
                                    </DialogContent>
                                    <DialogActions>
                                        <Button onClick={this.handleClose} color="primary">
                                            Close
                                        </Button>
                                    </DialogActions>
                                </Dialog></div></td><br/>

                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="ISBN13"
                                    value={this.state.ISBN}
                                    onChange={this.onChangeISBN}
                                    inputProps={{ maxLength: 13, minLength:13 }}
                                />
                            </div><br/>
                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Genre"
                                    value={this.state.genre}
                                    onChange={this.onChangeGenre}
                                />
                            </div><br/>
                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Book Page"
                                    value={this.state.book_page}
                                    onChange={this.onChangePageNumber}
                                />
                            </div><br/>


                        </form>
                        <Typography
                            style={{marginLeft:"22%"}}>Description:</Typography>
                        <textarea  style={{marginLeft:"22%"}} rows="7" cols="50" value={this.state.description} onChange={this.onChangeDescription} />
                        <Button
                            onClick={()=>{this.checkValidation()}}
                            style={{marginTop:"3%",marginLeft:"33%",backgroundColor:"#5DADE2",color:"white"}}
                            variant="outlined">Add</Button>
                        <Button
                            component={Link} to={"/admin"}
                            style={{marginTop:"3%",marginLeft:"5%",backgroundColor:"#C0392B",color:"white"}}
                            variant="outlined"
                        >Cancel</Button>

                    </Paper>
                </div>
            </div>
        );
    }
}
