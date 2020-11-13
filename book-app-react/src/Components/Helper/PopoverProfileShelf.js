import React from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Popover from '@material-ui/core/Popover';
import PopupState, { bindTrigger, bindPopover } from 'material-ui-popup-state';
import ProfileShelf from "./ProfileShelf";
export default function PopoverPopupState() {
    return (
        <PopupState variant="popover" popupId="demo-popup-popover">
            {(popupState) => (
                <div>
                    <Button variant="contained" color="primary" {...bindTrigger(popupState)}>
                        Shelves
                    </Button>
                    <Popover
                        {...bindPopover(popupState)}
                        anchorReference="anchorPosition"
                        anchorPosition={{ top: 220, left: 20 }}
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
                            <ProfileShelf/>
                        </Box>
                    </Popover>
                </div>
            )}
        </PopupState>
    );
}
