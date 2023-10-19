import Register from '../../Pages/Register/Register'
const AdminRegister = (props) =>{
    const {setRenderComponent} = props;
    return(
        <Register role={['admin']} setRenderComponent={setRenderComponent}/>
    );
}
export default AdminRegister;