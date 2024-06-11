import { Link } from "react-router-dom"
import "./Entity.css"
import "../Home.css"
import axios from "../../api/axios"
import { useState, useEffect } from "react"
import getHeaderConfig from "../hooks/Config"
const VEHICLE_URL = "/vehicle";

const EditVehicles = () => {
    const [data, setData] = useState([]);
    const [id, setID] = useState('');
    const [model, setModel] = useState('');
    const [make, setMake] = useState('');
    // Add other state variables for other fields in the Vehicle model

    const config = getHeaderConfig();

    const makeRequest = async (field, value) => {
        const response = await axios.get(VEHICLE_URL, {
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
        const response = await axios.put(`${VEHICLE_URL}/${id}`, JSON.stringify({
            id: id,
            model: model,
            make: make,
            // Add other fields from the Vehicle model
        }), config);
        setData(response?.data);
        makeRequest("all", "all");
    }

    const makeCreate = async () => {
        const response = await axios.post(VEHICLE_URL, JSON.stringify({
            model: model,
            make: make,
            // Add other fields from the Vehicle model
        }), config);
        setData(response?.data);
        makeRequest("all", "all");
    }

    const makeDelete = async (field, value) => {
        const response = await axios.delete(`${VEHICLE_URL}/${value}`, config);
        setData(response?.data);
        makeRequest("all", "all");
    }

    useEffect(() => {
        makeRequest("all", "all");
    }, []);

    return (
        <section>
            <h2>Vehicles</h2>
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
            <p>You can edit data about vehicles</p>
            <div className="container">
                <h2>Data Table</h2>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Model</th>
                            <th>Make</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Create new:</td>
                            <td><input type="text" onChange={(e) => setModel(e.target.value)} value={model} /></td>
                            <td><input type="text" onChange={(e) => setMake(e.target.value)} value={make} /></td>
                            <td><button onClick={() => makeCreate()}>Create</button></td>
                        </tr>
                        {data?.map((item) => (
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                <td><input type="text" onChange={(e) => {
                                    const newData = [...data];
                                    newData.find((el) => el.id === item.id).model = e.target.value;
                                    setData(newData);
                                }} value={item.model} /></td>
                                <td><input type="text" onChange={(e) => {
                                    const newData = [...data];
                                    newData.find((el) => el.id === item.id).make = e.target.value
                                    setData(newData);
                                }} value={item.make} /></td>
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

export default EditVehicles