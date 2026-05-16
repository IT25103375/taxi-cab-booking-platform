import * as Yup from "yup"
import { yupResolver } from "@hookform/resolvers/yup"
import { useAuth } from '../context/useAuth';
import { useForm } from 'react-hook-form';
import {useEffect} from "react";
import Navbar from "../components/Navbar.tsx";
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
            <section className="bg-gray-50">
                <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                    <div className="w-full bg-white rounded-lg shadow md:mb-20 sm:max-w-md xl:p-0">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            {!isLoggedIn() ? (<div>
                                <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                                    Sign in to your account
                                </h1>
                                <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(handleLogin)}>
                                    <div>
                                        <label
                                            htmlFor="email"
                                            className="block mb-2 text-sm font-medium text-gray-900"
                                        >
                                            Email
                                        </label>
                                        <input
                                            type="text"
                                            id="email"
                                            className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                            placeholder="example@gmail.com"
                                            {...register("email")}
                                        />
                                        {errors.email ? <p>{errors.email.message}</p> : ""}
                                    </div>
                                    <div>
                                        <label
                                            htmlFor="password"
                                            className="block mb-2 text-sm font-medium text-gray-900"
                                        >
                                            Password
                                        </label>
                                        <input
                                            type="password"
                                            id="password"
                                            placeholder="••••••••"
                                            className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                            {...register("password")}
                                        />
                                        {errors.password ? <p>{errors.password.message}</p> : ""}
                                    </div>
                                    <div className="flex items-center justify-between">
                                        <div className="flex items-start">
                                            <div className="flex items-center h-5">
                                                <input
                                                    id="remember"
                                                    aria-describedby="remember"
                                                    type="checkbox"
                                                    className="hover:ring-2 w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300"
                                                />
                                            </div>
                                            <div className="ml-3 text-sm">
                                                <label
                                                    htmlFor="remember"
                                                    className="text-gray-500 select-none"
                                                >
                                                    Remember me
                                                </label>
                                            </div>
                                        </div>
                                        <button
                                            className="text-sm font-medium text-pink-500 hover:underline"
                                        >
                                            Forgot password?
                                        </button>
                                    </div>
                                    <button
                                        type="submit"
                                        className="w-full text-white text-l bg-pink-300 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                                    >
                                        Sign in
                                    </button>
                                    <p className="text-sm font-light text-gray-500">
                                        Don’t have an account yet?{" "}
                                        {/*<Link*/}
                                        {/*    to="/register"*/}
                                        {/*    className="font-medium text-pink-500 hover:underline"*/}
                                        {/*>*/}
                                        {/*    Sign up*/}
                                        {/*</Link>*/}
                                    </p>
                                </form>
                            </div>) : (
                                <div>
                                    <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900">
                                        Logged in as {user?.username}
                                    </h1>
                                    <button
                                        onClick={logout}
                                        className="w-full text-white text-l bg-pink-300 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
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