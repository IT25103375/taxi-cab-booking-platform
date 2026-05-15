import axios from "axios";
import { handleError } from "../helpers/ErrorHandler";
import type {VehicleTypeList} from "../models/VehicleType.ts";

const api="http://localhost:8090/";

export const vehicleTypeAPI = {
    getAllTypes: async () => {
        try {
            return await axios.get<VehicleTypeList>(api + "api/vehicle/types", {});
        } catch (error) {
            handleError(error);
        }
    }
}