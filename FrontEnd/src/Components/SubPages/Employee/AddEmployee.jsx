import Grid from '@material-ui/core/Grid'
import React,{useState,useEffect} from 'react';
import '../../Common.scss'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import axios from 'axios'

const AddEmployee = (props) =>{
    const {systemErrors,setSystemErrors,setIsUnassignPopupOpen,getEmployeeData} =props
    const initialValues = {
        firstName:"",
        lastName:"",
        dateOfBirth:"",
        age:"",
        mobileNumber:"",
        email: "", 
        password: "Test@123",
        role: ['employee'] ,
    };
    const [focus, setFocused] = useState(false);
    const onFocus = () => setFocused(true);
    const onBlur = () => setFocused(false);
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isDisable, setIsDisable] = useState(true);
    const [ageValue,setAgeValue] = useState();
    const [ageBlur,setAgeBlur] = useState(false);
    const input = {
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
        }
    };
    const inputNumber = {
        maxLength: 10,
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
        }
    }
    const handleRegister = () =>{
        axios.post('http://localhost:6001/api/auth/signup',finalValues)
        .then(response=>{
            if(response?.status==200){
                setSystemErrors({...systemErrors,response:'User Successfully Registered!'});
                getEmployeeData();
                setIsUnassignPopupOpen(false);
                setTimeout(function() {
                    setSystemErrors({...systemErrors,response:''})
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
    const handleChange = (e) => {
        const { name, value } = e.target;
        if(name === "email"){
            if(!value){
                setFormErrors({...formErrors,email:'Email Required'});
            }
            else if(!/^[A-Z0-9a-z+_-]+@walmart.com$/.test(value)){
                setFormErrors({...formErrors,email:'Invalid Email'});
            }
            else{
                setFormErrors({...formErrors,email:''});
                setFormValues({...formValues,email:value})
            }
        }
        else if(name === "firstName"){
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
        role: formValues.role
    };
    useEffect(() => {
        if (formErrors?.firstName?.length == 0 && formErrors?.lastName?.length == 0 && formErrors?.dateOfBirth?.length == 0 && formErrors?.mobileNumber?.length == 0 && formErrors?.email?.length == 0) {
            setIsDisable(false);
        }
        else if(formErrors?.firstName?.length != 0 || formErrors?.lastName?.length != 0 || formErrors?.dateOfBirth?.length != 0 || formErrors?.mobileNumber?.length != 0 || formErrors?.email?.length != 0){
            setIsDisable(true);
        }
    }, [formErrors]);
    return (
        <Grid className="addemployee">
            <Grid className='employee-popup'>
                <Grid>
                    <FormControl className="register-form">
                        <h1>Add Employee</h1>
                        <Grid className="all-fields">
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
                                    <TextField type="Number" className="text" variant="standard" InputProps={inputNumber} name="mobileNumber" style={{width: "205px"}} onChange={handleChange} label="Mobile Number" size="small" required></TextField>
                                </Grid>
                                <Grid className="textbox">
                                    <TextField type="email" className="text" variant="standard" InputProps={input}  name="email" style={{width: "205px",marginLeft:"65px"}} onChange={handleChange} label="Email Id" size="small" required></TextField>
                                </Grid>
                                <p style={{color:"red", position:"absolute",marginLeft:"-30px",marginTop:"65px"}}>{formErrors.mobileNumber}</p>
                                <p style={{color:"red", position:"absolute",marginLeft:"210px",marginTop:"65px"}}>{formErrors.email}</p>
                            </Grid>
                            <Grid className="button-label">
                                <Button variant="contained" className="button" onClick={handleRegister} disabled={isDisable} InputProps={input} size="large" >Add</Button>
                            </Grid>
                        </Grid>
                    </FormControl>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default AddEmployee;