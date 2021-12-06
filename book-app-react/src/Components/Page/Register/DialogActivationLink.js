import React from 'react';
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import TextField from "@material-ui/core/TextField";

export default function DialogSelect(props) {
    const [open, setOpen] = React.useState(false);
    const [activationLink, setActivationLink] = React.useState("");

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const onChangeLink=(e)=>{
        setActivationLink(e.target.value);
    }

    const handleActivation=()=>{
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://"+activationLink.trim(), requestOptions)
            .then(response => response.text())
            .then(result => {
                if(result!=="Confirmation completed"){
                    alert("\n" +JSON.parse(result).message)
                }else{
                    alert(result)
                }
            }).catch((err)=> {
            alert("Authorization service temporarily is offline for maintenance.")
        })
    }

    return (
        <div>
            <Button
                style={{marginTop:"5%",marginLeft:"30%"}}
                variant="outlined"
                id="changeBorder"
                onClick={handleClickOpen}>ACTIVATION LINK</Button>
            <Dialog
                fullWidth={"md"}
                maxWidth={"md"}
                disableBackdropClick disableEscapeKeyDown open={open} onClose={handleClose}>
                <DialogContent>
                        <form onSubmit={handleActivation} style={{marginTop:"5%",marginLeft:"3%"}}>
                            <div className="form-group">
                                <TextField
                                    required
                                    style={{backgroundColor:"white",width:"100%"}}
                                    variant="outlined"
                                    id="input-with-icon-textfield"
                                    label="Activation Link"
                                    value={activationLink}
                                    onChange={onChangeLink}
                                />
                            </div><br/>
                        </form>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Close
                    </Button>
                    <Button onClick={()=>
                    {
                        if(activationLink!==""){handleActivation();}
                        else{alert("Field must be filled")}
                    }} color="primary">
                        Send
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}