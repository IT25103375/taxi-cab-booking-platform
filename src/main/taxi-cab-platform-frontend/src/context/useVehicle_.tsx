import React, {createContext, useContext, useEffect, useState} from "react";
import { vehicleAPI } from "../services/VehicleService.tsx";
import {handleError} from "../helpers/ErrorHandler.tsx";
import {Bounce, Slide, toast} from "react-toastify";
import type {VehicleTypeGet} from "../models/VehicleType.ts";
import {vehicleTypeAPI} from "../services/VehicleService.tsx";
import {useAuth} from "./useAuth.tsx";
import type {VehicleGet, VehicleList} from "../models/Vehicle.ts";

type VehicleContextType = {
    createVehicle: (displayName: string, plateNumber: string, vehicleTypeId: number) => void;
    removeVehicle: (vehicleId: number) => void;
    currentVehicle: (vehicleId: number) => void;
    fetchVehicles: number;
    setFetchVehicles: (trigger: number) => void;
    loadingVehicle: boolean;
    vehicles: VehicleList | null;
}

type Props = { children: React.ReactNode };

const VehicleContext = createContext<VehicleContextType>({} as VehicleContextType);

export const VehicleProvider = ({children} : Props) => {

    const { user, isLoggedIn } = useAuth();
    const [vehicles, setVehicle] = useState<VehicleGet[] | null>([]);
    const [fetchVehicles, setFetchVehicles] = useState<number>(0);
    const [loadingVehicle, setVehicleLoading] = useState<boolean>(true);

    useEffect(() => {
        if (!isLoggedIn()) return;
        const fetchAllVehicles = async () => {
            try {
                setVehicleLoading(true);
                const res = await vehicleAPI.getAllById();
                if (res) setVehicle(res?.data);
            } catch (error) {
                handleError(error);
            } finally {
                setVehicleLoading(false);
            }
        };
        fetchAllVehicles();
    }, [fetchVehicles]);

    const createVehicle =
        async (displayName: string, plateNumber: string, vehicleTypeId: number) => {
            if (!isLoggedIn()) return;
            await vehicleAPI.createVehicle(displayName, plateNumber, vehicleTypeId)
                .then((res) => {
                    if(res) {
                        toast.success("Vehicle created", {
                            hideProgressBar: true,
                            closeOnClick: true,
                            transition: Slide,
                            position: "bottom-right",
                        })
                        return res;
                    }
                }).catch((e) => toast.warning("Server error occurred", {
                    hideProgressBar: true,
                    closeOnClick: true,
                    transition: Bounce,
                    position: "bottom-right",
                }))

            setFetchVehicles(fetchVehicles + 1);
        }

    const removeVehicle =
        async (vehicleId: number) => {
            if (!isLoggedIn()) return;
            await vehicleAPI.removeVehicle(vehicleId)
                .then((res) => {
                    if(res) {
                        toast.success("Vehicle removed", {
                            hideProgressBar: true,
                            closeOnClick: true,
                            transition: Slide,
                            position: "bottom-right",
                        })
                        return res;
                    }
                }).catch((e) => toast.warning("Server error occurred", {
                    hideProgressBar: true,
                    closeOnClick: true,
                    transition: Bounce,
                    position: "bottom-right",
                }))

            setFetchVehicles(fetchVehicles + 1);
        }

    const currentVehicle =
        async (vehicleId: number) => {
            if (!isLoggedIn()) return;
            await vehicleAPI.currentVehicle(vehicleId)
                .then((res) => {
                    if(res) {
                        toast.success("Vehicle set as current", {
                            hideProgressBar: true,
                            closeOnClick: true,
                            transition: Slide,
                            position: "bottom-right",
                        })
                        return res;
                    }
                }).catch((e) => toast.warning("Server error occurred", {
                    hideProgressBar: true,
                    closeOnClick: true,
                    transition: Bounce,
                    position: "bottom-right",
                }))

            setFetchVehicles(fetchVehicles + 1);
        }

    return (
        <VehicleContext.Provider value={
            {createVehicle, fetchVehicles, removeVehicle, vehicles, setFetchVehicles, loadingVehicle, currentVehicle}}>
            {children}
        </VehicleContext.Provider>
    )
}

// 4. Custom hook wrapper for cleaner components and runtime safety
export const useVehicle = () => {
    const context = useContext(VehicleContext);
    if (!context) {
        throw new Error("useTrip must be used within a TripProvider");
    }
    return context;
};