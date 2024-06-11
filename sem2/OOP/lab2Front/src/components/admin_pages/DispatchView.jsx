import { Link } from "react-router-dom";
import '../Home.css';

const DispatchView = () =>{

    return (
        <div className="home-page">
            <h2>Welcome to Dispatch page</h2>
            <div className="home-page__links">
                   <Link to="/dispatch_flight" className="home-page__link">Dispatch Flight data</Link>
            </div>
            <div className="home-page__button">
                <Link to="/home">Back</Link>
            </div>
        </div>
    );
}

export default DispatchView