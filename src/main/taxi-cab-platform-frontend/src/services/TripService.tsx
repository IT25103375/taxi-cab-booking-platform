import axios from "axios";
import { handleError } from "../helpers/ErrorHandler";
import type {TripList, TripSet} from "../models/Trip.ts";

const api="http://localhost:8090/";

export const tripAPI = {
    getTrips: async () => {
        try{
            return await axios.get<TripList>(api + "api/booking/trip", {
            });
        }
        catch(error)
        {
            handleError(error);
        }
    },

    postTrip: async (vehicleTypeId: number, startAddress: string, startSubRegionId: number,
                     destAddress: string, destSubRegionId: number) => {
        try{
            return await axios.post<TripSet>(api + "api/booking/trip", {
                vehicleTypeId: vehicleTypeId,
                startAddress: startAddress,
                startSubRegionId: startSubRegionId,
                destAddress: destAddress,
                destSubRegionId: destSubRegionId
            })
        }
        catch (error)
        {
            handleError(error);
        }
    }
}