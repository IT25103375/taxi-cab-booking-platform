import axios from "axios";
import { handleError } from "../helpers/ErrorHandler";
import type {RegionList} from "../models/Region.ts";
import type {SubRegionList} from "../models/SubRegion.ts";

const api="http://localhost:8090/";

export const regionAPI = async () => {
    try{
        return await axios.get<RegionList>(api + "api/region/regions", {
        });
    }
    catch(error)
    {
        handleError(error);
    }
}

export const subRegionAPI = async (regionId: number) => {
    try{
        return await axios.get<SubRegionList>(api + "api/region/sub-regions", {
            params: {
                "region_id": regionId
            }
        });
    }
    catch(error)
    {
        handleError(error);
    }
}