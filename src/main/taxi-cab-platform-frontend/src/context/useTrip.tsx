import React, {createContext, useContext, useEffect, useState} from "react";
import { tripAPI } from "../services/TripService.tsx";
import {handleError} from "../helpers/ErrorHandler.tsx";
import type {RegionGet} from "../models/Region.ts";
import type {SubRegionGet} from "../models/SubRegion.ts";
import type {TripGet} from "../models/Trip.ts";
import type {UserType} from "../enums/UserType.ts";
import {registerAPI} from "../services/AuthService.tsx";
import {Bounce, Slide, toast} from "react-toastify";

type TripContextType = {
    trips: TripGet[] | null;
    loadingTrips: boolean;
    setFetchTrip: (trigger: boolean) => void;
    bookTrip: (startAddress: string, startSubRegionId: number, destAddress: string,
               destSubRegionId: number, vehicleTypeId: number) => void;
}

type Props = { children: React.ReactNode };

const TripContext = createContext<TripContextType>({} as TripContextType);

export const TripProvider = ({children} : Props) => {

    const [trips, setTrips] = useState<TripGet[] | null>([]);
    const [loadingTrips, setTripLoading] = useState<boolean>(true);
    const [fetchTrip, setFetchTrip] = useState<boolean>(true);

    useEffect(() => {
        const fetchTrips = async () => {
            try {
                setTripLoading(true);
                console.log("LOGGED")
                const res = await tripAPI.getTrips();
                if (res) setTrips(res.data);
            } catch (error) {
                handleError(error);
            } finally {
                setTripLoading(false);
            }
        };

        fetchTrips();
    }, [fetchTrip]);

    const bookTrip =
        async (startAddress: string, startSubRegionId: number, destAddress: string,
               destSubRegionId: number, vehicleTypeId: number) => {
            await tripAPI.postTrip(vehicleTypeId, startAddress, startSubRegionId, destAddress, destSubRegionId)
                .then((res) => {
                    if(res) {
                        toast.success("Trip booked", {
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
        }

    return (
        <TripContext.Provider value={
            {loadingTrips, trips, bookTrip, setFetchTrip}}>
            {children}
        </TripContext.Provider>
    )
}

// 4. Custom hook wrapper for cleaner components and runtime safety
export const useTrip = () => {
    const context = useContext(TripContext);
    if (!context) {
        throw new Error("useTrip must be used within a TripProvider");
    }
    return context;
};