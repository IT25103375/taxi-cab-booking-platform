import * as Yup from "yup"
import { yupResolver } from "@hookform/resolvers/yup"
import { useAuth } from '../context/useAuth';
import { useForm } from 'react-hook-form';
import {useEffect} from "react";
import Navbar from "../components/Navbar.tsx";
import {Link} from "react-router-dom";
//import { Link } from 'react-router-dom';

type Props = {}

type LoginFormsInputs = {
    email: string;
    password: string;
}

const validation = Yup.object().shape({
    email: Yup.string().required("Email is required"),
    password: Yup.string().required("Password is required"),
})

const LoginPage = (props: Props) => {
    const {loginUser, isLoggedIn, logout, user} = useAuth();
    const { register, handleSubmit , formState: {errors}} = useForm<LoginFormsInputs>({ resolver: yupResolver(validation)})

    const handleLogin = (form: LoginFormsInputs) => {
        loginUser(form.email, form.password)
    }

    useEffect(() => {}, [])

    return (
        <>
            <Navbar/>
            <section className="bg-zinc-900">
                <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                    <div className="w-full bg-zinc-800 rounded-lg shadow md:mb-20 sm:max-w-md xl:p-0">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            {!isLoggedIn() ? (<div>
                                <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                                    Sign in to your account
                                </h1>
                                <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(handleLogin)}>
                                    <div>
                                        <label
                                            htmlFor="email"
                                            className="block mb-2 text-md font-medium text-white"
                                        >
                                            Email
                                        </label>
                                        <input
                                            type="text"
                                            id="email"
                                            autoComplete="email"
                                            className="bg-zinc-900 border border-zinc-600 text-zinc-400 sm:text-sm rounded-lg focus:ring-purple-900 focus:border-purple-900 block w-full p-2.5"
                                            placeholder="example@gmail.com"
                                            {...register("email")}
                                        />
                                        {errors.email ? <p className="text-red-500">{errors.email.message}</p> : ""}
                                    </div>
                                    <div>
                                        <label
                                            htmlFor="password"
                                            className="block mb-2 text-md font-medium text-white"
                                        >
                                            Password
                                        </label>
                                        <input
                                            type="password"
                                            id="password"
                                            autoComplete="current-password"
                                            placeholder="••••••••"
                                            className="bg-zinc-900 border border-zinc-600 text-zinc-400 sm:text-sm rounded-lg focus:ring-purple-900 focus:border-purple-900 block w-full p-2.5"
                                            {...register("password")}
                                        />
                                        {errors.password ? <p className="text-red-500">{errors.password.message}</p> : ""}
                                    </div>
                                    <button
                                        type="submit"
                                        className="w-full text-white text-l bg-purple-800 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                                    >
                                        Sign in
                                    </button>
                                    <p className="text-sm font-light text-gray-500">
                                        Don’t have an account yet?{" "}
                                        <Link
                                            to="/register"
                                            className="font-medium text-purple-600 hover:underline"
                                        >
                                            Sign up
                                        </Link>
                                    </p>
                                </form>
                            </div>) : (
                                <div>
                                    <h1 className="text-xl font-bold leading-tight tracking-tight text-zinc-700">
                                        Logged in as {user?.username}
                                    </h1>
                                    <button
                                        onClick={logout}
                                        className="w-full text-white text-l bg-purple-700 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                                    >
                                        Logout
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default LoginPage