import taxiIcon from '../assets/taxi-icon.svg'
import './Ununsed_Register.css'
import { useNavigate } from 'react-router-dom'
import { useState } from "react";

function Unused_Register() {

    // TODO: Move stuff to reusable components

    // Move to components
    const navigate = useNavigate();
    const [registerData, setRegisterData] = useState({
        username: "",
        email: "",
        password: "",
        userType: "",
        regionId: 0,
        subRegionId: 0,
    });
    const handleChange = (e: { target: { name: any; value: any; }; }) => {
        const { name, value } = e.target;
        setRegisterData(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    return (
        <>
            <section id="navigation-bar">
                <nav className="navigation-content">
                    <div id="logo" >
                        <img className="main-logo" src={taxiIcon} alt=""/>
                    </div>
                    <ul>
                        <li><a href="/test1"> test1</a></li>
                        <li><a href="/test2"> test2</a></li>
                        <li><a href="/test2"> test3</a></li>
                    </ul>
                </nav>
            </section>
            <section id="body">
                <div id="background" >
                    <div className="glass-filter" />
                </div>
                <form id="auth-component">
                    <div id="auth-content">
                        <div className="auth-item auth-header">
                            <h2>Sign Up</h2>
                            <h2>Login</h2>
                        </div>
                        <div id="border"></div>
                        <div className="auth-item auth-column">
                            <h2 className="auth-item auth-left-text"> Name</h2>
                            <input className="auth-grow"
                                   name="username"
                                   value={registerData.username}
                                   onChange={handleChange}
                                   placeholder="Enter username"
                            />
                        </div>
                        <div className="auth-item auth-column">
                            <h2 className="auth-item auth-left-text"> Email</h2>
                            <input className="auth-grow"
                                   name="email"
                                   value={registerData.email}
                                   onChange={handleChange}
                                   placeholder="Enter email"
                            />
                        </div>
                        <div className="auth-item auth-column">
                            <h2 className="auth-item auth-left-text"> Password</h2>
                            <input className="auth-grow"
                                   name="password"
                                   value={registerData.password}
                                   onChange={handleChange}
                                   placeholder="Enter password"
                            />
                        </div>
                        <div className="auth-item">
                            <button
                                type="button"
                                className="counter"
                                onClick={() =>
                                    setRegisterData((count))}
                            >
                                Sign up
                            </button>
                        </div>
                    </div>
                </form>
            </section>
        </>
    )
}

export default Unused_Register
