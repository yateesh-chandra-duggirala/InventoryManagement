import Grid from '@material-ui/core/Grid';
import React,{useState,useEffect} from 'react'
import '../../Common.scss'
import {TableContainer,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    Paper,
} from '@mui/material';
import AddItem from './AddItem'
import Dialog from '@mui/material/Dialog';

const UnAssignedItems = (props) =>{
    const {itemPopupData,getItemData} = props;
    const [isUnassignPopupOpen,setIsUnassignPopupOpen] = useState(false);
    useEffect(() => {
        getItemData();
    },[]);
    return (
        <Grid className="employee-body">
            <Grid className="grid-btn">
                <h1>UnAssigned Items</h1>
            </Grid>
            <TableContainer component={Paper} className="app-container">
                <Table aria-label='table'>
                    <TableHead>
                        <TableRow>
                            <TableCell>Item Id</TableCell>
                            <TableCell>Item Name</TableCell>
                            <TableCell>Category</TableCell>
                            <TableCell>Bill Number</TableCell>
                            <TableCell>Date of Purchase</TableCell>
                            <TableCell>Warranty</TableCell>
                            <TableCell>Expire date</TableCell>
                        </TableRow>
                    </TableHead>
                    {itemPopupData?.length>0 ? (
                        <TableBody>
                            {itemPopupData?.map((item) => (
                                <TableRow 
                                    key = {item.itemId}
                                    sx = {{ '&:last-child td, &:last-child th': {border:0} }}>
                                    <TableCell>{item.itemId}</TableCell>
                                    <TableCell>{item.itemName}</TableCell>
                                    <TableCell>{item.category}</TableCell>
                                    <TableCell>{item.billNumber}</TableCell>
                                    <TableCell>{item.dateOfPurchase}</TableCell>
                                    <TableCell>{item.warranty}</TableCell>
                                    <TableCell>{item.expireDate}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                        ):(
                        <Grid>
                            <p align="center">No Records Found</p>
                        </Grid>
                    )}
                </Table>
            </TableContainer>
            <Dialog 
                sx={{
                    "& .MuiDialog-container": {
                        "& .MuiPaper-root": {
                            width: "800px", 
                            height:"400px",
                            borderRadius:"10px"
                        },
                    },
                }} onClose={()=>{setIsUnassignPopupOpen(false)}} open={isUnassignPopupOpen}>
                <AddItem/>
            </Dialog>
        </Grid>
    )
}

export default UnAssignedItems;