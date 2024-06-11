import { Link } from "react-router-dom";
import '../Home.css';

const AdminView = () =>{

    return (
        <div className="home-page">
            <h2>Welcome to Viewing page</h2>
            <div className="home-page__links">
                <Link to="/vehicle_edit" className="home-page__link">Edit Vehicles data</Link>
                <Link to="/request_edit" className="home-page__link">Edit Requests data</Link>
                <Link to="/driver_edit" className="home-page__link">Edit Drivers data</Link>
                <Link to="/flight_edit" className="home-page__link">Edit Flights data</Link>
            </div>
            <div className="home-page__button">
                <Link to="/home">Back</Link>
            </div>
        </div>
    );
}

export default AdminView