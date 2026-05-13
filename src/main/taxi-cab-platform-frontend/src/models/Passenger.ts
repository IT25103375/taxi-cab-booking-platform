import type {UserType} from "../enums/UserType.ts";

export type PassengerGet = {
    username: string;
    ID : number;
}
//TODO: Remove username and password fields, get email from auth object

export type PassengerPost = {
    username : string;
    email : string;
    password : string;
    userType: UserType;
}

export type UserProfileToken = {
    email: string;
    username: string;
    token: string;
}