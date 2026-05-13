import LoginPage from "../pages/LoginPage.tsx";
import { type RouteObject } from 'react-router-dom';
import Home from "../pages/Home.tsx";
import RegisterPage from "../pages/RegisterPage.tsx";

export const routes: RouteObject[] = [
    {
        path: '/',
        element: <Home />
    },
    {
        path: '/register',
        element: <RegisterPage />
    },
    {
        path: "/login",
        element: <LoginPage />
    }
]
