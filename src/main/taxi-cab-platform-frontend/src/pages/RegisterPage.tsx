import * as Yup from "yup"
import { yupResolver } from "@hookform/resolvers/yup"
import { useAuth } from '../context/useAuth';
import { useForm } from 'react-hook-form';
import { UserType, ValidUserTypes} from '../enums/UserType.ts'
import { useEffect } from "react";
import { useRegion } from '../context/useRegion';
//import { Link } from 'react-router-dom';

type Props = {}

type RegisterFormsInput = {
    username: string;
    email: string;
    password: string;

    userType: UserType;

    regionId: number | null;
    subRegionId: number | null;
}

const validation = Yup.object().shape({
    username: Yup.string().required("Username is required"),
    email: Yup.string().required("Email is required"),
    password: Yup.string().required("Password is required"),
    userType: Yup.string().oneOf(Object.values(UserType), "User type is invalid")
        .required("User type is required"),

    regionId: Yup.number()
        .transform((value, originalValue) => String(originalValue).trim() === "" ? null : value)
        .required("Region is required")
        .nullable() as Yup.Schema<number | null>,

    subRegionId: Yup.number()
        .transform((value, originalValue) => String(originalValue).trim() === "" ? null : value)
        .required("Sub Region is required")
        .nullable() as Yup.Schema<number | null>,
});

const RegisterPage = (props: Props) => {
    const {registerUser} = useAuth();
    const { register, handleSubmit, setValue, watch, formState: {errors}} = useForm<RegisterFormsInput>({ resolver: yupResolver(validation)})

    const handleLogin = (form: RegisterFormsInput) => {
        registerUser(form.username, form.email, form.password, form.userType, form.regionId, form.subRegionId)
    }

    const { regions, subRegions, loadingRegions, loadingSubRegions, setRegionId } = useRegion();

    useEffect(() => {
        setRegionId(watch("regionId"));
    }, [watch("regionId"), setRegionId]);

    const selectedUserType = watch("userType");
    useEffect(() => {
        if (selectedUserType !== UserType.Driver) {
            setValue("regionId", null);
            setValue("subRegionId", null);
            setRegionId(null);
        }
    }, [selectedUserType, setValue, setRegionId]);

    return (
        <section className="bg-gray-50">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                <div className="w-full bg-white rounded-lg shadow md:mb-20 sm:max-w-md xl:p-0">
                    <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                        <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                            Create an account
                        </h1>
                        <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(handleLogin)}>
                            <div>
                                <label
                                    htmlFor="username"
                                    className="block mb-2 text-sm font-medium text-gray-900"
                                >
                                    Username
                                </label>
                                <input
                                    type="text"
                                    id="username"
                                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                                    placeholder="Enter Username"
                                    {...register("username")}
                                />
                                {errors.username ? <p className="text-red-500">{errors.username.message}</p> : ""}
                            </div>
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
                                {errors.email ? <p className="text-red-500">{errors.email.message}</p> : ""}
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
                                {errors.password ? <p className="text-red-500">{errors.password.message}</p> : ""}
                            </div>
                            <div>
                                <label
                                    htmlFor="userType"
                                    className="block mb-2 text-sm font-medium text-gray-900"
                                >
                                    I am a...
                                </label>
                                <select
                                    id="userType"
                                    className="block w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body"
                                    {...register("userType")}
                                >
                                    <option value=""> </option>
                                    {Object.values(ValidUserTypes).map((role) => (
                                        <option key={role} value={role}>
                                            {role.charAt(0) + role.slice(1).toLowerCase()}
                                        </option>
                                    ))}
                                </select>
                                {errors.userType ? <p className="text-red-500">{errors.userType.message}</p> : ""}
                            </div>
                            {watch("userType") == UserType.Driver && (
                                <div>
                                    <div>
                                        <label
                                            htmlFor="regionId"
                                            className="block mb-2 text-sm font-medium text-gray-900"
                                        >
                                            Region
                                        </label>
                                        <select disabled={loadingRegions || watch("userType") != UserType.Driver}
                                                id="regionId"
                                                className="block w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body
                                        disabled:bg-gray-100 disabled:text-gray-400
                                        disabled:border-gray-200 disabled:cursor-not-allowed
                                        disabled:opacity-100"
                                                {...register("regionId", { valueAsNumber: true })}
                                        >
                                            <option value=""> </option>
                                            {regions?.map((region) => (
                                                <option key={region.regionId} value={region.regionId}>
                                                    {region.regionDisplayName}
                                                </option>
                                            ))}
                                        </select>
                                        {errors.regionId ? <p className="text-red-500">{errors.regionId.message}</p> : ""}
                                    </div>
                                    <div>
                                        <label
                                            htmlFor="subRegionId"
                                            className="block mb-2 text-sm font-medium text-gray-900"
                                        >
                                            Sub Region
                                        </label>
                                        <select disabled={loadingSubRegions || watch("userType") != UserType.Driver}
                                                id="subRegionId"
                                                className="block w-full px-3 py-2.5 bg-neutral-secondary-medium border border-default-medium text-heading text-sm rounded-base focus:ring-brand focus:border-brand shadow-xs placeholder:text-body
                                        disabled:bg-gray-100 disabled:text-gray-400
                                        disabled:border-gray-200 disabled:cursor-not-allowed
                                        disabled:opacity-100"
                                                {...register("subRegionId", { valueAsNumber: true })}
                                        >
                                            <option value=""> </option>
                                            {subRegions?.map((subRegion) => (
                                                <option key={subRegion.subRegionId} value={subRegion.subRegionId}>
                                                    {subRegion.subRegionDisplayName}
                                                </option>
                                            ))}
                                        </select>
                                        {errors.subRegionId ? <p className="text-red-500">{errors.subRegionId.message}</p> : ""}
                                    </div>
                                </div>
                            )}
                            {/*<div className="flex items-center justify-between">*/}
                            {/*    <div className="flex items-start">*/}
                            {/*        <div className="flex items-center h-5">*/}
                            {/*            <input*/}
                            {/*                id="remember"*/}
                            {/*                aria-describedby="remember"*/}
                            {/*                type="checkbox"*/}
                            {/*                className="hover:ring-2 w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300"*/}
                            {/*            />*/}
                            {/*        </div>*/}
                            {/*        <div className="ml-3 text-sm">*/}
                            {/*            <label*/}
                            {/*                htmlFor="remember"*/}
                            {/*                className="text-gray-500 select-none"*/}
                            {/*            >*/}
                            {/*                Remember me*/}
                            {/*            </label>*/}
                            {/*        </div>*/}
                            {/*    </div>*/}
                            {/*</div>*/}
                            <button
                                type="submit"
                                className="w-full text-white text-l bg-pink-300 hover:opacity-70 focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                            >
                                Register
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default RegisterPage