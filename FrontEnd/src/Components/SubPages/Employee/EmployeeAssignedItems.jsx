import Grid from '@material-ui/core/Grid'
import React,{useState,useEffect} from 'react'
import '../../Common.scss'
import Dialog from '@mui/material/Dialog';
import CloseIcon from '@mui/icons-material/Close';
import DialogActions from '@mui/material/DialogActions';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import {TableContainer,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    Paper,
} from '@mui/material';
import IconButton from '@mui/material/IconButton';
import axios from 'axios';
import EmployeeProfile from './EmployeeProfile';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import ItemsPopUp from'../Item/ItemsPopUp';
import MuiAlert from '@mui/material/Alert';

const EmployeeAssignedItems = (props) =>{
    const {employeeDetails,itemData,setItemData,getAllItemData,getItemData} = props;
    const [assignedItemData,setAssignedItemData] = useState([]);
    const [selectedItems,setSelectedItems] = useState([]);
    const [isUnassignPopupOpen,setIsUnassignPopupOpen] = useState(false);
    const [systemErrors,setSystemErrors] = useState("");
    const Alert = React.forwardRef(function Alert(props, ref) {
        return <MuiAlert ref={ref} variant="filled" {...props} />;
    });
    const  getEmployeeData= async () =>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token
        };
        const response =await axios.get(`http://localhost:6001/api/employeeitems/assignitems/${employeeDetails?.userId}`,{headers})
        setAssignedItemData(response?.data?.employeeItems);
        if(response?.data?.employeeItems === null){
            setAssignedItemData([]);
        }
    };
    const handleAssign = () =>{
        setIsUnassignPopupOpen(true);
    };
    const handleAdd = () =>{
        let assignedItems = [];
        let unAssignedItems = itemData;
        assignedItems = [...assignedItemData,...selectedItems];
        itemData?.map(item=>{
            selectedItems?.map(selectedItem=>{
                if(item?.itemId == selectedItem?.itemId){
                    let index = itemData.indexOf(item);
                    unAssignedItems.splice(index,selectedItems.length);
                }
            });
        });
        setIsUnassignPopupOpen(false);
        setAssignedItemData(assignedItems);
        setItemData(unAssignedItems);
    };
    const getUnassignedData =async() =>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token
        };
        try{
            const unassign =await axios.get('http://localhost:6001/api/inventory/InventoryItems/unassign',{headers});
            setItemData(unassign?.data?.items);
        }catch(error) {
            console.error('Error fetching item data:', error);
        }
    }
    const handlePost=(payload)=>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token,
            'content-type': 'application/json'
        };
        axios.post('http://localhost:6001/api/employeeitems/assignitems',payload,{headers})
        .then(response=>{
            if(response?.status==200){
                setSystemErrors({...systemErrors,response:'Updated Successfully'});
                setTimeout(function() {
                    setSystemErrors({...systemErrors,response:''});
                }, 2000);
                getUnassignedData();
                getAllItemData();
            }
        }).catch(error=>{
            if(error?.message=="Network Error"){
                setSystemErrors({...systemErrors,networkError:error?.message})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 2000);
            }
        });
    }
    const handlePut =(payload)=>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token,
            'content-type': 'application/json'
        };
        axios.put(`http://localhost:6001/api/employeeitems/unassignitems/${employeeDetails?.userId}`,payload,{headers})
        .then(response=>{
            if(response?.status==200){
                setSystemErrors({...systemErrors,response:'Updated Successfully'});
                setTimeout(function() {
                    setSystemErrors({...systemErrors,response:''});
                }, 2000);
                getUnassignedData();
            }
        }).catch(error=>{
            if(error?.message=="Network Error"){
                setSystemErrors({...systemErrors,networkError:error?.message})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 2000);
            }
        });
    }
    const handleUpdate =() =>{
        let payload = {
            empId: employeeDetails?.userId,
            assignedItems:[]
        }
        assignedItemData?.map(item=>{
            payload?.assignedItems.push(item?.itemId);
        })
        handlePut(payload);
        handlePost(payload);
    };
    useEffect(() => {
        getEmployeeData();
    },[]);
    return (
        <Grid className="inventory-body">
            <Grid className="grid-btn">
                <h1>Inventory Details</h1>
            </Grid>
            {systemErrors?.networkError?.length>0 && <Alert severity="error" style={{width:'400px', position:"absolute", marginLeft:'920px', marginTop:'140px'}}>{systemErrors?.networkError}</Alert>}   
            {systemErrors?.response?.length>0 && <Alert severity="success" style={{width:'400px', position:"absolute", marginLeft:'920px', marginTop:'140px'}}>{systemErrors?.response}</Alert>} 
            <Grid className="btn-grid">
                <Button variant="contained" size="medium" className="btn1" onClick={handleUpdate}>Update</Button>
                <Button variant="contained" size="medium" onClick={handleAssign} className="btn"><AddIcon/>Assign More</Button>
            </Grid>
            {assignedItemData?.length>0 ? (
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
                                <TableCell>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {assignedItemData.map((item) => (
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
                                    <TableCell align="center" scope="row" component="th">
                                        <Grid style={{display:'flex'}}>
                                            <Button variant="contained" style={{marginLeft:'10px',backgroundColor:"black"}} 
                                                onClick={()=>{
                                                    let deletedItems =[];
                                                    assignedItemData.map((row)=>{
                                                        if(row.itemId != item.itemId){
                                                            deletedItems.push(row);
                                                        }
                                                    })
                                                    setAssignedItemData(deletedItems);
                                                    setItemData([...itemData,item]);
                                                }} size="small">
                                                <DeleteIcon/>
                                            </Button>
                                        </Grid>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                ):(
                <Grid className="last-grid">
                    <h1>No Items Assigned</h1>
                </Grid>
            )}
            <Dialog 
                sx={{
                    "& .MuiDialog-container": {
                        "& .MuiPaper-root": {
                            width: "800px", 
                            height:"500px",
                            marginLeft:"80px"
                        },
                    },
                }} onClose={()=>{setIsUnassignPopupOpen(false)}} open={isUnassignPopupOpen} >
                <DialogTitle sx={{height: '40px',marginTop:'-11px',marginBottom:'10px'}}>
                    <h3>Inventory Items</h3>
                    <IconButton onClick={()=>{setIsUnassignPopupOpen(false)}} style={{marginLeft:'520px',marginTop:'-130px'}}>
                        <CloseIcon/>
                    </IconButton>
                </DialogTitle>
                <DialogContent sx={{overflowY:"hidden"}}>
                    <ItemsPopUp selectedItems={selectedItems} setSelectedItems={setSelectedItems} itemData={itemData} />
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" className="button"  onClick={()=>{setIsUnassignPopupOpen(false)}}>Cancel</Button>
                    <Button variant="contained" className="button" onClick={handleAdd}>Add</Button>
                </DialogActions>
            </Dialog>
            <Grid className="profile">
                <h3><EmployeeProfile userId={employeeDetails?.userId}/></h3>
            </Grid>
        </Grid>
    )
}

export default EmployeeAssignedItems;