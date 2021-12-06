import React, {useEffect} from 'react';
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import TextField from "@material-ui/core/TextField";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import AuthService from "../../../Service/AuthService";
import {Typography} from "@material-ui/core";

export default function DialogSelect(props) {
    const [open, setOpen] = React.useState(false);
    const [firstName, setFirstName] = React.useState();
    const [lastName, setLastName] = React.useState();
    const [userInfo, setUserInfo] = React.useState(   []);
    const [textAboutMe, setTextAboutMe] = React.useState(   "");

    useEffect(()=>{
        loadData();
    },[]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const onChangeFirstName = (e) => {
       setFirstName(e.target.value);
    };

    const onChangeLastName = (e) => {
        setLastName(e.target.value);
    };

    const onChangeTextAboutMe = (e) => {
        setTextAboutMe(e.target.value);
    };

    const loadData=async ()=>{
        let requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("http://localhost:9191/api/profile/"+AuthService.getCurrentUserName(), requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    setUserInfo(JSON.parse(result));
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Authorization service temporarily is offline for maintenance.")
        })
    }


    const edit = () => {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Basic bW9iaWxlOnBpbg==");
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify(
            {"firstname":firstName,
                "lastname":lastName,
                "aboutMe":textAboutMe
            });

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:9191/api/profile/"+AuthService.getCurrentUserName(), requestOptions)
            .then(response => response.text())
            .then(result =>{
                if (result.slice(10,23)!=="invalid_token") {
                    alert("Your profile is updated")
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Authorization service temporarily is offline for maintenance.")
        })
    }


    return (
        <div>
            <Button
                variant="contained"
                style={{backgroundColor:"#95A5A6",color:"white",marginLeft:"80%"}}
                id="changeBorder"
                onClick={handleClickOpen}>Profile</Button>
            <Dialog

                disableBackdropClick disableEscapeKeyDown open={open} onClose={handleClose}>
                <DialogContent>
                    <Typography
                        style={{marginLeft:"45%"}}>Profile</Typography>
                    <TableRow>
                   <TableCell> <TextField
                        id="standard-read-only-input"
                        label="Username"
                        defaultValue={userInfo.username}
                        InputProps={{
                            readOnly: true,
                        }}
                    /></TableCell>
                        <TableCell> <TextField
                            id="standard-read-only-input"
                            label="Email"
                            defaultValue={userInfo.email}
                            InputProps={{
                                readOnly: true,
                            }}
                        /></TableCell></TableRow>
                    <TableRow>
                    <TableCell><form noValidate autoComplete="off">
                        <TextField
                            style={{backgroundColor:"white"}}
                            id="standard-basic"
                            label="FirstName"
                            defaultValue={userInfo.firstname}
                            value={firstName}
                            onChange={onChangeFirstName}
                            inputProps={{ maxLength: 25 }}
                        />
                    </form></TableCell>
                    <TableCell><form noValidate autoComplete="off">
                        <TextField
                            style={{backgroundColor:"white"}}
                            id="standard-basic"
                            label="LastName"
                            defaultValue={userInfo.lastname}
                            inputProps={{ maxLength: 25 }}
                            value={lastName}
                            onChange={onChangeLastName}
                        />
                    </form></TableCell></TableRow><br/><br/>
                    <Typography
                        style={{marginLeft:"5%"}}>About Me</Typography>
                    <textarea  style={{marginLeft:"5%"}} rows="7" cols="50"
                               defaultValue={userInfo.aboutMe}
                               value={textAboutMe} onChange={onChangeTextAboutMe} />

                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>{edit()}} color="primary">
                        Edit
                    </Button>
                    <Button onClick={handleClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}