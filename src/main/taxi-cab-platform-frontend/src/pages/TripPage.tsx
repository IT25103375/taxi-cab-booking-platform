import * as Yup from 'yup';
import {useTrip} from "../context/useTrip.tsx"
import {useEffect, useState} from "react";
import type {TripGet} from "../models/Trip.ts";
import Popup from "../components/Popup.tsx";
import {set, useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {iconMap} from "../components/VehicleMap.tsx";
import {useVehicle} from "../context/useVehicle.tsx";
import {useRegion} from "../context/useRegion.tsx";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.tsx";
import { tripFuncButton } from "../helpers/TripHelper.tsx"
import {useAuth} from "../context/useAuth.tsx";

const TripPage = () => {

    type TripFormsInput = {
        vehicleTypeId: number;
        startAddress: string;
        startSubRegionId: number;
        destAddress: string;
        destSubRegionId: number;
    }

    const validation = Yup.object().shape({
        vehicleTypeId: Yup.number().required("Vehicle Type is required"),
        startAddress: Yup.string().required("StartAddress is required"),
        startSubRegionId: Yup.number().required("StartSubRegion is required"),
        destAddress: Yup.string().required("DestAddress is required"),
        destSubRegionId: Yup.number().required("DestSubRegion is required")
    });

    const navigate = useNavigate();
    const { user } = useAuth();
    const { vehicleTypes, loadingVehicleTypes } = useVehicle();
    const { trips, loadingTrips, bookTrip, cancelTrip } = useTrip();
    const [ selectedTrip, setSelectedTrip ] = useState<TripGet | null>(null);
    const [ tripWindowOpen, setTripWindowOpen ] = useState(false);

    const { register, handleSubmit, setValue, watch, formState: {errors}} = useForm<TripFormsInput>({ resolver: yupResolver(validation)})
    const handleTripRegister = (form: TripFormsInput) => {
        bookTrip(form.startAddress, form.startSubRegionId, form.destAddress, form.destSubRegionId, form.vehicleTypeId);
        // TODO : Properly update instead of force refreshing
    }

    const { regions, subRegions, subRegions2, loadingRegions, loadingSubRegions, loadingSubRegions2,
        setRegionId, setRegionId2 } = useRegion();

    return (
        <>
            <Navbar/>
            <div className="flex flex-col h-screen gap-4 p-4">
                <section className="h-64 overflow-y-auto space-y-3 rounded-xl border p-3">
                    <div>
                        {trips?.map((trip, index) => (
                            <button
                                key={index}
                                onClick={() => setSelectedTrip(trip)}
                                className="w-full rounded-lg border p-4 text-left hover:bg-gray-900">
                                <div className="font-semibold">
                                    {trip.startAddress + ", " + trip.startSubRegion + ", " + trip.startRegion}  →
                                    {" " + trip.destAddress  + ", " + trip.destSubRegion + ", " + trip.destRegion}
                                </div>
                                <div className="text-sm text-gray-200">
                                    {"FIXME-DATE"} • {trip.tripStatus.charAt(0) + trip.tripStatus.slice(1).toLowerCase()}
                                </div>
                                <div className="text-sm font-medium">{"FIXME-FARE"}</div>
                            </button>
                        ))}
                    </div>
                </section>
                <section className="flex-1 rounded-xl border p-6">
                    <div className="flex flex-row space-x-4">
                        <div className="flex justify-start">
                            <button
                                onClick={() => setTripWindowOpen(true)}
                                className="text-white text-l border bg-purple-900 border-purple-800 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center hover:bg-purple-950"
                            >
                                New Trip
                            </button>
                        </div>
                        <div className="flex justify-end">
                            <button
                                onClick={() => {if (selectedTrip) cancelTrip(selectedTrip.id)}}
                                disabled={!tripFuncButton("CancelTrip", selectedTrip, user)}
                                className="text-white text-l border bg-purple-900 border-purple-800 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center hover:bg-purple-950
                                disabled:bg-gray-100 disabled:text-gray-400
                                        disabled:border-gray-200 disabled:cursor-not-allowed
                                        disabled:opacity-100"
                            >
                                Cancel Trip
                            </button>
                        </div>
                    </div>
                    <hr className="w-full border-b-4 border-white-1000 my-4"></hr>
                    {selectedTrip ? (
                        <div className="space-y-4">
                            <h2 className="text-2xl font-bold">Trip Details</h2>
                            <div>
                                <p><strong>Pickup:</strong> {selectedTrip.startAddress + ", " + selectedTrip.startSubRegion + ", " + selectedTrip.startRegion}</p>
                                <p><strong>Dropoff:</strong> {selectedTrip.destAddress  + ", " + selectedTrip.destSubRegion + ", " + selectedTrip.destRegion}</p>
                                <p><strong>Date:</strong> {"FIXME-DATE"}</p>
                                <p><strong>Driver:</strong> {(selectedTrip.driverName) ? selectedTrip.driverName : "Not assigned"}</p>
                                <p><strong>Fare:</strong> {"FIXME-FARE"}</p>
                                <p><strong>Status:</strong> {selectedTrip.tripStatus.charAt(0) + selectedTrip.tripStatus.slice(1).toLowerCase()}</p>
                            </div>
                        </div>
                    ) : (
                        <p className="text-gray-500">Select a trip to view details.</p>
                    )}
                </section>

                <Popup
                    isOpen={tripWindowOpen}
                    size="xl"
                    onClose={() => setTripWindowOpen(false)}
                >
                    <div className="bg-zinc-900 p-6 space-y-4 md:space-y-6 sm:p-8">
                        <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                            Book a trip
                        </h1>
                        <div className="flex space-x-1 overflow-x-auto">
                            {vehicleTypes?.map((vehicleType) => {
                                const IconComponent = iconMap[vehicleType.name];

                                return (
                                    <button
                                        key={vehicleType.id}
                                        onClick={() => setValue("vehicleTypeId", vehicleType.id)}
                                        className={"shrink-0 w-35 rounded-lg border p-4 text-left active:bg-zinc-700"
                                            + ((watch("vehicleTypeId") == vehicleType.id) ? " bg-zinc-600 border-zinc-400 text-zinc-200" : " hover:bg-zinc-700")}
                                    >
                                        <div className="flex flex-col justify-center items-center">
                                            {IconComponent ? <IconComponent size="5em"/> : null}
                                            <p className="text-center"> {vehicleType.displayName} Passengers: {vehicleType.maxPassengers}</p>
                                        </div>
                                    </button>
                                );
                            })}
                        </div>
                        {errors.vehicleTypeId ? <p className="text-red-500">{errors.vehicleTypeId.message}</p> : ""}
                        <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(handleTripRegister)}>
                            <div className="flex flex-row justify-around space-x-8">
                                <div className="flex flex-grow flex-col">
                                    <div>
                                        <label
                                            htmlFor="Starting Location"
                                            className="block mb-3 text-sm font-medium text-zinc-300"
                                        >
                                            Pickup
                                        </label>
                                        <input
                                            type="startAddress"
                                            id="startAddress"
                                            placeholder="123 Main St"
                                            className="bg-zinc-900 border border-zinc-400 sm:text-sm rounded-lg focus:ring-zinc-400 focus:border-zinc-400 block w-full p-2.5"
                                            {...register("startAddress")}
                                        />
                                        {errors.startAddress ? <p className="text-red-500">{errors.startAddress.message}</p> : ""}
                                    </div>
                                    <div>
                                        <label
                                            htmlFor="startRegionId"
                                            className="block mb-2 text-sm font-medium text-gray-900"
                                        >
                                        </label>
                                        <select disabled={loadingRegions}
                                                id="startRegionId"
                                                className="block mb-2 w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body
                                        disabled:bg-zinc-800
                                        disabled:border-zinc-600 disabled:cursor-not-allowed"
                                                onChange={(selectedRegionId) => setRegionId(Number(selectedRegionId.target.value))}
                                        >
                                            <option value=""> </option>
                                            {regions?.map((region) => (
                                                <option key={region.regionId} value={region.regionId}>
                                                    {region.regionDisplayName}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                    <div>
                                        <select disabled={loadingSubRegions}
                                                id="startSubRegionId"
                                                className="block w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body
                                        disabled:bg-zinc-800
                                        disabled:border-zinc-600 disabled:cursor-not-allowed"
                                                {...register("startSubRegionId", { valueAsNumber: true })}
                                        >
                                            <option value=""> </option>
                                            {subRegions?.map((subRegion) => (
                                                <option key={subRegion.subRegionId} value={subRegion.subRegionId}>
                                                    {subRegion.subRegionDisplayName}
                                                </option>
                                            ))}
                                        </select>
                                        {errors.startSubRegionId ? <p className="text-red-500">{errors.startSubRegionId.message}</p> : ""}
                                    </div>
                                </div>
                                <div className="flex flex-grow flex-col">
                                    <div>
                                        <label
                                            htmlFor="Starting Location"
                                            className="block mb-3 text-sm font-medium text-zinc-300"
                                        >
                                            Dropoff
                                        </label>
                                        <input
                                            type="destAddress"
                                            id="destAddress"
                                            placeholder="123 Main St"
                                            className="bg-zinc-900 border border-zinc-400 sm:text-sm rounded-lg focus:ring-zinc-400 focus:border-zinc-400 block w-full p-2.5"
                                            {...register("destAddress")}
                                        />
                                        {errors.destAddress ? <p className="text-red-500">{errors.destAddress.message}</p> : ""}
                                    </div>
                                    <div>
                                        <label
                                            htmlFor="destRegionId"
                                            className="block mb-2 text-sm font-medium text-gray-900"
                                        >
                                        </label>
                                        <select disabled={loadingRegions}
                                                id="destRegionId"
                                                className="block mb-2 w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body
                                        disabled:bg-zinc-800
                                        disabled:border-zinc-600 disabled:cursor-not-allowed"
                                                onChange={(selectedRegionId) => setRegionId2(Number(selectedRegionId.target.value))}
                                        >
                                            <option value=""> </option>
                                            {regions?.map((region) => (
                                                <option key={region.regionId} value={region.regionId}>
                                                    {region.regionDisplayName}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                    <div>
                                        <select disabled={loadingSubRegions2}
                                                id="destSubRegionId"
                                                className="block w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body
                                        disabled:bg-zinc-800
                                        disabled:border-zinc-600 disabled:cursor-not-allowed"
                                                {...register("destSubRegionId", { valueAsNumber: true })}
                                        >
                                            <option value=""> </option>
                                            {subRegions2?.map((subRegion) => (
                                                <option key={subRegion.subRegionId} value={subRegion.subRegionId}>
                                                    {subRegion.subRegionDisplayName}
                                                </option>
                                            ))}
                                        </select>
                                        {errors.startSubRegionId ? <p className="text-red-500">{errors.startSubRegionId.message}</p> : ""}
                                    </div>
                                </div>
                            </div>
                            <button
                                type="submit"
                                className="w-full text-white font-bold text-l bg-purple-700 hover:opacity-70 focus:ring-primary-300 rounded-lg px-5 py-2.5 text-center"
                            >
                                Book
                            </button>
                        </form>
                    </div>
                </Popup>
            </div>
        </>
    )
}

export default TripPage;