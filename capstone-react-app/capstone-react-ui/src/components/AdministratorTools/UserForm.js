import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";
import { emptyUser } from "../../services/data";
import {findById, add, update} from "../../services/users"

function UserForm(){
    const[user, setUser] = useState(emptyUser);
    const history = useHistory();
    const { id } = useParams();


 useEffect(() => {
        if (id) {
            findById(id)
                .then(u => setUser(u))
                .catch(() => history.push("/failure"));
        }
    }, [history, id]);

    const onChange = evt => {
        const nextUser = { ...user };
            nextUser[evt.target.name] = evt.target.value;
        setUser(nextUser);
    };

    const onSubmit = evt => {
        evt.preventDefault();
        (user.userId > 0 ? update(user) : add(user))
            .then(() => history.push("/admintools"))
            .catch();
    };

    const cancel = evt => {
        evt.preventDefault();
        history.push("/admintools");
    };



    return (
        <form onSubmit={onSubmit}>
            <h2>{`${(user.userId > 0 ? "Edit" : "Add")} a User`}</h2>
            <div className="form-group">
                <label htmlFor="firstName">First Name</label>
                <input type="text" className="form-control" id="firstName" name="firstName"
                    value={user.firstName} onChange={onChange} required />
            </div>
            <div className="form-group">
                <label htmlFor="lastName">Last Name</label>
                <input type="text" className="form-control" id="lastName" name="lastName"
                    value={user.lastName} onChange={onChange} required />
            </div>
            <div className="form-group">
                <label htmlFor="username">User Name</label>
                <input type="text" className="form-control" id="username" name="username"
                    value={user.username} onChange={onChange} required />
            </div>
            <div className="form-group">
                <label htmlFor="password">Password</label>
                <input type="text" className="form-control" id="password" name="password"
                    value={user.password} onChange={onChange} required />
            </div>
            <div className="form-group">
                <label htmlFor="userType">User Type</label>
                <input type="text" className="form-control" id="userType" name="userType"
                    value={user.userType} onChange={onChange} required />
            </div>
        
            
            <div className="form-group">
                <button type="submit" className="btn btn-primary me-2">Save</button>
                <button className="btn btn-secondary" onClick={cancel}>Cancel</button>
            </div> 
        </form>
    );


}

export default UserForm;
