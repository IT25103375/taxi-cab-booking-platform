import React, {createContext, useContext, useEffect, useState} from "react";
import { tripAPI } from "../services/TripService.tsx";
import {handleError} from "../helpers/ErrorHandler.tsx";
import type {RegionGet} from "../models/Region.ts";
import type {SubRegionGet} from "../models/SubRegion.ts";
import type {TripGet} from "../models/Trip.ts";
import type {UserType} from "../enums/UserType.ts";
import {registerAPI} from "../services/AuthService.tsx";
import {Bounce, toast} from "react-toastify";
import type {VehicleTypeGet} from "../models/VehicleType.ts";
import {vehicleTypeAPI} from "../services/VehicleService.tsx";

type VehicleContextType = {
    vehicleTypes: VehicleTypeGet[] | null;
    loadingVehicleTypes: boolean;
}

type Props = { children: React.ReactNode };

const VehicleContext = createContext<VehicleContextType>({} as VehicleContextType);

export const VehicleProvider = ({children} : Props) => {

    const [vehicleTypes, setVehicleTypes] = useState<VehicleTypeGet[] | null>([]);
    const [loadingVehicleTypes, setVehicleTypeLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchVehicleTypes = async () => {
            try {
                setVehicleTypeLoading(true);
                console.log("LOGGED")
                const res = await vehicleTypeAPI.getAllTypes();
                if (res) setVehicleTypes(res.data);
            } catch (error) {
                handleError(error);
            } finally {
                setVehicleTypeLoading(false);
            }
        };
        fetchVehicleTypes();
    }, []);

    return (
        <VehicleContext.Provider value={
            {loadingVehicleTypes, vehicleTypes}}>
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