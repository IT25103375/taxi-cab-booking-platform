import React, {createContext, useContext, useEffect, useState} from "react";
import {regionAPI, subRegionAPI} from "../services/RegionService.tsx";
import {handleError} from "../helpers/ErrorHandler.tsx";
import type {RegionGet} from "../models/Region.ts";
import type {SubRegionGet} from "../models/SubRegion.ts";
import {useAuth} from "./useAuth.tsx";

type RegionContextType = {
    regionId: number | null;
    regionId2: number | null;
    regions: RegionGet[] | null;
    subRegions : SubRegionGet[] | null;
    setSubRegions: (subRegions: SubRegionGet[] | null) => void;
    setSubRegions2: (subRegions: SubRegionGet[] | null) => void;
    subRegions2: SubRegionGet[] | null;
    loadingRegions: boolean;
    loadingSubRegions: boolean;
    loadingSubRegions2: boolean;
    setRegionId: (id: number | null) => void;
    setRegionId2: (id: number | null) => void;
}

type Props = { children: React.ReactNode };

const RegionContext = createContext<RegionContextType>({} as RegionContextType);

export const RegionProvider = ({children} : Props) => {

    const { isLoggedIn } = useAuth();

    const [regionId, setRegionId] = useState<number | null>(null);
    const [regionId2, setRegionId2] = useState<number | null>(null);
    const [regions, setRegions] = useState<RegionGet[] | null>([]);
    const [subRegions, setSubRegions] = useState<SubRegionGet[] | null>([]);
    const [subRegions2, setSubRegions2] = useState<SubRegionGet[] | null>([]);
    const [loadingRegions, setRegionLoading] = useState<boolean>(true);
    const [loadingSubRegions, setSubRegionLoading] = useState<boolean>(true);
    const [loadingSubRegions2, setSubRegionLoading2] = useState<boolean>(true);

    useEffect(() => {
        if (!isLoggedIn()) return;
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
        if (!isLoggedIn()) return;
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

    useEffect(() => {
        if (!isLoggedIn()) return;
        if (!regionId2) {
            setSubRegions2([]);
            return;
        }

        const fetchSubRegions2 = async () => {
            try {
                setSubRegionLoading2(true);
                const res = await subRegionAPI(regionId2);
                if (res) setSubRegions2(res.data);
            } catch (error) {
                handleError(error);
            } finally {
                setSubRegionLoading2(false);
            }
        };

        fetchSubRegions2();
    }, [regionId2]);

    return (
        <RegionContext.Provider value={
            {loadingRegions, loadingSubRegions, loadingSubRegions2, regions, subRegions, setSubRegions, subRegions2, setSubRegions2, regionId, regionId2, setRegionId, setRegionId2}}>
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