import React,{useState} from 'react';
import Login from '../Login/login';
import EmployeeNavbar from '../NavBar/EmployeeNavbar';
import AdminRegister from '../../SubPages/Admin/AdminRegister';
import EmployeeRegister from '../../SubPages/Employee/EmployeeRegister';
import AdminNavBar from '../NavBar/AdminNavBar';

const Walmart = () =>{
    const [renderComponent,setRenderComponent] = useState("login");
    const [userDetails,setUserDetails] = useState({});
    const renderPage = () => {
    switch (renderComponent) {
      case 'login':
        return <Login setRenderComponent={setRenderComponent} setUserDetails={setUserDetails}/>;
      case 'adminBoard':
        return <AdminNavBar setRenderComponent={setRenderComponent} userDetails={userDetails}/>;
      case 'employeeBoard':
        return <EmployeeNavbar setRenderComponent={setRenderComponent} userDetails={userDetails}/>;
      case 'adminRegister':
        return <AdminRegister setRenderComponent={setRenderComponent}/>;
      case 'employeeRegister':
        return <EmployeeRegister setRenderComponent={setRenderComponent}/>;
      default:
        return null;
    }
    };
    return(
        <>
            {renderPage()}
        </>
    )
};

export default Walmart;