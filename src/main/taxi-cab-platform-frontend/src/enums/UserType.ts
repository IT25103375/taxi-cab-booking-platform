export const UserType = {
    Passenger: "PASSENGER",
    Driver: "DRIVER",
    Admin: "ADMIN",
} as const;

export const ValidUserTypes = {
    Passenger: "PASSENGER",
    Driver: "DRIVER",
} as const;

export type UserType = (typeof UserType)[keyof typeof UserType];
export type ValidUserTypes = (typeof ValidUserTypes)[keyof typeof ValidUserTypes];