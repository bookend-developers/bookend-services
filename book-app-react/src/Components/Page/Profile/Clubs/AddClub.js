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
import AddBoxIcon from '@material-ui/icons/AddBox';

export default function AddClub(props) {
    const [open, setOpen] = React.useState(false);
    const [clubName, setClubName] = React.useState("");
    const [privateOrNot, setPrivateOrNot] = React.useState("");
    const [description, setDescription] = React.useState(   "");

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleRadioChange = (event) => {
        setPrivateOrNot(event.target.value);
    };

    const onChangeClubName = (e) => {
        setClubName(e.target.value);
    };

    const onChangeDescription = (e) => {
        setDescription(e.target.value);
    };

    const checkAddClub =()=>{
        if(description!=="" && clubName!=="" && privateOrNot!==""){
            console.log(privateOrNot==="yes")
            let bool;
            if(privateOrNot==="yes"){
                bool=true;
            }else{
                bool=false;
            }
            console.log(bool)
            handleAddClub(bool);
        }else{
            alert("All fields must be filled.")
        }
    }

    const handleAddClub = (private_value) => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());
        myHeaders.append("Content-Type", "application/json");


        let raw = JSON.stringify({
            "clubName":clubName,
            "description":description,
            "username":AuthService.getCurrentUserName(),
            "privatee":private_value
           });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8089/api/club/add", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10, 23) !== "invalid_token") {
                    if(JSON.parse(result).status !==400 && JSON.parse(result).status!==500){
                        alert("Added successfully")
                        window.location.reload()
                    }
                    else {
                        alert("Club name is already used")
                    }
                } else {
                    this.props.history.push("/");
                    window.location.reload();
                }
            })
}


    return (
        <div>
            <td> <Button onClick={handleClickOpen} style={{marginLeft:"17%"}}> <AddBoxIcon color="primary"/> </Button></td>
            <Dialog
                disableBackdropClick disableEscapeKeyDown open={open} onClose={handleClose}>
                <DialogContent>
                    <Typography
                        style={{marginLeft:"35%"}}>Adding Club</Typography>
                    <TableRow>
                        <TableCell><form noValidate autoComplete="off">
                            <TextField
                                style={{backgroundColor:"white"}}
                                id="standard-basic"
                                label="Club Name"
                                value={clubName}
                                onChange={onChangeClubName}
                            />
                        </form></TableCell>
                        <TableCell><Typography>Private:</Typography></TableCell>
                       <div><td><Typography>Yes</Typography></td>
                           <td><Radio
                            checked={privateOrNot === "yes"}
                            onChange={handleRadioChange}
                            value="yes"
                            name="radio-button-demo"
                            aria-label="Yes"
                           /></td></div>
                        <div><td><Typography> No</Typography></td>
                        <td><Radio
                            checked={privateOrNot === "no"}
                            onChange={handleRadioChange}
                            value="no"
                            name="radio-button-demo"
                            aria-label="No"
                        /></td></div>
                    </TableRow>
                    <Typography
                        style={{marginLeft:"5%"}}>Description</Typography>
                    <textarea  style={{marginLeft:"5%"}} rows="7" cols="50"
                               value={description} onChange={onChangeDescription} />

                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>checkAddClub()} color="primary">
                        Add
                    </Button>
                    <Button onClick={handleClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
