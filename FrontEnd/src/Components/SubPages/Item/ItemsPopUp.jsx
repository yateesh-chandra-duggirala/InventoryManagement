import Grid from '@material-ui/core/Grid'
import React from 'react'
import '../../Common.scss'
import {DataGrid} from '@mui/x-data-grid';
const columns =[
  { field: 'itemId', headerName: 'Item Id'},
  { field: 'itemName', headerName: 'Item Name', sortable: true},
  { field: 'category', headerName: 'Category', sortable: true},
  { field: 'billNumber', headerName: 'Bill Number'},
  { field: 'dateOfPurchase', headerName: 'Date of Purchase'},
  { field: 'warranty', headerName: 'Warranty'},
  { field: 'expireDate', headerName: 'Expire Date'},
];

const ItemsPopUp = (props) =>{
  const {setSelectedItems,itemData} = props;
  const formattedRows = itemData.map((item)=>({
    id:item.itemId,
    ...item,
  }));
  const onRowsSelectionHandler = (ids) => {
    const selectedRowsData = ids.map((id) => formattedRows.find((row) => row.id == id));
    setSelectedItems(selectedRowsData);
  };
  return (
    <Grid className="items-popup-body">
      <Grid className="grid-btn"></Grid>
      {Array.isArray(itemData) && itemData.length > 0 ?(
        <DataGrid
          rows={formattedRows}
          columns={columns}
          initialState={{
            pagination: {
              paginationModel: { page: 0, pageSize: 5 },
            },
          }}
          sx={{
            height: '370px',
            '& .MuiDataGrid-virtualScroller::-webkit-scrollbar':{
              height: '3px',
              width: '3px'
            },
            '& .MuiDataGrid-virtualScroller::-webkit-scrollbar-track': {
              background: '#fff',
            },
            '& .MuiDataGrid-virtualScroller::-webkit-scrollbar-thumb': {
              background: '#A7A2A2',
            },
            '& .MuiDataGrid-virtualScroller::-webkit-scrollbar-thumb:hover': {
              background: '#555',
            },
          }}
          pageSizeOptions={[5, 10]}
          checkboxSelection
          onRowSelectionModelChange={ids =>onRowsSelectionHandler(ids)}
        />
        ):(<p>No item data available</p>
      )}
    </Grid>
  )
}

export default ItemsPopUp;