import React, {useEffect} from 'react';
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import TextField from "@material-ui/core/TextField";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";
import {Typography} from "@material-ui/core";
import Radio from '@material-ui/core/Radio';
import AddBoxIcon from "@material-ui/icons/AddBox";
import {Link} from "react-router-dom";
import Table from "@material-ui/core/Table";
import AddNewTag from "./AddNewTag";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import Input from "@material-ui/core/Input";
import MenuItem from "@material-ui/core/MenuItem";
import Checkbox from "@material-ui/core/Checkbox";
import ListItemText from "@material-ui/core/ListItemText";


const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

export default class AddNewShelf extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            open:false,
            shelfName:"",
            genreName:[],
            genres:[]
        };
        this.createNewShelf = this.createNewShelf.bind(this);
    }

    onChangeShelfName = (e) => {
        this.setState({shelfName:e.target.value});
    };

    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    onChangeGenreName = (e) => {
        this.setState({genreName:e.target.value});
    };


    createNewShelf(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");

        console.log(this.state.genreName)
        let raw = JSON.stringify({
            "shelfname":this.state.shelfName,
            "tags":["kfjd"],
           });

        let requestOptions = {
            method: 'POST',
            body:raw,
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/api/shelf/new", requestOptions)
            .then(response => response.json())
            .then(result => {
                    if (result.status === 400 || result.status === 500) {
                        alert("\n" + result.message)
                    } else {
                        alert("New shelf created. Please refresh..")
                        window.location.reload();
                    }
                }
            )
            .catch(error => console.log('error', error));
    }

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };


        fetch("http://localhost:8083/api/shelf/tags", requestOptions)
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
        return (
            <div>
                <td> <Button onClick={this.handleClickOpen} style={{marginLeft:"17%"}}> <AddBoxIcon color="primary"/> </Button></td>
                <Dialog
                    fullWidth={"xs"}
                    maxWidth={"xs"}
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <Typography
                            style={{marginLeft:"35%"}}>Add New Shelf</Typography>

                                <form noValidate autoComplete="off" style={{marginLeft:"25%",width:"50%"}}>
                                    <TextField
                                        style={{backgroundColor:"white"}}
                                        id="standard-basic"
                                        label="Shelf Name"
                                        value={this.state.shelfName}
                                        onChange={this.onChangeShelfName}
                                    />
                                </form>
                         <br/>
                        <FormControl style={{marginLeft:"25%",width:"50%"}}>
                            <InputLabel id="demo-mutiple-checkbox-label">Tag</InputLabel>
                            <Select
                                labelId="demo-mutiple-checkbox-label"
                                id="demo-mutiple-checkbox"
                                multiple
                                value={this.state.genreName}
                                onChange={this.onChangeGenreName}
                                input={<Input />}
                                renderValue={(selected) => selected.join(', ')}
                                MenuProps={MenuProps}
                            >
                                {this.state.genres.map((name) => (
                                    <MenuItem key={name.tag} value={name.tag}>
                                        <Checkbox checked={this.state.genreName.indexOf(name.tag) > -1} />
                                        <ListItemText primary={name.tag} />
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={()=>this.createNewShelf()} color="primary">
                            Add
                        </Button>
                        <Button onClick={this.handleClose} color="primary">
                            Close
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}
