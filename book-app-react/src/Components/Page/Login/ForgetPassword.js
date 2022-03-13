import React from 'react';
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import TextField from "@material-ui/core/TextField";
import Link from "@material-ui/core/Link";
import { useHistory } from "react-router";

export default function DialogSelect(props) {
    const [open, setOpen] = React.useState(false);
    const [email, setEmail] = React.useState("");
    const history = useHistory();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const onChangeEmail=(e)=>{
        setEmail(e.target.value);
    }

    const handleControlEmail =()=>{
        if(email!=="") {
            if (!(email.includes(".com", 0) && email.includes("@", 0))) {
                alert("Invalid email");
            } else {
                handleEmail();
            }
        }
        else{alert("Field must be filled")}
    }

    const handleEmail=()=>{
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Basic bW9iaWxlOnBpbg==");
        myHeaders.append("Content-Type", "application/json");

        let raw = JSON.stringify({"email":email});

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:9191/api/oauth/resetPassword", requestOptions)
            .then(response => response.text())
            .then(result => {
                if (JSON.parse(result).error === "Not Found" && JSON.parse(result).status!==500 ) {
                        alert("Not found..")
                }else{
                    alert(JSON.parse(result).message)
                    history.push({
                        pathname:  "/reset-password",
                    });
                }
            }).catch((err)=> {
            alert("Authorization service temporarily is offline for maintenance.")
        })
    }

    return (
        <div>

            <Button
                style={{marginTop:"2%",marginLeft:"30%"}}
                onClick={handleClickOpen}>Forget Password?</Button>
            <Dialog
                fullWidth={"xs"}
                maxWidth={"xs"}
                disableBackdropClick disableEscapeKeyDown open={open} onClose={handleClose}>
                <DialogContent>
                    <form onSubmit={handleEmail} style={{marginTop:"5%",marginLeft:"3%"}}>
                        <div className="form-group">
                            <TextField
                                required
                                style={{backgroundColor:"white",width:"100%"}}
                                variant="outlined"
                                id="input-with-icon-textfield"
                                label="Email"
                                value={email}
                                onChange={onChangeEmail}
                            />
                        </div><br/>
                    </form>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Close
                    </Button>
                    <Button
                            onClick={handleControlEmail}color="primary">
                        Send
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}