import type {TripStatus} from "../enums/TripStatus.ts";

export type TripGet = {
    passengerName: string,
    driverName: string,
    startAddress: string,
    startRegion: string,
    startSubRegion: string,
    destAddress: string,
    destRegion: string,
    destSubRegion: string,
    vehicleType: string,
    tripStatus: TripStatus,
    expDate: string,
}

export type TripSet = {
    startAddress: string,
    startSubRegionId: number,
    destAddress: string,
    destSubRegionId: number,
    vehicleTypeId: number,
}

export type TripList = TripGet[] | null;