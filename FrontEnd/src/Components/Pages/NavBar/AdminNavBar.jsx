import Grid from '@material-ui/core/Grid'
import React,{useState,useEffect} from 'react'
import '../../Common.scss'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import AdminBoard from '../../SubPages/Admin/AdminBoard';
import EmployeeList from '../../SubPages/Employee/EmployeeList';
import ItemList from '../../SubPages/Item/SideNavbar';
import EmployeeAssignedItems from '../../SubPages/Employee/EmployeeAssignedItems';
import AddEmployee from '../../SubPages/Employee/AddEmployee';
import axios from 'axios';
import EmployeeProfile from '../../SubPages/Employee/EmployeeProfile';
import ShowProfile from '../../SubPages/ShowProfile'

const AdminNavBar = (props) =>{
    const {setRenderComponent,userDetails} = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const [navigation,setNavigation] = useState({
        home:true,
        employee:false,
        items:false,
        profile:false,
        employeeAssigned:false,
        employeeProfile:false,
        addEmployee:false,
    });
    const [isDialogOpen,setIsDialogOpen] = useState(false);
    const [employeeDetails,setEmployeeDetails] = useState({});
    const [assignItemCount, setAssignItemCount] = useState(0);
    const [unAssignItemCount, setUnAssignItemCount] = useState(0);
    const [employeeCount, setEmployeeCount] = useState(0);
    const [employeeData,setEmployeeData] = useState([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [filteredEmployeeData, setFilteredEmployeeData] = useState([]);
    const [itemData,setItemData] = useState([]);
    const [searchQuery1, setSearchQuery1] = useState('');
    const [filteredItemData, setFilteredItemData] = useState([]);
    const [itemPopupData,setItemPopupData] = useState([])
    const [itemAssignData,setItemAssignData] = useState([]);
    const handleProfile = () => {
        setNavigation({...navigation,home:false,employee:false,items:false,profile:true,employeeAssigned:false,addEmployee:false});
        setAnchorEl(null);
    };
    const handleLogOut = () => {
        setAnchorEl(null);
        localStorage.setItem("jwtToken", "");
        setTimeout(function() {
            setRenderComponent("login");
        },1000);
    };
    const handleClose = () =>{
        setIsDialogOpen(false);
        setAnchorEl(null);
    };
    const handleCloseMenu = () =>{
        setAnchorEl(null);
    };
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleHome = ()=>{
        setNavigation({...navigation,home:true,employee:false,items:false,profile:false,employeeAssigned:false,addEmployee:false});
    };
    const handleEmployee = ()=>{
        setNavigation({...navigation,home:false,employee:true,items:false,profile:false,employeeAssigned:false,addEmployee:false});
    }
    const handleItem = ()=>{
        setNavigation({...navigation,home:false,employee:false,items:true,profile:false,employeeAssigned:false,addEmployee:false});
    }; 
    const getEmployeeData = async () =>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token
        };
        const response =await axios.get('http://localhost:6001/api/users/getemployees',{headers});
        const data = response?.data?.employees;
        setEmployeeData(data);
        setEmployeeCount(data?.length);
        const filteredData = data?.filter((employee) => {
            const empIdMatch = employee.userId.toString().includes(searchQuery.toLowerCase());
            const empNameMatch = `${employee.firstName} ${employee.lastName}`.toLowerCase().includes(searchQuery.toLowerCase());
            const empEmailMatch = employee.email.toLowerCase().includes(searchQuery.toLowerCase());
            return empIdMatch || empNameMatch || empEmailMatch;
        });
        setFilteredEmployeeData(filteredData);
    };
    const getItemData = async () =>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token
        };
        const assign = await axios.get('http://localhost:6001/api/inventory/InventoryItems/assign',{headers});
        setAssignItemCount(assign?.data?.items?.length);
        setItemAssignData(assign?.data?.items)
        try{
            const unassign = await axios.get('http://localhost:6001/api/inventory/InventoryItems/unassign',{headers});
            setUnAssignItemCount(unassign?.data?.items?.length);
            setItemPopupData(unassign?.data?.items);
        }catch(error) {
            console.error('Error fetching item data:', error);
        }
        const response =await axios.get('http://localhost:6001/api/inventory/InventoryItems',{headers});
        const data = response?.data?.items;
        setItemData(data);
        const filteredData = data?.filter((item) =>{
            const itemNameMatch = item.itemName.toLowerCase().includes(searchQuery1.toLowerCase());
            const itemIdMatch = item.itemId.toString().includes(searchQuery1.toLowerCase());
            const categoryMatch = item.category.toLowerCase().includes(searchQuery1.toLowerCase());
            const billNumberMatch = item.billNumber.toString().includes(searchQuery1.toLowerCase());    
            return itemNameMatch || itemIdMatch || categoryMatch || billNumberMatch;
        });
        setFilteredItemData(filteredData)
    };
    useEffect(() => {
        getEmployeeData();
        getItemData();
    },[searchQuery,searchQuery1]);
    return (
        <Grid className="adminnav-body">
            <Grid className='label'>
                <Grid className="logo">WalMart</Grid>
                <Button variant="standard" className="home" onClick={handleHome}>{navigation.home ? <u>Home</u>:<>Home</>}</Button>
                <Button variant="standard" className="employee" onClick={handleEmployee}>{navigation.employee ? <u>Employee</u>:<>Employee</>}</Button>
                <Button variant="standard" className="items" onClick={handleItem}>{navigation.items ? <u>Items</u>:<>Items</>}</Button>
                <IconButton aria-controls="simple-menu" aria-haspopup="true" onClick={handleClick}>
                    <AccountCircleIcon className="icon" sx={{fontSize:'40px', color:'white', marginTop:'-5px', position: 'absolute', marginLeft:'170px'}}/>
                </IconButton>
                <Menu
                    keepMounted
                    anchorEl={anchorEl}
                    style={{top: 50,marginLeft:'55px'}}
                    onClose={handleCloseMenu}
                    open={Boolean(anchorEl)}
                    autoFocus>
                    <MenuItem onClick={handleProfile}>Profile</MenuItem>
                    <MenuItem onClick={()=>{setIsDialogOpen(true)}}>Logout</MenuItem>
                </Menu>
            </Grid>
            {navigation ?.home && <AdminBoard
                assignItemCount={assignItemCount}
                unAssignItemCount={unAssignItemCount}
                employeeCount={employeeCount}/>
            }
            {navigation ?.employee && <EmployeeList
                setEmployeeDetails={setEmployeeDetails}
                setNavigation={setNavigation}
                navigation={navigation}
                employeeData={employeeData}
                setSearchQuery={setSearchQuery}
                searchQuery={searchQuery}
                filteredEmployeeData={filteredEmployeeData}
                getEmployeeData={getEmployeeData}
                getItemData={getItemData}/>
            }
            {navigation ?.items && <ItemList
                searchQuery1={searchQuery1}
                filteredItemData={filteredItemData}
                setSearchQuery1={setSearchQuery1}
                itemAssignData={itemAssignData}
                getItemData={getItemData}
                itemPopupData={itemPopupData}/>
            }
            {navigation ?.employeeProfile && <EmployeeProfile employeeDetails={employeeDetails}/>}
            {navigation ?.profile && <ShowProfile userDetails={userDetails}/>}
            {navigation ?.employeeAssigned && <EmployeeAssignedItems 
                employeeDetails={employeeDetails} 
                getAllItemData={getItemData} 
                itemData={itemPopupData} 
                setItemData={setItemPopupData}
                getItemData={getItemData}/>
            }
            {navigation ?.addEmployee && <AddEmployee/>}
            <Dialog 
                sx={{
                    "& .MuiDialog-container": {
                        "& .MuiPaper-root": {
                            width: "100%",
                            maxWidth: "400px", 
                            marginLeft:"80px"
                        },
                    },
                }} onClose={handleClose} open={isDialogOpen}>
                <DialogContent>
                    <DialogContentText>
                        Do you want to Logout?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" className="button"  onClick={handleLogOut}>Logout</Button>
                    <Button variant="contained" className="button" onClick={handleClose}>Stay</Button>
                </DialogActions>
            </Dialog>
        </Grid>
    )
}

export default AdminNavBar;