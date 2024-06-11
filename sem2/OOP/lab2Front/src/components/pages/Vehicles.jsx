import { Link } from "react-router-dom"
import "./Entity.css"
import "../Home.css"
import axios from "../../api/axios"
import { useState } from "react"
import getHeaderConfig from "../hooks/Config"
const VEHICLE_URL = "/vehicle";

const Vehicles = () => {
    const [data, setData] = useState([]);
    const [id, setID] = useState('');

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

    return (
        <section>
            <h2>Vehicles</h2>
            <p>You can query data about vehicles</p>
            <div className="container">
                <h2>Data Table</h2>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Model</th>
                        <th>Make</th>
                        <th>Plate Number</th>
                        <th>Vehicle Category</th>
                        <th>Year</th>
                        <th>Color</th>
                        <th>Mileage</th>
                        <th>Last Service</th>
                        <th>Has Airbags</th>
                        <th>Has ABS</th>
                        <th>Is Wheelchair Accessible</th>
                        <th>Condition</th>
                        <th>Pet Friendly</th>
                        <th>Child Seat Required</th>
                        <th>Info</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data?.map((item) => (
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.model}</td>
                            <td>{item.make}</td>
                            <td>{item.plateNumber}</td>
                            <td>{item.vehicleCategory}</td>
                            <td>{item.year}</td>
                            <td>{item.color}</td>
                            <td>{item.mileage}</td>
                            <td>{item.lastService}</td>
                            <td>{item.hasAirbags ? 'Yes' : 'No'}</td>
                            <td>{item.hasABS ? 'Yes' : 'No'}</td>
                            <td>{item.isWheelchairAccessible ? 'Yes' : 'No'}</td>
                            <td>{item.condition}</td>
                            <td>{item.petFriendly ? 'Yes' : 'No'}</td>
                            <td>{item.childSeatRequired ? 'Yes' : 'No'}</td>
                            <td>{item.info}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
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
            <div className="home-page__button">
                <Link to="/view">Back</Link>
            </div>
        </section>
    )
}

export default Vehicles