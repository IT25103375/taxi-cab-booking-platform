import LoginPage from "../pages/LoginPage.tsx";
import {redirect, type RouteObject } from 'react-router-dom';
import RegisterPage from "../pages/RegisterPage.tsx";
import TripPage from "../pages/TripPage.tsx";
import ProtectedRoute from "./ProtectedRoute.tsx";
import Dashboard from "../pages/Dashboard.tsx";
import VehiclePage from "../pages/VehiclePage.tsx";

export const routes: RouteObject[] = [
    {
        path: '/',
        loader: () => redirect("/register")
    },
    {
        path: '/register',
        element: <RegisterPage />
    },
    {
        path: "/login",
        element: <LoginPage />
    },
    {
        path: "/trip",
        element: <ProtectedRoute><TripPage /></ProtectedRoute>
    },
    {
        path: "/dashboard",
        element: <ProtectedRoute><Dashboard /></ProtectedRoute>
    },
    {
        path: "/vehicles",
        element: <ProtectedRoute><VehiclePage /></ProtectedRoute>
    }
]
