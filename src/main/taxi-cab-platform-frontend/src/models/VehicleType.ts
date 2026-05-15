export type VehicleTypeGet = {
    id: number;
    name: string;
    displayName: string;
    maxPassengers: number;
}

export type VehicleTypeList = VehicleTypeGet[] | null;