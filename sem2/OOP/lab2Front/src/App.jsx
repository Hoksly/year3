
import './App.css'
import Login from './components/Login/Login.jsx'
import Layout from './components/Layout.jsx'
import Home from './components/Home.jsx'
import { Routes, Route } from 'react-router-dom'
import Unauthorized from './components/Unauthorized.jsx'
import RequireAuth from './components/RequireAuth.jsx'
import Flights from './components/pages/Flights.jsx'
import View from './components/pages/View.jsx'
import AdminView from './components/admin_pages/AdminView.jsx'
import DispatchView from './components/admin_pages/DispatchView.jsx'
import EditFlight from "./components/admin_pages/EditFlights.jsx";
import Requests from "./components/pages/Request.jsx";
import Drivers from "./components/pages/Drivers.jsx";
import Vehicles from "./components/pages/Vehicles.jsx";
import RequestEdit from "./components/admin_pages/RequestEdit.jsx";
import EditVehicles from "./components/admin_pages/EditVehicles.jsx";
import DriversEdit from "./components/admin_pages/DriversEdit.jsx";

const ROLES = {
  Admin: "admin",
  Dispatch: "dispatch",
  User: "user"
}


function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route path='/' element={<Login />} />
        <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
          <Route path="admin_view" element={<AdminView />} />

          <Route path='drive_edit' element={DriversEdit} />
          <Route path='request_edit' element={<RequestEdit />} />
          <Route path='vehicle_edit' element={<EditVehicles />} />

        </Route>
        <Route element={<RequireAuth allowedRoles={[ROLES.Admin, ROLES.Dispatch, ROLES.User]} />}>
          <Route path='vehicle_view' element={<Vehicles />} />
          <Route path='driver_view' element={<Drivers />} />
          <Route path='flights_view' element={<Flights />} />
          <Route path='request_view' element={<Requests />} />
          <Route path='view' element={<View />} />
        </Route>
        <Route element={<RequireAuth allowedRoles={[ROLES.Admin, ROLES.Dispatch]} />}>
          <Route path="dispatch_view" element={<DispatchView />} />
          <Route path='flight_edit' element={<EditFlight />} />
        </Route>
        <Route path='unauthorized' element={<Unauthorized />} />
        <Route path='home' element={<Home />} />
      </Route>
    </Routes>
  )
}

export default App
