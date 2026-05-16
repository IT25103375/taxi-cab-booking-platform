import {useAuth} from "../context/useAuth.tsx";
import {UserType} from "../enums/UserType.ts";
import type {TripGet} from "../models/Trip.ts";
import {TripStatus} from "../enums/TripStatus.ts";
import type {UserProfile} from "../models/User.ts";

export type TripFunctions =
    "AcceptTrip"|
    "PickupTrip"|
    "FinishTrip"|
    "CancelTrip";

export function tripFuncButton(TripFunction: TripFunctions, trip: TripGet | null, user: UserProfile | null) {

    if (!user) return false;
    if (!trip) return false;

    switch (TripFunction) {
        case "AcceptTrip":
            return (user.role == UserType.Driver && trip.tripStatus == TripStatus.Requesting)

        case "PickupTrip":
            return (user.role == UserType.Driver && trip.tripStatus == TripStatus.Pickup)

        case "FinishTrip":
            return (user.role == UserType.Driver && trip.tripStatus == TripStatus.Ongoing)

        case "CancelTrip":
            return (trip.tripStatus == TripStatus.Pickup || trip.tripStatus == TripStatus.Ongoing)
                || (user.role == UserType.Passenger && trip.tripStatus == TripStatus.Requesting)

    }
}