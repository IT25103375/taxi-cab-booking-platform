import { routes } from './router/ReactRouter'
import './pages/Home.css'
import { createBrowserRouter, RouterProvider  } from "react-router-dom"
import {UserProvider} from "./context/useAuth.tsx";
import {RegionProvider} from "./context/useRegion.tsx";
import {ToastContainer} from "react-toastify";
const router = createBrowserRouter(routes)

function App() {
    return (
        <UserProvider>
            <RegionProvider>
                <RouterProvider router={router}/>
                <ToastContainer/>
            </RegionProvider>
        </UserProvider>
    )
}

export default App
