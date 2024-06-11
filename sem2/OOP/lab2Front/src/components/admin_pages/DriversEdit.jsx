import { Link } from "react-router-dom"
import "./Entity.css"
import "../Home.css"
import axios from "../../api/axios"
import { useState, useEffect } from "react"
import getHeaderConfig from "../hooks/Config"
const DRIVER_URL = "/driver";

const EditDrivers = () => {
    const [data, setData] = useState([]);
    const [id, setID] = useState('');
    // Add other state variables for other fields in the Driver model

    const config = getHeaderConfig();

    const makeRequest = async (field, value) => {
        const response = await axios.get(DRIVER_URL, {
            params: {
                field: field,
                value: value
            },
            headers: config.headers
        });
        console.log(JSON.stringify(response?.data));
        setData(response?.data);
    }

    const makeEdit = async (id) => {
        const response = await axios.put(`${DRIVER_URL}/${id}`, JSON.stringify({
            id: id,
            // Add other fields from the Driver model
        }), config);
        setData(response?.data);
        makeRequest("all", "all");
    }

    const makeCreate = async () => {
        const response = await axios.post(DRIVER_URL, JSON.stringify({
            // Add other fields from the Driver model
        }), config);
        setData(response?.data);
        makeRequest("all", "all");
    }

    const makeDelete = async (field, value) => {
        const response = await axios.delete(`${DRIVER_URL}/${value}`, config);
        setData(response?.data);
        makeRequest("all", "all");
    }

    useEffect(() => {
        makeRequest("all", "all");
    }, []);

    return (
        <section>
            <h2>Drivers</h2>
            <div className="queries">
                <div>
                    <label htmlFor="byID">Query by ID</label>
                    <input type="text" id="byID" onChange={(e) => setID(e.target.value)} value={id} />
                    <button onClick={() => makeRequest("id", id)}>Execute Query</button>
                </div>
                <div>
                    <label htmlFor="all">Query All</label>
                    <button id="all" onClick={() => makeRequest("all", "all")}>Execute Query</button>
                </div>
            </div>
            <p>You can edit data about drivers</p>
            <div className="container">
                <h2>Data Table</h2>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            // Add other headers for other fields in the Driver model
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Create new:</td>
                            // Add inputs for other fields in the Driver model
                            <td><button onClick={() => makeCreate()}>Create</button></td>
                        </tr>
                        {data?.map((item) => (
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                // Add inputs for other fields in the Driver model
                                <td>
                                    <button onClick={() => makeEdit(item.id)}>Edit</button>
                                    <button onClick={() => makeDelete("delete", item.id)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="home-page__button">
                <Link to="/admin_view">Back</Link>
            </div>
        </section>
    )
}

export default EditDrivers