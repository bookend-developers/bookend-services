import React, {useEffect} from 'react';
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import TextField from "@material-ui/core/TextField";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../../Service/AuthService";


export default class UpdateGenre extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            new_genre: "",
            open: false,
        };
    }
    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsGenreId: props.data
        }
    }

    handleClickOpen = () => {
        this.setState({open: true});
    };
    onChangeNewGenre = (e) => {
        this.setState({new_genre: e.target.value})
    }

    handleClose = () => {
        this.setState({open: false});
    };


    handleUpdateGenre = (genre) =>{
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        let raw = JSON.stringify({"genre":genre,
            "id":this.state.propsGenreId})
        let requestOptions = {
            method: 'POST',
            body: raw,
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8082/api/book/admin/genre", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(JSON.parse(result).genre === genre && JSON.parse(result).status!==400){
                        alert("Updated successfully")
                        window.location.reload();
                    }else{
                        alert(JSON.parse(result).message)
                    }

                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
            .catch(error => console.log('error', error));
    }




    render() {

        return (
            <div>
                <Button
                    style={{textAlign:"center",marginTop:10,marginLeft:"38%",backgroundColor:"#148F77",color:"white"}}
                    onClick={this.handleClickOpen}>Update</Button>

                <Dialog
                    fullWidth={"xs"}
                    maxWidth={"xs"}
                    disableBackdropClick disableEscapeKeyDown open={this.state.open} onClose={this.handleClose}>
                    <DialogContent>
                        <TableCell><form noValidate autoComplete="off">
                            <TextField
                                style={{backgroundColor:"white"}}
                                id="standard-basic"
                                label="Update"
                                value={this.state.new_genre}
                                onChange={this.onChangeNewGenre}
                            />
                        </form></TableCell>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={()=>this.handleUpdateGenre(this.state.new_genre)} color="primary">
                            Send
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

