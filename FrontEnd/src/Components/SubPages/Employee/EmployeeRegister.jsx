import Register from '../../Pages/Register/Register'
const EmployeeRegister = (props) =>{
    const {setRenderComponent} = props;
    return(
        <Register role={['employee']} setRenderComponent={setRenderComponent}/>
    );
}
export default EmployeeRegister;