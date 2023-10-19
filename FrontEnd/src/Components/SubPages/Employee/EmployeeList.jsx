import Grid from '@material-ui/core/Grid'
import TextField from '@material-ui/core/TextField';
import React,{useState,useEffect} from 'react'
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
import axios from 'axios';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import SearchIcon from '@mui/icons-material/Search';
import Dialog from '@mui/material/Dialog';
import AddEmployee from './AddEmployee';
import MuiAlert from '@mui/material/Alert';
const EmployeeList= (props) =>{
    const {setEmployeeDetails,navigation,setNavigation,setSearchQuery,getItemData,filteredEmployeeData,searchQuery,getEmployeeData} = props;
    const [isUnassignPopupOpen,setIsUnassignPopupOpen] = useState(false);
    const [systemErrors,setSystemErrors] = useState("");
    const handleSearchQueryChange = (event) => {
        setSearchQuery(event.target.value);
    };
    const Alert = React.forwardRef(function Alert(props, ref) {
        return <MuiAlert ref={ref} variant="filled" {...props} />;
    });
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
    const handleAdd = () =>{
        setIsUnassignPopupOpen(true);
    };
    useEffect(() => {
        getEmployeeData();
        getItemData();
    },[]);
    return (
        <Grid className="employee-body">
            <Grid className="grid-btn">
                <h1>EMPLOYEES</h1>
                {systemErrors?.networkError?.length>0 && <Alert severity="error" style={{width:'400px', position:"absolute", marginLeft:'920px', marginTop:'-60px'}}>{systemErrors?.networkError}</Alert>}   
                {systemErrors?.response?.length>0 && <Alert severity="success" style={{width:'400px', position:"absolute", marginLeft:'920px', marginTop:'-60px'}}>{systemErrors?.response}</Alert>} 
                <Button variant="contained" color="primary" size="medium" onClick={handleAdd} className="buttonnew"><AddIcon/>Add Employee</Button>
                <TextField variant="standard" className="button" color="primary" placeholder='Search' InputProps={input} value={searchQuery} onChange={handleSearchQueryChange}></TextField>
            </Grid>
            <TableContainer component={Paper} className="app-container">
                <Table aria-label='table'>
                    <TableHead>
                        <TableRow>
                            <TableCell>User ID</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>First Name</TableCell>
                            <TableCell>Last Name</TableCell>
                            <TableCell>Date Of Birth</TableCell>
                            <TableCell>Age</TableCell>
                            <TableCell>Mobile Number</TableCell>
                            <TableCell align="center">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    {filteredEmployeeData.length>0 ? (
                        <TableBody>
                            {filteredEmployeeData.map((employee) => (
                                <TableRow 
                                    key = {employee.userId}
                                    sx = {{ '&:last-child td, &:last-child th': {border:0} }}>
                                    <TableCell>{employee.userId}</TableCell>
                                    <TableCell>{employee.email}</TableCell>
                                    <TableCell>{employee.firstName}</TableCell>
                                    <TableCell>{employee.lastName}</TableCell>
                                    <TableCell>{employee.dateOfBirth}</TableCell>
                                    <TableCell>{employee.age}</TableCell>
                                    <TableCell>{employee.mobileNumber}</TableCell>
                                    <TableCell align="center" scope="row" component="th">
                                        <Grid style={{display:'flex'}}>
                                            <Button variant="contained" 
                                                style={{marginLeft:'10px'}} 
                                                onClick={()=>{
                                                    const token = localStorage.getItem("jwtToken");
                                                    const headers = {
                                                        'Authorization': token
                                                    };
                                                    try{
                                                        axios.delete(`http://localhost:6001/api/users/deleteusers/${employee.userId}`,{headers});
                                                        getEmployeeData();
                                                        getItemData();
                                                    }catch(error) {
                                                        console.error('Error deleting employee:', error);
                                                    }
                                                }} 
                                                color="error" 
                                                size="small">Delete
                                            </Button>
                                            <Button variant="contained" size="small"
                                                style={{marginLeft:'10px'}} 
                                                onClick={()=>{
                                                getItemData();
                                                setEmployeeDetails(employee);
                                                setNavigation({...navigation,
                                                    home:false,
                                                    employee:false,
                                                    items:false,
                                                    profile:false,
                                                    employeeAssigned:true,
                                                    addEmployee:false
                                                })}}>Inventory Details
                                            </Button>
                                        </Grid>
                                    </TableCell>
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
                            height:"420px",
                            borderRadius:"10px"
                        },
                    },
                }} onClose={()=>{setIsUnassignPopupOpen(false)}} open={isUnassignPopupOpen} >
                <AddEmployee systemErrors={systemErrors} setSystemErrors={setSystemErrors} getEmployeeData={getEmployeeData} setIsUnassignPopupOpen={setIsUnassignPopupOpen}/>
            </Dialog>
        </Grid>
    )
}

export default EmployeeList;