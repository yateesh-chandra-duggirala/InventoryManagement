import React,{useState,useEffect} from 'react';
import Grid from '@material-ui/core/Grid'
import './login.scss'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import InputAdornment from '@mui/material/InputAdornment';
import IconButton from '@mui/material/IconButton';
import VisibilityIcon from '@mui/icons-material/Visibility';
import MuiAlert from '@mui/material/Alert';
import axios from 'axios'
import Dialog from '@mui/material/Dialog';
import CloseIcon from '@mui/icons-material/Close';
import DialogActions from '@mui/material/DialogActions';

const Login = (props) =>{
    const {setRenderComponent,setUserDetails} = props;
    const initialValues = {
        email: "",
        password: "" 
    };
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword(!showPassword);
    const handleMouseDownPassword = () => setShowPassword(!showPassword);
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isDisable, setIsDisable] = useState(true);
    const [isDialogOpen,setIsDialogOpen] = useState(false);
    const [systemErrors,setSystemErrors] = useState({});
    const Alert = React.forwardRef(function Alert(props, ref) {
        return <MuiAlert ref={ref} variant="filled" {...props} />;
    });
    const input = {
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
        },
        endAdornment: (
            <InputAdornment position="end">
              <AccountCircleIcon sx={{ color: "white"}}/>
            </InputAdornment>
        ),
    };
    const inputpass = {
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
        },
        endAdornment: (
            <InputAdornment position="end">
                <IconButton
                aria-label="toggle password visibility"
                onClick={handleClickShowPassword}
                onMouseDown={handleMouseDownPassword}>
                {showPassword ? <VisibilityIcon sx={{ color: "white"}}/> : <VisibilityOffIcon sx={{ color: "white"}}/>}
                </IconButton>
            </InputAdornment>
        ),
    };
    const handleClose = () =>{
        setIsDialogOpen(false);
    };
    const handleChange = (e) => {
        const { name, value } = e.target;
        if(name === "email"){
            if(!value){
                setFormErrors({...formErrors,email:'Email Required'});
            }
            else{
                setFormErrors({...formErrors,email:''});
                setFormValues({...formValues,email:value})
            }
        }
        else if(name === "password"){
            if(!value){
                setFormErrors({...formErrors,password:'Password Required'});
            }
            else{
                setFormErrors({...formErrors,password:''});
                setFormValues({...formValues,password:value})
            }
        }
        setFormValues({ ...formValues, [name]: value });
    };
    const handleLogin = async(e) => {
      axios.post('http://localhost:6001/api/auth/signin',formValues)
        .then(response=>{
            if(response?.status==200){
                const {token,type} = response?.data;
                localStorage.setItem("jwtToken", type+" "+token);
                if(response?.data.roles[0]== "ROLE_ADMIN"){
                    setSystemErrors({...systemErrors,response:'Login Successfull'})
                    setTimeout(function() {
                        setSystemErrors({...systemErrors,response:''})
                        setRenderComponent("adminBoard")
                    }, 3000);
                }
                else if(response?.data.roles[0]== "ROLE_EMPLOYEE"){
                    setSystemErrors({...systemErrors,response:'Login Successfull'})
                    setTimeout(function() {
                        setSystemErrors({...systemErrors,response:''})
                        setRenderComponent("employeeBoard")
                    }, 3000);
                }
            }
        }).catch(error=>{
            if(error?.message=="Network Error"){
                setSystemErrors({...systemErrors,networkError:error?.message})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 5000);
            }
            else if(error?.response?.status==401){
                setSystemErrors({...systemErrors,networkError:'Invalid Credentials'})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 5000);
            }
        });
        const res = await axios.get(`http://localhost:6001/api/users/getusersbyemail/${formValues.email}`);
        setUserDetails(res?.data?.employees)
    };
    useEffect(() => {
        if (formErrors?.email?.length == 0 && formErrors?.password?.length == 0) {
            setIsDisable(false);
        }
        else if(formErrors?.email?.length != 0 || formErrors?.password?.length != 0){
            setIsDisable(true);
        }
    },[formErrors]);
    return (
        <Grid className="body">
            <Grid className="logo">WalMart</Grid>
            {systemErrors?.networkError?.length>0 && <Alert severity="error" style={{width:'350px', position:"absolute", marginLeft:'983px', marginTop:'400px'}}>{systemErrors?.networkError}</Alert>}  
            {systemErrors?.response?.length>0 && <Alert severity="success" style={{width:'350px', position:"absolute", marginLeft:'983px', marginTop:'400px'}}>{systemErrors?.response}</Alert>}  
            <Grid className='login-popup'>
                <Grid>
                    <FormControl className="register-form">
                        <h1>SignIn</h1>
                        <Grid className="textbox">
                            <TextField type="email" className="text" name="email" style={{width: "235px"}} onChange={handleChange} InputProps={input} variant="standard" label = "Email ID" size="small" required></TextField>
                        </Grid>
                        <p style={{color:"red", position:"absolute",marginLeft:"20px",marginTop:"75px"}}>{formErrors.email}</p>
                        <Grid className="textbox">
                            <TextField type={showPassword ? "text" : "password"} className="text"  name="password" onChange={handleChange} InputProps={inputpass} variant="standard" label = "Password" size="small"  required></TextField>
                        </Grid>
                        <p style={{color:"red", position:"absolute",marginLeft:"20px",marginTop:"140px"}}>{formErrors.password}</p>
                        <Grid className="buttonlabel">
                            <Button variant="contained" className="button" disabled={isDisable} onClick={handleLogin}>Login</Button>
                            <Button variant="text" className="link" onClick={()=>{setIsDialogOpen(true)}} >New User! Click here</Button>
                        </Grid>
                    </FormControl>
                </Grid>
            </Grid>
            <Dialog 
                sx={{
                    "& .MuiDialog-container": {
                        "& .MuiPaper-root": {
                            width: "100%",
                            maxWidth: "400px", 
                            marginLeft:"80px"
                        },
                    },
                }} onClose={handleClose} open={isDialogOpen} >
                <DialogActions>
                    <Button variant="contained" className="button"  onClick={()=>{setRenderComponent("adminRegister")}}>Register as Admin</Button>
                    <Button variant="contained" className="button" onClick={()=>{setRenderComponent("employeeRegister")}}>Register as Employee</Button>
                    <IconButton onClick={handleClose}>
                        <CloseIcon/>
                    </IconButton>
                </DialogActions>
            </Dialog>
        </Grid>
    )
}

export default Login;