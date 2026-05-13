import axios from "axios";
import { handleError } from "../helpers/ErrorHandler";
import type { UserProfileToken, PassengerPost } from "../models/Passenger";
import type {UserType} from "../enums/UserType.ts";

const api="http://localhost:8090/";

export const loginAPI = async (email: string,password: string) => {
    try{
        return await axios.post<UserProfileToken>(api + "api/user/auth/login", {
            email: email,
            password: password,
        });
    }
    catch(error)
    {
        handleError(error);
    }
}

export const registerAPI =
    async (username: string, email: string, password: string,
           userType: UserType, regionId: number | null, subRegionId: number | null) => {
    try{
        return await axios.post<PassengerPost>(api + "api/user/auth/register", {
            username: username,
            email: email,
            password: password,
            userType: userType,
            regionId: regionId,
            subRegionId: subRegionId,
        });
    }
    catch(error)
    {
        handleError(error);
    }
}