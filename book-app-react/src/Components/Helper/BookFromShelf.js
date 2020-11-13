import React, { Component } from 'react';
import Button from "@material-ui/core/Button";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import TableHead from "@material-ui/core/TableHead";
import AuthService from "../../Service/AuthService";
import TablePagination from "@material-ui/core/TablePagination";
import PopupState, {bindPopover, bindTrigger} from "material-ui-popup-state/index";
import Popover from "@material-ui/core/Popover";
import Box from "@material-ui/core/Box";
import { Link } from 'react-router-dom';

export default class FetchBookFromShelf extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            page:0,
            rowsPerPage:5,
            bookFromShelf: [],
            propsShelfId:0,
        };

    }

    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsShelfId: props.data
        }
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

        fetch("http://localhost:8084/user/shelf/"+this.state.propsShelfId, requestOptions)
            .then(response => response.text())
            .then(result => {
                 if (result) {
                    this.setState({bookFromShelf:JSON.parse(result)});
                    console.log(JSON.parse(result))
            }
        })

    }
    render() {

        if (!this.state.bookFromShelf) {
            return <div>didn't get a book</div>;
        }
        return (
            <div style={{flexGrow: 1}}>
                <PopupState variant="popover" popupId="demo-popup-popover">
                    {(popupState) => (
                        <div>
                            <Button style={{backgroundColor: "#5499C7", color: "white"}}
                                    {...bindTrigger(popupState)}>Show</Button>
                            <Popover
                                {...bindPopover(popupState)}
                                anchorReference="anchorPosition"
                                anchorPosition={{ top: 340, left: 800 }}
                                anchorOrigin={{
                                    vertical: 'bottom',
                                    horizontal: 'left',
                                }}
                                transformOrigin={{
                                    vertical: 'center',
                                    horizontal: 'left',
                                }}
                            >
                                <Box p={2}>
                                    <TableHead>
                                        <TableCell>Book Name</TableCell>
                                        <TableCell>Author Name</TableCell>
                                    </TableHead>
                                    {(this.state.rowsPerPage > 0
                                        ? this.state.bookFromShelf.slice(this.state.page * this.state.rowsPerPage,this.state.page * this.state.rowsPerPage + this.state.rowsPerPage)
                                        : this.state.bookFromShelf).map((row)=>
                                        <TableRow >
                                            <TableCell><div>{row.bookName}</div></TableCell>
                                            <TableCell><div>{row.author}</div></TableCell>
                                            <TableCell><Button
                                                component={ Link } to="/Book"
                                                style={{backgroundColor: "#5499C7", color: "white"}}
                                                >Show</Button></TableCell>
                                        </TableRow>
                                    )}
                                    <TablePagination
                                        count={50}
                                        page={this.state.page}
                                        onChangePage={this.handleChangePage}
                                        rowsPerPage={this.state.rowsPerPage}
                                        onChangeRowsPerPage={this.handleChangeRowsPerPage}
                                    />
                                </Box>
                            </Popover>
                        </div>
                    )}
                </PopupState>
            </div>
        );
    }
}