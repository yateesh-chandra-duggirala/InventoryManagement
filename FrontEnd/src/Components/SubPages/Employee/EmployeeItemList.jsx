import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import React,{useEffect} from 'react'
import '../../Common.scss'
import InputAdornment from '@mui/material/InputAdornment';
import {TableContainer,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    Paper,
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const EmployeeItemList = (props) =>{
    const {setSearchQuery1,searchQuery1,filteredItemData} = props;
    if(!filteredItemData){
        return <p>loading...</p>;
    }
    const handleSearchQueryChange = (event) => {
        setSearchQuery1(event.target.value);
    };
    const input = {
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
            width:"200px",
            height:"35px",
        },
        endAdornment: (
            <InputAdornment position="end">
              <SearchIcon sx={{ color: "white"}}/>
            </InputAdornment>
        ),
    };
    return (
        <Grid className="employee-body">
            <Grid className="grid-btn">
                <h1>Inventory</h1>
                <TextField variant="standard" className="button" placeholder="Search" color="primary" InputProps={input} value={searchQuery1} onChange={handleSearchQueryChange}></TextField>
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
                    {filteredItemData.length>0 ? (
                        <TableBody>
                            {filteredItemData.map((item) => (
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
                            <p align="center">No Items Assigned</p>
                        </Grid>
                    )}
                </Table>
            </TableContainer>
        </Grid>
    )
}

export default EmployeeItemList;