import React, { useState } from 'react';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import ItemList from './ItemList';
import AssignedItems from './AssignedItems';
import UnAssignedItems from './UnAssignedItems';

const SideNavbar = (props) => {
  const {setSearchQuery1,searchQuery1,filteredItemData,getItemData,itemAssignData,itemPopupData} = props;
  const [selectedNavItem, setSelectedNavItem] = useState('All Items');
  const handleNavItemChange = (event,newValue) => {
    setSelectedNavItem(newValue);
  };
  const renderPage = () => {
    switch (selectedNavItem) {
      case 'All Items':
        return <ItemList 
          searchQuery1={searchQuery1}
          filteredItemData={filteredItemData}
          setSearchQuery1={setSearchQuery1}
          getItemData={getItemData}
        />;
      case 'Assigned Items':
        return (<AssignedItems itemAssignData={itemAssignData} getItemData={getItemData}/>);
      case 'Unassigned Items':
        return <UnAssignedItems itemPopupData={itemPopupData} getItemData={getItemData}/>;
      default:
        return null;
    }
  };
  return (
    <Grid>
      <Paper sx={{ position: 'fixed', bottom: 0, left: 0, right: 0,backgroundColor:"rgb(230, 236, 240)"}} elevation={3}>
        <BottomNavigation value={selectedNavItem} onChange={handleNavItemChange} showLabels>
          <BottomNavigationAction label="All Items" value="All Items" />
          <BottomNavigationAction label="Assigned Items" value="Assigned Items" />
          <BottomNavigationAction label="Unassigned Items" value="Unassigned Items" />
        </BottomNavigation>
      </Paper>
      {renderPage()}
    </Grid>
  );
};

export default SideNavbar;
