import Grid from '@material-ui/core/Grid'
import React,{useState,useEffect} from 'react';
import './Register.scss'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import InputAdornment from '@mui/material/InputAdornment';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import IconButton from '@mui/material/IconButton';
import VisibilityIcon from '@mui/icons-material/Visibility';
import axios from 'axios'
import MuiAlert from '@mui/material/Alert';

const Register = (props) =>{
    const {setRenderComponent,role} = props;
    const initialValues = {
        firstName:"",
        lastName:"",
        dateOfBirth:"",
        age:"",
        mobileNumber:"",
        email: "", 
        password: "",
        role: role 
    };
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword(!showPassword);
    const handleMouseDownPassword = () => setShowPassword(!showPassword);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const handleClickShowConfirmPassword = () => setShowConfirmPassword(!showConfirmPassword);
    const handleMouseDownConfirmPassword = () => setShowConfirmPassword(!showConfirmPassword);
    const [focus, setFocused] = useState(false);
    const onFocus = () => setFocused(true);
    const onBlur = () => setFocused(false);
    const [password, setPassword] = useState("");
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isDisable, setIsDisable] = useState(true);
    const [systemErrors,setSystemErrors] = useState("");
    const [ageValue,setAgeValue] = useState();
    const [ageBlur,setAgeBlur] = useState(false);
    const Alert = React.forwardRef(function Alert(props, ref) {
        return <MuiAlert ref={ref} variant="filled" {...props} />;
    });
    const input = {
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
        }
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
                    onMouseDown={handleMouseDownPassword}
                    >
                    {showPassword ? <VisibilityIcon sx={{ color: "white"}}/> : <VisibilityOffIcon sx={{ color: "white"}}/>}
                </IconButton>
            </InputAdornment>
        ),
    };
    const inputconf = {
        disableUnderline: true,
        shrink: true,
        style: {
            color: "white",
            disableUnderline: true,
        },
        endAdornment: (
            <InputAdornment position="end">
                <IconButton
                    aria-label="toggle password visibility"
                    onClick={handleClickShowConfirmPassword}
                    onMouseDown={handleMouseDownConfirmPassword}
                    >
                    {showConfirmPassword ? <VisibilityIcon sx={{ color: "white"}}/> : <VisibilityOffIcon sx={{ color: "white"}}/>}
                </IconButton>
            </InputAdornment>
        ),
    };
    const handleRegister = () =>{
      axios.post('http://localhost:6001/api/auth/signup',finalValues)
        .then(response=>{
            if(response?.status==200){
                setSystemErrors({...systemErrors,response:'User Successfully Registered! Redirecting to Login Page'});
                setTimeout(function() {
                    setSystemErrors({...systemErrors,response:''})
                    setRenderComponent("login");
                }, 5000);
            }
        })
        .catch(error=>{
            if(error?.message=="Network Error"){
                setSystemErrors({...systemErrors,networkError:error?.message})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 5000);
            }
            else if(error?.response?.status==400){
                setSystemErrors({...systemErrors,networkError:'Email Already Exists!'})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 5000);
            }
            else if(error?.response?.status==401){
                setSystemErrors({...systemErrors,networkError:'Values Out Of Range!'})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''})
                }, 5000);
            }
        });
    };
    const handleLogin = () => {
        setRenderComponent("login");
    }
    const handleChange = (e) => {
        const { name, value } = e.target;
        if(name === "email"){
            if(!value){
                setFormErrors({...formErrors,email:'Email Required'});
            }
            else if(!/^[A-Z0-9a-z+_-]+@walmart[.]com$/.test(value)){
                setFormErrors({...formErrors,email:'Invalid Email'});
            }
            else{
                setFormErrors({...formErrors,email:''});
                setFormValues({...formValues,email:value})
            }
        }
        else if(name === "password"){
            setPassword(value);
            var re = {
                capital: /(?=.*[A-Z])/,
                length: /(?=.{7,40}$)/,
                specialChar: /[ -\/:-@\[-\`{-~]/,
                digit: /(?=.*[0-9])/,
            };
            if(!value){
                setFormErrors({...formErrors,password:'Password Required'});
            }
            else if((!re.capital.test(value))||(!re.specialChar.test(value))||(!re.length.test(value))||(!re.digit.test(value))){
                setFormErrors({...formErrors,password:'Password must contain atleast a Capital, Special character, Number and minimum 8 characters '});
            }
            else{
                setFormErrors({...formErrors,password:''});
                setFormValues({...formValues,password:value})
            }
        }else if(name === "firstName"){
            if(!value){
                setFormErrors({...formErrors,firstName:'First Name Required'});
            }
            else{
                setFormErrors({...formErrors,firstName:''});
                setFormValues({...formValues,firstName:value})
            }
        }
        else if(name === "lastName"){
            if(!value){
                setFormErrors({...formErrors,lastName:'Last Name Required'});
            }
            else{
                setFormErrors({...formErrors,lastName:''});
                setFormValues({...formValues,lastName:value})
            }
        }
        else if(name === "dateOfBirth"){
            var today = new Date();
            var birthDate = new Date(e.target.value);
            var age = today.getFullYear() - birthDate.getFullYear();
            var m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            setAgeValue(age);
            setAgeBlur(true);
            if(!value){
                setFormErrors({...formErrors,dateOfBirth:'Date of Birth Required'});
            }
            else{
                setFormErrors({...formErrors,dateOfBirth:''});
                setFormValues({...formValues,dateOfBirth:value})
            }
        }
        else if(name === "mobileNumber"){
            if(!value){
                setFormErrors({...formErrors,mobileNumber:'Mobile Number Required'});
            }
            else{
                setFormErrors({...formErrors,mobileNumber:''});
                setFormValues({...formValues,mobileNumber:value})
            }
        }
        else if(name === "confirmpassword"){
            if(!value){
                setFormErrors({...formErrors,confirmpassword:'Confirm your password'});
            }
            else if(password!=value){
                
                setFormErrors({...formErrors,confirmpassword:'Passwords does not match'});
            }
            else{
                setFormErrors({...formErrors,confirmpassword:''});
                setFormValues({...formValues,confirmpassword:value})
            }
        }
        setFormValues({...formValues,[name]:value});
    };
    const finalValues ={
        firstName:formValues.firstName,
        lastName:formValues.lastName,
        dateOfBirth:formValues.dateOfBirth,
        age:ageValue,
        mobileNumber:formValues.mobileNumber,
        email:formValues.email, 
        password:formValues.password,
        role: role 
    };
    useEffect(() => {
        if (formErrors?.firstName?.length == 0 && formErrors?.lastName?.length == 0 && formErrors?.dateOfBirth?.length == 0 && formErrors?.mobileNumber?.length == 0 && formErrors?.email?.length == 0 && formErrors?.password?.length == 0 && formErrors?.confirmpassword?.length == 0) {
            setIsDisable(false);
        }
        else if(formErrors?.firstName?.length != 0 || formErrors?.lastName?.length != 0 || formErrors?.dateOfBirth?.length != 0 || formErrors?.mobileNumber?.length != 0 || formErrors?.email?.length != 0 || formErrors?.password?.length != 0 || formErrors?.confirmpassword?.length != 0){
            setIsDisable(true);
        }
    },[formErrors]);
    return (
        <Grid className="body">
            <Grid className="logo">WalMart</Grid>
            {systemErrors?.networkError?.length>0 && <Alert severity="error" style={{width:'350px', position:"absolute", marginLeft:'983px', marginTop:'470px'}}>{systemErrors?.networkError}</Alert>}   
            {systemErrors?.response?.length>0 && <Alert severity="success" style={{width:'400px', position:"absolute", marginLeft:'933px', marginTop:'470px'}}>{systemErrors?.response}</Alert>} 
            <Grid className='register-popup'>
                <Grid>
                    <FormControl className="register-form">
                        <h1>SignUp</h1>
                        <Grid className="field-container"> 
                            <Grid className="textbox">
                                <TextField type="text" className="text" variant="standard" InputProps={input} name="firstName" style={{width: "205px"}} onChange={handleChange} label="First Name" size="small" required></TextField>
                            </Grid>
                            <Grid className="textbox">
                                <TextField type="text" className="text" variant="standard" InputProps={input} name="lastName" style={{width: "205px",marginLeft:"65px"}} onChange={handleChange} label="Last Name" size="small" required></TextField>
                            </Grid>
                            <p style={{color:"red", position:"absolute",marginLeft:"-30px",marginTop:"65px"}}>{formErrors.firstName}</p>
                            <p style={{color:"red", position:"absolute",marginLeft:"210px",marginTop:"65px"}}>{formErrors.lastName}</p>
                        </Grid>
                        <Grid className = "field-container">
                            <Grid className="textbox">
                                <TextField type={focus ? "date" : "text"} className="text" variant="standard" InputProps={input} name="dateOfBirth" style={{width: "205px"}} onChange={handleChange} label="Date of birth" size="small" onFocus={onFocus} onBlur={onBlur} required></TextField>
                            </Grid>
                            <Grid className="textbox">
                                <TextField type="text" className="text" variant="standard" InputProps={input} InputLabelProps={{shrink:ageBlur}} inputProps={{ readOnly: true }} name="age" style={{width: "205px",marginLeft:"65px"}} onChange={handleChange} label="Age" size="small" value={ageValue} required></TextField>
                            </Grid>
                            <p style={{color:"red", position:"absolute",marginLeft:"-30px",marginTop:"65px"}}>{formErrors.dateOfBirth}</p>
                        </Grid>
                        <Grid className = "field-container">
                            <Grid className="textbox">
                                <TextField type="Number" className="text" variant="standard" InputProps={input} name="mobileNumber" style={{width: "205px"}} onChange={handleChange} label="Mobile Number" size="small" required></TextField>
                            </Grid>
                            <Grid className="textbox">
                                <TextField type="email" className="text" variant="standard" InputProps={input}  name="email" style={{width: "205px",marginLeft:"65px"}} onChange={handleChange} label="Email Id" size="small" required></TextField>
                            </Grid>
                            <p style={{color:"red", position:"absolute",marginLeft:"-30px",marginTop:"65px"}}>{formErrors.mobileNumber}</p>
                            <p style={{color:"red", position:"absolute",marginLeft:"210px",marginTop:"65px"}}>{formErrors.email}</p>
                        </Grid>
                        <Grid className = "field-container">
                            <Grid className="textbox">
                                <TextField type={showPassword ? "text" : "password"} className="text" variant="standard" InputProps={inputpass} name="password" style={{width: "210px"}} onChange={handleChange} label="Password" size="small" required></TextField>
                            </Grid>
                            <Grid className="textbox">
                                <TextField type={showConfirmPassword ? "text" : "password"} className="text" variant="standard" InputProps={inputconf} name="confirmpassword" style={{width: "210px",marginLeft:"65px"}} onChange={handleChange} label="Confirm Password" size="small" required></TextField>
                            </Grid>
                            <p style={{color:"red", position:"absolute",marginLeft:"-30px",marginTop:"65px"}}>{formErrors.password}</p>
                            <p style={{color:"red", position:"absolute",marginLeft:"210px",marginTop:"65px"}}>{formErrors.confirmpassword}</p>
                        </Grid>
                        <Grid className="button-label">
                            <Button variant="contained" className="button" onClick={handleRegister} disabled={isDisable} InputProps={input} size="large" >Register</Button>
                            <Button variant="standard" className="button2" onClick={handleLogin} InputProps={input} size="small" >having an account? Login</Button>
                        </Grid>
                    </FormControl>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default Register;