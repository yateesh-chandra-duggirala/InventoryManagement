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
import EmployeeBoard from '../../SubPages/Employee/EmployeeBoard';
import axios from 'axios';
import EmployeeItemList from '../../SubPages/Employee/EmployeeItemList';
import ShowProfile from '../../SubPages/ShowProfile';

const EmployeeNavbar = (props) =>{
    const {setRenderComponent,userDetails} = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const [navigation,setNavigation] = useState({
        home:true,
        items:false,
        profile:false
    });
    const [isDialogOpen,setIsDialogOpen] = useState(false);
    const [searchQuery1, setSearchQuery1] = useState('');
    const [filteredItemData, setFilteredItemData] = useState([]);
    const [count,setCount] = useState(0);
    const handleProfile = () => {
        setNavigation({...navigation,home:false,items:false,profile:true});
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
        setNavigation({...navigation,home:true,items:false,profile:false});
    };
    const handleItem = ()=>{
        setNavigation({...navigation,home:false,items:true,profile:false});
    };
    const getItemData = async () =>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token,
            'content-type': 'application/json'
        };
        const response =await axios.get(`http://localhost:6001/api/employeeitems/assignitems/${userDetails.userId}`,{headers});
        const data = response?.data?.employeeItems;
        const filteredData = data?.filter((item) =>{
            const itemNameMatch = item.itemName.toLowerCase().includes(searchQuery1.toLowerCase());
            const itemIdMatch = item.itemId.toString().includes(searchQuery1.toLowerCase());
            const categoryMatch = item.category.toLowerCase().includes(searchQuery1.toLowerCase());
            const billNumberMatch = item.billNumber.toString().includes(searchQuery1.toLowerCase());    
            return itemNameMatch || itemIdMatch || categoryMatch || billNumberMatch;
        });
        setFilteredItemData(filteredData)
        if(data === null){
            setFilteredItemData([])
        }
        setCount(data.length);
    };
    useEffect(() => {
        getItemData();
    },[searchQuery1]);
    return (
        <Grid className="employeenav-body">
            <Grid className='label'>
                <Grid className="logo">WalMart</Grid>
                <Button variant="standard" className="home" onClick={handleHome}>{navigation.home ? <u>Home</u>:<>Home</>}</Button>
                <Button variant="standard" className="items" onClick={handleItem}>{navigation.items ? <u>Inventory</u>:<>Inventory</>}</Button>
                <IconButton aria-controls="simple-menu" aria-haspopup="true" onClick={handleClick}>
                    <AccountCircleIcon className="icon" sx={{fontSize:'40px', color:'white', marginTop:'-5px', position: 'absolute', marginLeft:'140px'}}/>
                </IconButton>
                <Menu
                    keepMounted
                    anchorEl={anchorEl}
                    style={{top: 50,marginLeft:'40px'}}
                    onClose={handleCloseMenu}
                    open={Boolean(anchorEl)}
                    autoFocus>
                    <MenuItem onClick={handleProfile}>Profile</MenuItem>
                    <MenuItem onClick={()=>{setIsDialogOpen(true)}}>Logout</MenuItem>
                </Menu>
            </Grid>
            {navigation ?.home && <EmployeeBoard count={count} userDetails={userDetails}/>}
            {navigation ?.items && <EmployeeItemList
            searchQuery1={searchQuery1}
            filteredItemData={filteredItemData}
            userDetails={userDetails}
            setSearchQuery1={setSearchQuery1}/>}
            {navigation ?.profile && <ShowProfile userDetails={userDetails}/>}
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

export default EmployeeNavbar;