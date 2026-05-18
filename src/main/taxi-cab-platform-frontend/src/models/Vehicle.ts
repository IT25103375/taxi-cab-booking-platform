export type VehicleGet = {
    id: number;
    displayName: string;
    vehicleTypeId: number;
    plateNumber: string;
}

export type VehicleList = VehicleGet[] | null;