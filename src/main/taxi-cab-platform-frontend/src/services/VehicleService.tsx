import axios from "axios";
import { handleError } from "../helpers/ErrorHandler";
import type {VehicleTypeList} from "../models/VehicleType.ts";
import type {VehicleGet, VehicleList} from "../models/Vehicle.ts";

const api="http://localhost:8090/";

export const vehicleTypeAPI = {
    getAllTypes: async () => {
        try {
            return await axios.get<VehicleTypeList>(api + "api/vehicle/get-types", {});
        } catch (error) {
            handleError(error);
        }
    }
}

export const vehicleAPI = {

    createVehicle: async (displayName: string, plateNumber: string, vehicleTypeId: number) => {
        try {
            return await axios.post<VehicleGet>(api + "api/vehicle/register", {
                displayName: displayName,
                plateNumber: plateNumber,
                vehicleTypeId: vehicleTypeId,
            });
        } catch (error) {
            handleError(error);
        }
    },

    currentVehicle: async (vehicleId: number) => {
        try {
            return await axios.patch<boolean>(api + "api/vehicle/current", {
                vehicleId: vehicleId,
            });
        } catch (error) {
            handleError(error);
        }
    },

    removeVehicle: async (vehicleId: number) => {
        try {
            return await axios.delete<boolean>(api + "api/vehicle/remove", {
                data: {
                    vehicleId: vehicleId,
                }
            });
        } catch (error) {
            handleError(error);
        }
    },

    getAllById: async () => {
        try {
            return await axios.get<VehicleList>(api + "api/vehicle/get", {});
        } catch (error) {
            handleError(error);
        }
    },

    getCurrent: async () => {
        try {
            return await axios.get<VehicleGet>(api + "api/vehicle/get-current", {});
        } catch (error) {
            handleError(error);
        }
    }
}