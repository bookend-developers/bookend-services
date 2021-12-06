import React from 'react';
import AuthService from "../../../Service/AuthService";
import Popover from '@material-ui/core/Popover';
import PopupState, { bindTrigger, bindPopover } from 'material-ui-popup-state';
import Button from '@material-ui/core/Button';
import Box from '@material-ui/core/Box';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Checkbox from '@material-ui/core/Checkbox';

export default class PopOverShelf extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            propsBookId: "",
            shelfId:0,
            shelves:[]
        };

    }
    static getDerivedStateFromProps(props, state) {
        console.warn("hook",props,state)
        return {
            propsBookId: props.data
        }
    }
    changeShelfId(newId){
        this.setState({shelfId:newId});
    }

    addToShelf(){
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+AuthService.getCurrentUser());

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/api/shelf/"+this.state.shelfId+"/"+this.state.propsBookId, requestOptions)
            .then(response => response.json())
            .then(result => {
                    if (result.status === 400) {
                        alert("\n" + result.message)
                    }
                }
            )
            .catch((err)=> {
                alert("Shelf service temporarily is offline for maintenance.")
            })
    }

    componentDidMount() {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Bearer "+ AuthService.getCurrentUser());

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:8083/api/shelf/user/"+AuthService.getCurrentUserName(), requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    this.setState({shelves:JSON.parse(result)});
                    console.log(JSON.parse(result))
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
            }).catch((err)=> {
            alert("Shelf service temporarily is offline for maintenance.")
        })
    }

    render(){
        return (
            <div>
                <PopupState variant="popover" popupId="demo-popup-popover">
                    {(popupState) => (
                        <div>
                            <Button style={{backgroundColor: "#5499C7", color: "white"}} {...bindTrigger(popupState)}>Add to
                                Shelf</Button>
                            <Popover
                                {...bindPopover(popupState)}
                                anchorReference="anchorPosition"
                                anchorPosition={{ top: 270, left: 80 }}
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
                                    {this.state.shelves.map((row)=>
                                        <TableRow >
                                            <TableCell style={{marginLeft: "2%"}}>Shelf Name:</TableCell>
                                            <TableCell><div>{row.shelfname}</div></TableCell>
                                            <TableCell>
                                                <Checkbox
                                                defaultUnChecked
                                                style={{marginTop:10}}
                                                color="primary"
                                                inputProps={{ 'aria-label': 'secondary checkbox' }}
                                                onClick={()=>{this.changeShelfId(row.id)}}
                                            />
                                            </TableCell>
                                            <TableCell><Button
                                                style={{backgroundColor: "#5499C7", color: "white"}}
                                            onClick={()=>{this.addToShelf();}}>Add</Button></TableCell>
                                        </TableRow>
                                    )}
                                </Box>
                            </Popover>
                        </div>
                    )}
                </PopupState>
            </div>
        );
    }
}