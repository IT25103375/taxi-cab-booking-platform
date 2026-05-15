import { routes } from './router/Routes'
import './pages/Home.css'
import { createBrowserRouter, RouterProvider  } from "react-router-dom"
import {UserProvider} from "./context/useAuth.tsx";
import {RegionProvider} from "./context/useRegion.tsx";
import {ToastContainer} from "react-toastify";
import {TripProvider} from "./context/useTrip.tsx";
import {VehicleProvider} from "./context/useVehicle.tsx";
const router = createBrowserRouter(routes)

function App() {
    return (
        <UserProvider>
            <RegionProvider>
                <TripProvider>
                    <VehicleProvider>
                        <RouterProvider router={router}/>
                        <ToastContainer/>
                    </VehicleProvider>
                </TripProvider>
            </RegionProvider>
        </UserProvider>
    )
}

export default App
