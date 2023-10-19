import Grid from '@material-ui/core/Grid'
import React,{useEffect, useState} from 'react'
import '../Common.scss';
import axios from 'axios';
import Button from '@mui/material/Button';
import TextField from '@material-ui/core/TextField';
import MuiAlert from '@mui/material/Alert';

const ShowProfile = (props) =>{
    const {userDetails} = props;
    const [user,setUser] = useState(null);
    const [editing, setEditing] = useState(false);
    const [updatedUser, setUpdatedUser] = useState({});
    const [systemErrors,setSystemErrors] = useState("");
    const Alert = React.forwardRef(function Alert(props, ref) {
        return <MuiAlert ref={ref} variant="filled" {...props} />;
    });
    const fetchUser = async() =>{
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token
        };
        try{
            const response =await axios.get(`http://localhost:6001/api/users/getusers/${userDetails.userId}`,{headers});
            setUser(response?.data?.employees);
        }catch(error){
            console.log(error);
            console.log(userDetails.userId);
        }
    };
    const handleInputChange = (event) => {
        setUpdatedUser({
          ...updatedUser,
          [event.target.name]: event.target.value,
        });
      };
    const handleCancel = () =>{
        setEditing(false);
    }
    const handleUpdateUser = async () => {
        const token = localStorage.getItem("jwtToken");
        const headers = {
            'Authorization': token,
            'Content-Type' : 'application/json'
        };
        const updatedData = {
            firstName: updatedUser.firstName || user.firstName,
            lastName: updatedUser.lastName || user.lastName,
            email: updatedUser.email || user.email,
            dateOfBirth: updatedUser.dateOfBirth || user.dateOfBirth,
            age: updatedUser.age || user.age,
            mobileNumber: updatedUser.mobileNumber || user.mobileNumber,
        };
        await axios.put(`http://localhost:6001/api/users/updateusers/${user.userId}`,updatedData,{headers})
        .then(response=>{
            if(response?.status == 200){
                setSystemErrors({...systemErrors,response:'Updated Successfully'});
                setTimeout(function() {
                    setSystemErrors({...systemErrors,response:''});
                }, 2000);
                fetchUser();
            }
            else if(response?.status == 400){
                setSystemErrors({...systemErrors,response:'Error'});
                setTimeout(function() {
                    setSystemErrors({...systemErrors,response:''});
                }, 2000);
            }
        }).catch(error=>{
            if(error?.message=="Network Error"){
                setSystemErrors({...systemErrors,networkError:error?.message})
                setTimeout(function() {
                    setSystemErrors({...systemErrors,networkError:''});
                }, 2000);
            }
        });
    };
    useEffect(()=>{
        fetchUser();
    },[]);
   return (
        <Grid className="showprofile-body">
            {systemErrors?.networkError?.length>0 && <Alert severity="error" style={{width:'400px', position:"absolute", marginLeft:'920px', marginTop:'140px'}}>{systemErrors?.networkError}</Alert>}   
            {systemErrors?.response?.length>0 && <Alert severity="success" style={{width:'400px', position:"absolute", marginLeft:'920px', marginTop:'140px'}}>{systemErrors?.response}</Alert>} 
            {user ?(
                <Grid className="all">
                    <Grid className='heading'>
                        <h2 className='profile'>Account Profile</h2>
                        <Grid className="divider">
                            <hr/>
                        </Grid>
                        <img src="https://cdn-icons-png.flaticon.com/512/16/16363.png" className='image'/>
                    </Grid>
                    <Grid className="grid-container">
                        <h3 className="name">ID :- <span style={{marginLeft:"137px",color:"grey"}}>{user.userId}</span></h3>
                        {!editing? (
                            <>
                                <h3 className="name">FirstName :-<span style={{marginLeft:"70px",color:"grey"}}>{user.firstName}</span></h3>
                                <h3 className="name">LastName :- <span style={{marginLeft:"69px",color:"grey"}}>{user.lastName}</span></h3> 
                                <h3 className="name">EMAIL :- <span style={{marginLeft:"101px",color:"grey"}}>{user.email}</span></h3>
                                <h3 className="name">DATE OF BIRTH :- <span style={{marginLeft:"23px",color:"grey"}}>{user.dateOfBirth}</span></h3>
                                <h3 className="name">AGE :- <span style={{marginLeft:"124px",color:"grey"}}>{user.age}</span></h3>
                                <h3 className="name">Mobile Number :- <span style={{marginLeft:"21px",color:"grey"}}>{user.mobileNumber}</span></h3>
                                <Button variant="contained" size="medium" className="button" onClick={() => setEditing(true)}>Update</Button>
                            </>
                        ):(
                            <>
                                <h3 className="name">FirstName :-
                                <TextField
                                style={{marginLeft:"70px",marginTop:"-8px",color:"grey"}}
                                type="text"
                                name="firstName"
                                placeholder="First Name"
                                value={updatedUser.firstName || user.firstName}
                                onChange={handleInputChange}
                                /></h3>
                                <h3 className="name">LastName :-
                                <TextField
                                style={{marginLeft:"72px",marginTop:"-8px",color:"grey"}}
                                type="text"
                                name="lastName"
                                placeholder="Last Name"
                                value={updatedUser.lastName || user.lastName}
                                onChange={handleInputChange}
                                /></h3>
                                <h3 className="name">Email :-<span style={{marginLeft:"109px",color:"grey"}}>{user.email}</span></h3>
                                <h3 className="name">Date Of Birth :-
                                <TextField
                                style={{marginLeft:"41px",marginTop:"-8px",color:"grey"}}
                                type="date"
                                name="dateOfBirth"
                                placeholder="Date Of birth"
                                value={updatedUser.dateOfBirth || user.dateOfBirth}
                                onChange={handleInputChange}
                                /></h3>
                                <h3 className="name">Age :-
                                <TextField
                                style={{marginLeft:"124px",marginTop:"-8px", color:"grey"}}
                                type="text"
                                name="age"
                                placeholder="Age"
                                value={updatedUser.age || user.age}
                                onChange={handleInputChange}
                                /></h3>
                                <h3 className="name"> Mobile Number:-
                                <TextField
                                style={{marginLeft:"23px",marginTop:"-8px",color:"grey"}}
                                type="text"
                                name="mobileNumber"
                                placeholder="Mobile Number"
                                value={updatedUser.mobileNumber || user.mobileNumber}
                                onChange={handleInputChange}
                                /></h3>
                                <Button size="medium" variant="contained" className="button" onClick={handleUpdateUser}>Save</Button>
                                <Button size="medium" variant="contained" className="button" onClick={handleCancel}>Back</Button>
                            </>
                        )}
                    </Grid>
                </Grid>
                ):(
                    <p align="center">Loading user profile ....</p>
            )}
        </Grid>
    );
};

export default ShowProfile;