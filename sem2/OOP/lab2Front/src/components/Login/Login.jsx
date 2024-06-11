
import { useRef, useState, useEffect } from "react";
import axios from "../../api/axios";
import "./Login.css"
import useAuth from "../hooks/useAuth";
import { Link } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

const LOGIN_URL = '/auth'

const Login = () => {

    const navigate = useNavigate();
    // const location = useLocation();
    // const from = location.state?.from?.pathname || "/home";

    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [email, setEmail] = useState('');
    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false)

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setErrMsg('');
    }, [user, pwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        await axios.post(LOGIN_URL, JSON.stringify({
            login: user,
            password: pwd,
            email: email
        }),  {
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
            const token = response.data
            console.log(JSON.stringify(response?.data));
            setUser('');
            setPwd('');
            setSuccess(true);
            window.localStorage.setItem('Token', JSON.stringify(token));
            navigate('/home')
            //navigate(from, { replace: true });
        })
            .catch(err => {
                console.error('Error:', err); // Handle any errors
                if (!err?.response) {
                    setErrMsg("No Response");
                }
                else if (err.response?.status == 400) {
                    setErrMsg("Missing Username or Password");
                }
                else if (err.response?.status == 401) {
                    setErrMsg("Unauthorized");
                } else {
                    setErrMsg("Login failed");
                }
                errRef.current.focus();
            });
    }

    return (
        <div>
                <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                <h1>Sign in</h1>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username">Username:</label>
                    <input type="text" id="username" ref={userRef} autoComplete="off" onChange={(e) => setUser(e.target.value)} value={user} required />
                    <label htmlFor="email">Email:</label>
                    <input type="text" id="email" autoComplete="off" onChange={(e) => setEmail(e.target.value)} value={email} required />
                    <label htmlFor="password">Password:</label>
                    <input type="password" id="password" onChange={(e) => setPwd(e.target.value)} value={pwd} required />
                    <button>Sign in</button>
                </form>
            </div>
    )
}

export default Login