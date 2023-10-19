import Grid from '@material-ui/core/Grid'
import React from 'react'
import '../../Common.scss'
import {Card,Space,Statistic} from 'antd';
import Category from '@mui/icons-material/Category';
import PeopleOutline from '@mui/icons-material/PeopleOutline';
import ShoppingCartCheckoutOutlined from '@mui/icons-material/ShoppingCartCheckoutOutlined';
import ShoppingCartOutlined from '@mui/icons-material/ShoppingCartOutlined';

const AdminBoard = (props) =>{
    const {assignItemCount,unAssignItemCount,employeeCount} = props;
    return (
        <Grid className="adminboard-body">
            <Grid className="grid1">
                <img src='https://www.spherewms.com/hubfs/blog-files/SPH%20Whse%20Inv%20Mgmt%20Blog-shutterstock_1930996376.jpg' width="1366" height="420" className="image" ></img>
                <Grid className='space'>
                    <Space direction="horizontal">
                        <DashboardCard icon={<PeopleOutline style={{color:"black", backgroundColor:"red",borderRadius:20, fontSize:24, padding:8,}}/>} title="Employees" value={employeeCount}/>
                        <DashboardCard icon={<ShoppingCartOutlined style={{color:"black", backgroundColor:"blueviolet",borderRadius:20, fontSize:24, padding:8,}}/>} title="Assigned Items" value={assignItemCount}/>
                        <DashboardCard icon={<ShoppingCartCheckoutOutlined style={{color:"black", backgroundColor:"yellow",borderRadius:20, fontSize:24, padding:8,}}/>} title="Unassigned Items" value={unAssignItemCount}/>
                        <DashboardCard icon={<Category style={{color:"black", backgroundColor:"pink",borderRadius:20, fontSize:24, padding:8,}}/>} title="Categories" value={5}/>
                    </Space>
                </Grid>
            </Grid>
        </Grid>
    )
}
function DashboardCard({title,value,icon}){
    return(
        <Card style={{marginLeft:"120px",height:"100px",border:"0"}}>
            <Space direction="horizontal">
                {icon}
                <Statistic title={title} value={value}/>
            </Space>
        </Card>
    );
}

export default AdminBoard;