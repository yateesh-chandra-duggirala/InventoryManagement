import Grid from '@material-ui/core/Grid'
import React from 'react'
import '../../Common.scss'
import {Card,Space,Statistic} from 'antd';
import Category from '@mui/icons-material/Category';
import ShoppingCartOutlined from '@mui/icons-material/ShoppingCartOutlined';

const EmployeeBoard = (props) =>{
    const {count,userDetails} = props;
    return (
        <Grid className="adminboard-body">
            <Grid className="grid1">
                <img src='https://www.spherewms.com/hubfs/blog-files/SPH%20Whse%20Inv%20Mgmt%20Blog-shutterstock_1930996376.jpg' width="1366" height="420" className="image" ></img>
                <Grid className='space'>
                    <Space direction="horizontal">
                        <Card style={{marginLeft:"100px",height:"100px",position:"absolute",border:"0"}}>
                            <Space direction="horizontal">
                                {<ShoppingCartOutlined style={{color:"black", backgroundColor:"blueviolet",borderRadius:20, fontSize:24, padding:8,}}/>}
                                <Statistic title="Assigned Items" value={count}/>
                            </Space>
                        </Card>
                            <h1 style={{marginLeft:"500px",position:"absolute"}}>Welcome!  {userDetails.firstName}</h1>
                        <Card style={{marginLeft:"1100px",height:"100px",position:"absolute",border:"0"}}>
                            <Space direction="horizontal">
                                {<Category style={{color:"black", backgroundColor:"pink",borderRadius:20, fontSize:24, padding:8,}}/>}
                                <Statistic title="Categories" value={5}/>
                            </Space>
                        </Card>
                    </Space>
                </Grid>
            </Grid>
        </Grid>
    )
}
export default EmployeeBoard;