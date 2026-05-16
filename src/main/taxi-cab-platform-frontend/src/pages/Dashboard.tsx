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
import {tripFuncButton} from "../helpers/TripHelper.tsx";
import {useAuth} from "../context/useAuth.tsx";
import Navbar from "../components/Navbar.tsx";

const Dashboard = () => {

    type FareFormsInput = {
        distanceKM : number;
    }

    const validation = Yup.object().shape({
        distanceKM: Yup.number().required("Enter distance travelled")
    });

    const { user } = useAuth()
    const { driverTrips, setFetchDriverTrip, fetchDriverTrip, acceptTrip, pickupTrip, finishTrip, cancelTrip } = useTrip();
    const [ selectedTrip, setSelectedTrip ] = useState<TripGet | null>(null);
    const [ selectedIndex, setSelectedIndex ] = useState<number>(-1);
    const [ fareWindowOpen, setFareWindowOpen ] = useState(false);
    const navigate = useNavigate();

    const { register, handleSubmit, formState: {errors}} = useForm<FareFormsInput>({ resolver: yupResolver(validation)})
    const handleTripFinish = (form: FareFormsInput) => {
        if (!selectedTrip) return;

        finishTrip(selectedTrip.id, form.distanceKM);
        setFareWindowOpen(false);
    }

    useEffect(() => {
        if (driverTrips && selectedIndex != -1) {
            setSelectedTrip(driverTrips[selectedIndex]);
        }
        else setSelectedTrip(null);
    }, [driverTrips]);

    return (
        <>
            <Navbar/>
            <div className="flex h-screen gap-4 p-4">
                <aside className="w-80 shrink-0 rounded-xl border bg-zinc-900 p-4 flex flex-col">
                    <h2 className="mb-4 text-xl font-bold">Available Trips</h2>

                    <div className="flex-1 overflow-y-auto space-y-3">
                        {driverTrips != null && driverTrips.length > 0 ? (
                            <div>
                                {driverTrips.map((trip, index) => (
                                    <button
                                        key={index}
                                        onClick={() => {setSelectedTrip(trip); setSelectedIndex(index);}}
                                        className="w-full rounded-lg border p-4 text-left hover:bg-gray-50"
                                    >
                                        <div className="font-semibold">
                                            {trip.startSubRegion} → {trip.destSubRegion}
                                        </div>
                                        <div className="text-sm text-gray-500">
                                            {trip.tripStatus}
                                        </div>
                                        <div className="text-sm font-medium">
                                            {trip.driverName ?? 'Unassigned'}
                                        </div>
                                    </button>
                                ))}
                            </div>) : (
                            <div className="flex flex-col space-y-4 h-full">
                                <p className="text-zinc-400">
                                    No trips available.
                                </p>
                                <button
                                    type="button"
                                    onClick = {() => setFetchDriverTrip(fetchDriverTrip + 1)}
                                    className="w-full rounded-lg bg-green-600 px-5 py-3 font-medium text-white hover:bg-green-700 active:bg-green-800"
                                >
                                    Refresh
                                </button>
                            </div>
                        )}
                    </div>
                </aside>

                {/* Center Panel: Sri Lanka Map Placeholder */}
                <section className="flex-1 rounded-xl border bg-zinc-900 p-4 flex items-center justify-center">
                    <div className="w-full h-full rounded-lg border-2 border-dashed border-gray-300 flex items-center justify-center">
                        <div className="text-center text-gray-500">
                            <h2 className="text-2xl font-bold mb-2">Sri Lanka Map</h2>
                            <p>Map placeholder for trip markers</p>
                        </div>
                    </div>
                </section>

                {/* Right Sidebar: Selected Trip Details */}
                <aside className="w-96 shrink-0 rounded-xl border bg-zinc-900 p-6 overflow-y-auto">
                    {selectedTrip ? (
                        <div className="space-y-4">
                            <h2 className="text-2xl font-bold">Trip Details</h2>

                            <div className="space-y-2">
                                <p>
                                    <strong>Pickup:</strong>{' '}
                                    {selectedTrip.startAddress}, {selectedTrip.startSubRegion},{' '}
                                    {selectedTrip.startRegion}
                                </p>

                                <p>
                                    <strong>Dropoff:</strong>{' '}
                                    {selectedTrip.destAddress}, {selectedTrip.destSubRegion},{' '}
                                    {selectedTrip.destRegion}
                                </p>

                                <p>
                                    <strong>Driver:</strong>{' '}
                                    {selectedTrip.driverName ?? 'Not assigned'}
                                </p>

                                <p>
                                    <strong>Status:</strong>{' '}
                                    {selectedTrip.tripStatus.charAt(0) +
                                        selectedTrip.tripStatus.slice(1).toLowerCase()}
                                </p>

                                <p>
                                    <strong>Date:</strong> FIXME-DATE
                                </p>

                                <p>
                                    <strong>Fare:</strong> FIXME-FARE
                                </p>
                            </div>
                            {tripFuncButton("AcceptTrip", selectedTrip, user) && (
                                <button
                                    type="button"
                                    onClick = {() => acceptTrip(selectedTrip.id)}
                                    className="w-full rounded-lg bg-green-600 px-5 py-3 font-medium text-white hover:bg-green-700 active:bg-green-800"
                                >
                                    Accept Trip
                                </button>
                            )}
                            {tripFuncButton("PickupTrip", selectedTrip, user) && (
                                <button
                                    type="button"
                                    onClick = {() => pickupTrip(selectedTrip.id)}
                                    className="w-full rounded-lg bg-green-600 px-5 py-3 font-medium text-white hover:bg-green-700 active:bg-green-800"
                                >
                                    Confirm Pickup
                                </button>
                            )}
                            {tripFuncButton("FinishTrip", selectedTrip, user) && (
                                <button
                                    type="button"
                                    onClick = {() => setFareWindowOpen(true)}
                                    className="w-full rounded-lg bg-green-600 px-5 py-3 font-medium text-white hover:bg-green-700 active:bg-green-800"
                                >
                                    Finish Trip
                                </button>
                            )}
                            {tripFuncButton("CancelTrip", selectedTrip, user) && (
                                <button
                                    type="button"
                                    onClick = {() => cancelTrip(selectedTrip.id)}
                                    className="w-full rounded-lg bg-green-600 px-5 py-3 font-medium text-white hover:bg-green-700 active:bg-green-800"
                                >
                                    Cancel Trip
                                </button>
                            )}
                        </div>
                    ) : (
                        <p className="text-gray-500">
                            Select an available trip to view details.
                        </p>
                    )}
                    <Popup
                        isOpen={fareWindowOpen}
                        size="sm"
                        onClose={() => setFareWindowOpen(false)}
                    >
                        <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(handleTripFinish)}>
                            <div className="bg-zinc-900 p-6 space-y-4 md:space-y-6 sm:p-8">
                                <h1 className="text-xs font-bold leading-tight tracking-tight text-gray-900">
                                    Enter total distance in KM
                                </h1>
                                <div>
                                    <label
                                        htmlFor="Distance"
                                        className="block mb-3 text-sm font-medium text-zinc-300"
                                    >
                                        Distance:
                                    </label>
                                    <div className="flex flex-row mb-4 items-center space-x-2">
                                        <input
                                            placeholder="123 Main St"
                                            className="bg-zinc-900 border border-zinc-400 sm:text-sm rounded-lg focus:ring-zinc-400 focus:border-zinc-400 w-full block p-2.5"
                                            {...register("distanceKM")}
                                        />
                                        <p> KM </p>
                                    </div>
                                    <button
                                        type="submit"
                                        className="w-full text-black text-l bg-pink-300 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg px-5 py-2.5 text-center"
                                    >
                                        Finish trip
                                    </button>
                                </div>
                            </div>
                        </form>
                    </Popup>
                </aside>
            </div>
        </>
    )
}

export default Dashboard;