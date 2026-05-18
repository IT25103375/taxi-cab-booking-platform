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
import {UserType} from "../enums/UserType.ts";
import type {VehicleGet} from "../models/Vehicle.ts";

const VehiclePage = () => {

    type VehicleFormsInput = {
        displayName: string;
        plateNumber: string;
        vehicleTypeId: number;
    }

    const validation = Yup.object().shape({
        displayName: Yup.string().required("Display name is required"),
        plateNumber: Yup.string().required("Plate Number is required"),
        vehicleTypeId: Yup.number().required("VehicleTypeId is required")
    });

    const navigate = useNavigate();
    const { user } = useAuth();
    const { createVehicle, vehicles, removeVehicle, currentVehicle, vehicleTypes, fetchedCurrentVehicle,
        setFetchCurrentVehicle, fetchCurrentVehicle} = useVehicle();
    const [ selectedVehicle, setSelectedVehicle ] = useState<VehicleGet | null>(null);
    const [ vehicleWindowOpen, setVehicleWindowOpen ] = useState(false);

    const { register, handleSubmit, setValue, watch, reset, formState: {errors}} = useForm<VehicleFormsInput>({ resolver: yupResolver(validation)})
    const handleTripRegister = (form: VehicleFormsInput) => {
        createVehicle(form.displayName, form.plateNumber, form.vehicleTypeId);
        reset();
        setVehicleWindowOpen(false);
    }

    return (
        <>
            <Navbar/>
            <div className="flex flex-col h-screen gap-4 p-4">
                <section className="overflow-x-auto space-y-3 rounded-xl border p-3">
                    <div className="flex space-x-1 overflow-x-auto">
                        {(vehicleTypes) && vehicles?.map((vehicle) => {
                            const vehicleTypeName = vehicleTypes?.find(item => item.id === vehicle?.vehicleTypeId)?.name;
                            const IconComponent = vehicleTypeName ? iconMap[vehicleTypeName] : null;

                            return (
                                <button
                                    key={vehicle.id}
                                    onClick={() => setSelectedVehicle(vehicle)}
                                    className={"shrink-0 w-35 rounded-lg border p-4 text-left"
                                        + ((vehicle?.id == fetchedCurrentVehicle?.id) ? " bg-blue-700 border-zinc-400 text-zinc-200" :
                                            ((selectedVehicle?.id == vehicle.id) ? " bg-purple-950 border-zinc-400 text-zinc-200" : " hover:bg-zinc-700"))}
                                >
                                    <div className="flex flex-col justify-center items-center">
                                        {IconComponent ? <IconComponent size="5em"/> : null}
                                        <p className="text-center"> {vehicle.displayName}</p>
                                    </div>
                                </button>
                            );
                        })}
                    </div>
                </section>
                <section className="flex-1 rounded-xl border p-6">
                    <div className="flex flex-row space-x-4">
                        <div className="flex justify-start">
                            <button
                                onClick={() => setVehicleWindowOpen(true)}
                                disabled={user?.role != UserType.Driver}
                                className="text-white text-l border bg-purple-900 border-purple-800 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center hover:bg-purple-950
                                disabled:bg-purple-950 disabled:text-gray-400
                                disabled:border-purple-800 disabled:cursor-not-allowed
                                disabled:opacity-100"
                            >
                                Add Vehicle
                            </button>
                        </div>
                        <div className="flex justify-end">
                            <button
                                onClick={() => {if (selectedVehicle) currentVehicle(selectedVehicle.id)}}
                                disabled={(user?.role != UserType.Driver) || (!selectedVehicle)}
                                className="text-white text-l border bg-purple-900 border-purple-800 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center hover:bg-purple-950
                                disabled:bg-purple-950 disabled:text-gray-400
                                        disabled:border-purple-800 disabled:cursor-not-allowed
                                        disabled:opacity-100"
                            >
                                Set as current Vehicle
                            </button>
                        </div>
                        <div className="ml-auto">
                            <button
                                onClick={() => {if (selectedVehicle) removeVehicle(selectedVehicle.id)}}
                                disabled={(user?.role != UserType.Driver) || (!selectedVehicle)}
                                className="text-white text-l border bg-purple-900 border-purple-800 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center hover:bg-purple-950
                                disabled:bg-purple-950 disabled:text-gray-400
                                        disabled:border-purple-800 disabled:cursor-not-allowed
                                        disabled:opacity-100"
                            >
                                Remove Vehicle
                            </button>
                        </div>
                    </div>
                    <hr className="w-full border-b-4 border-white-1000 my-4"></hr>
                    {selectedVehicle ? (
                        <div className="space-y-4">
                            <h2 className="text-2xl font-bold">Trip Details</h2>
                            <div>
                                <p><strong>Name:</strong> {(selectedVehicle.displayName) ? selectedVehicle.displayName : "Not assigned"}</p>
                                <p><strong>Vehicle Type:</strong> {(selectedVehicle.vehicleTypeId) ? vehicleTypes?.find(item => item.id === selectedVehicle.vehicleTypeId)?.name : "Not assigned"}</p>
                                <p><strong>Plate Number:</strong> {(selectedVehicle.plateNumber) ? selectedVehicle.plateNumber : "Not assigned"}</p>
                            </div>
                        </div>
                    ) : (
                        <p className="text-gray-500">Select a vehicle to view details.</p>
                    )}
                </section>
                <Popup
                    isOpen={vehicleWindowOpen}
                    size="md"
                    onClose={() => setVehicleWindowOpen(false)}
                >
                    <div className="bg-zinc-900 p-6 space-y-4 md:space-y-6 sm:p-8">
                        <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                            Add a vehicle
                        </h1>
                        {errors.vehicleTypeId ? <p className="text-red-500">{errors.vehicleTypeId.message}</p> : ""}
                        <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(handleTripRegister)}>
                            <div className="flex justify-around">
                                <div className="flex flex-col">
                                    <div className="mb-4">
                                        <label
                                            htmlFor="Display Name"
                                            className="block mb-3 text-sm font-medium text-zinc-300"
                                        >
                                            Name
                                        </label>
                                        <input
                                            type="displayName"
                                            id="displayName"
                                            placeholder="My Car"
                                            className="bg-zinc-900 border border-zinc-400 sm:text-sm rounded-lg focus:ring-zinc-400 focus:border-zinc-400 block w-full p-2.5"
                                            {...register("displayName")}
                                        />
                                        {errors.displayName ? <p className="text-red-500">{errors.displayName.message}</p> : ""}
                                    </div>
                                    <div className="mb-6">
                                        <label
                                            htmlFor="Plate Number"
                                            className="block mb-3 text-sm font-medium text-zinc-300"
                                        >
                                            Plate Number
                                        </label>
                                        <input
                                            type="plateNumber"
                                            id="plateNumber"
                                            placeholder="AA0000"
                                            className="bg-zinc-900 border border-zinc-400 sm:text-sm rounded-lg focus:ring-zinc-400 focus:border-zinc-400 block w-full p-2.5"
                                            {...register("plateNumber")}
                                        />
                                        {errors.plateNumber ? <p className="text-red-500">{errors.plateNumber.message}</p> : ""}
                                    </div>
                                    <div className="flex space-x-1 max-w-80 overflow-scroll">
                                        {vehicleTypes?.map((vehicleType) => {
                                            const IconComponent = iconMap[vehicleType.name];

                                            return (
                                                <button
                                                    key={vehicleType.id}
                                                    type="button"
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
                                </div>
                            </div>
                            <button
                                type="submit"
                                className="w-full text-white font-bold text-l bg-purple-700 hover:opacity-70 focus:ring-primary-300 rounded-lg px-5 py-2.5 text-center"
                            >
                                Add vehicle
                            </button>
                        </form>
                    </div>
                </Popup>
            </div>
        </>
    )
}

export default VehiclePage;