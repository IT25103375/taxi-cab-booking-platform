export type VehicleGet = {
    id: number;
    displayName: string;
    vehicleTypeId: number;
}

export type VehicleList = VehicleGet[] | null;