export type RegionGet = {
    regionId : number;
    regionName : string;
    regionDisplayName : string;
}

export type RegionList = RegionGet[] | null;