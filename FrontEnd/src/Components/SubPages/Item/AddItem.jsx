import Grid from '@material-ui/core/Grid'
import React,{useState} from 'react';
import '../../Common.scss'
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import axios from 'axios'
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

const AddItem = (props) =>{
    const {title, initialValues,getItemData,systemErrors,setSystemErrors,setIsUnassignPopupOpen} = props;
    const [focus, setFocused] = useState(false);
    const onFocus = () => setFocused(true);
    const onBlur = (e) => {
        setFocused(false)
        const { name, value } = e.target;
        if(name == "dateOfPurchase"){
        let dateArray = value.split("-");
        let date = dateArray[2]+"-"+dateArray[1]+"-"+dateArray[0];
        setFormValues({...formValues,dateOfPurchase:date})
        }
    };
    const [formValues, setFormValues] = useState({...initialValues});
    const input = {
        disableUnderline: true,
        style: {
            color: "white",
            disableUnderline: true,
        }
    };
    const categories = [
        "Skin Care Products",
        "Clothing",
        "Food Items",
        "Sports",
        "Foot wear"
    ];
    const postRequest = ()=>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token,
            'content-type': 'application/json'
        };
        axios.post('http://localhost:6001/api/inventory/InventoryItems',formValues,{headers})
        .then(response=>{
            if(response?.status==200){
                setSystemErrors({...systemErrors,response:'Item added Successfully'});
                getItemData();
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
        });
    };
    const putRequest = ()=>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token,
            'content-type': 'application/json'
        };
        axios.put(`http://localhost:6001/api/inventory/InventoryItems/${initialValues?.itemId}`,formValues,{headers})
        .then(response=>{
            if(response?.status==200){
                setSystemErrors({...systemErrors,response:'Item updated Successfully'});
                getItemData();
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
        });
    }
    const handleRegister = () =>{
       if(title == 'Add Item'){
            postRequest();
       }else{
            putRequest();
       }
    };
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({...formValues,[name]:value});
    };
    return (
        <Grid className="additem">
            <Grid className='item-popup'>
                <Grid>
                    <FormControl className="register-form">
                        <h1>{title}</h1>
                        <Grid className="all-items">
                            <Grid className="field-container"> 
                                <Grid className="textbox">
                                    <TextField type="text" className="text" variant="standard" InputProps={input} value={formValues?.itemName} name="itemName" style={{width: "205px"}} onChange={handleChange} label="Item Name" size="small" required></TextField>
                                </Grid>
                                <Grid className="textbox">
                                    <FormControl variant="standard" style={{ width: '205px', marginLeft: '65px',marginTop:'13px' }}>
                                        <Select
                                            value={formValues?.category}
                                            onChange={handleChange}
                                            name="category"
                                            displayEmpty
                                            style={{ color: 'white'}}
                                            sx={{
                                                ':before': { borderBottomColor: 'white' },
                                                ':after': { borderBottomColor: 'white' },
                                            }}>
                                            <MenuItem value="" disabled>Category</MenuItem>
                                            {categories.map((category) => (
                                                <MenuItem key={category} value={category} >{category}</MenuItem>
                                            ))}
                                        </Select>
                                    </FormControl>
                                </Grid>
                            </Grid>
                            <Grid className = "field-container">
                                <Grid className="textbox">
                                    <TextField type={focus ? "date" : "text"} className="text" variant="standard" InputProps={input} value={formValues?.dateOfPurchase} name="dateOfPurchase" style={{width: "205px"}} onChange={handleChange} label="Date of purchase" size="small" onFocus={onFocus} onBlur={onBlur} required></TextField>
                                </Grid>
                                <Grid className="textbox">
                                    <TextField type="text" className="text" variant="standard" InputProps={input} name="billNumber" value={formValues?.billNumber} style={{width: "205px",marginLeft:"65px"}} onChange={handleChange} label="Bill Number" size="small" required></TextField>
                                </Grid>
                            </Grid>
                            <Grid className = "field-container">
                                <Grid className="textbox">
                                    <TextField type="Number" className="text" variant="standard" InputProps={input} name="warranty" style={{width: "205px"}} value={formValues?.warranty} onChange={handleChange} label="Warranty(in months)" size="small" ></TextField>
                                </Grid>
                                <Grid className="textbox">
                                    <TextField className="text" variant="standard" InputProps={input}  name="expireDate" value={formValues?.expireDate} style={{width: "205px",marginLeft:"65px"}} onChange={handleChange} label="Expire Date" size="small" ></TextField>
                                </Grid>
                            </Grid>
                            <Grid className="button-label">
                                <Button variant="contained" className="button" onClick={handleRegister} InputProps={input} size="large" >{title == "Add Item"? "Add" : "Update"}</Button>
                            </Grid>
                        </Grid>
                    </FormControl>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default AddItem;