export const TripStatus = {
    Requesting: "REQUESTING",
    Pickup: "PICKUP",
    Ongoing: "ONGOING",
    Finished: "FINISHED",
    Cancelled: "CANCELLED"
} as const;

export type TripStatus = (typeof TripStatus)[keyof typeof TripStatus];