import type {UserType} from "../enums/UserType.ts";

export type DriverGet = {
    username: string;
    ID : number;
}
//TODO: Remove username and password fields, get email from auth object

export type DriverPost = {
    username : string;
    email : string;
    password : string;
    userType: UserType;
    regionId: string;
    subRegionId: string;
}

export type UserProfileToken = {
    email: string;
    username: string;
    token: string;
}