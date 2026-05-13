import React, {createContext, useContext, useEffect, useState} from "react";
import {regionAPI, subRegionAPI} from "../services/RegionService.tsx";
import {handleError} from "../helpers/ErrorHandler.tsx";
import type {RegionGet} from "../models/Region.ts";
import type {SubRegionGet} from "../models/SubRegion.ts";

type RegionContextType = {
    regionId: number | null;
    regions: RegionGet[] | null;
    subRegions : SubRegionGet[] | null;
    loadingRegions: boolean;
    loadingSubRegions: boolean;
    setRegionId: (id: number | null) => void;
}

type Props = { children: React.ReactNode };

const RegionContext = createContext<RegionContextType>({} as RegionContextType);

export const RegionProvider = ({children} : Props) => {

    const [regionId, setRegionId] = useState<number | null>(null);
    const [regions, setRegions] = useState<RegionGet[] | null>([]);
    const [subRegions, setSubRegions] = useState<SubRegionGet[] | null>([]);
    const [loadingRegions, setRegionLoading] = useState<boolean>(true);
    const [loadingSubRegions, setSubRegionLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchRegions = async () => {
            try {
                setRegionLoading(true);
                const res = await regionAPI();
                if (res) setRegions(res.data);
            } catch (error) {
                handleError(error);
            } finally {
                setRegionLoading(false);
            }
        };
        fetchRegions();
    }, []);

    useEffect(() => {
        if (!regionId) {
            setSubRegions([]);
            return;
        }

        const fetchSubRegions = async () => {
            try {
                setSubRegionLoading(true);
                const res = await subRegionAPI(regionId);
                if (res) setSubRegions(res.data);
            } catch (error) {
                handleError(error);
            } finally {
                setSubRegionLoading(false);
            }
        };

        fetchSubRegions();
    }, [regionId]);

    return (
        <RegionContext.Provider value={
            {loadingRegions, loadingSubRegions, regions, subRegions, regionId, setRegionId}}>
            {children}
        </RegionContext.Provider>
    )
}

// 4. Custom hook wrapper for cleaner components and runtime safety
export const useRegion = () => {
    const context = useContext(RegionContext);
    if (!context) {
        throw new Error("useRegion must be used within a RegionProvider");
    }
    return context;
};