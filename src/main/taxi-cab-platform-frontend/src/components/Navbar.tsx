import React from "react";
import taxiicon from "../assets/taxiIcon.svg";
import { Link } from "react-router-dom";
import { useAuth } from "../context/useAuth";
import {UserType} from "../enums/UserType.ts";

interface Props {}

const Navbar = (props: Props) => {
  const {isLoggedIn, user, logout } = useAuth();

  return (
      <nav className="p-5 shadow-md w-auto bg-zinc-900 border-b border-zinc-600">
        <div className="flex items-center justify-between">
          <div className="flex items-center hover:shadow-md hover:outline-1 hover:outline-blue-200">
            <Link to="/dashboard">
              <img src={taxiicon} className="size-20" alt="" />
            </Link>
          </div>
          <div className="flex space-x-5 justify-start flex-grow ml-8 items-left">
            {(user?.role == UserType.Driver) && (
                <div className="space-x-5">
                  <Link
                      to="/dashboard"
                      reloadDocument
                      className="font-bold rounded text-white hover:opacity-70"
                  >
                    Dashboard
                  </Link>
                  <Link
                      to="/vehicles"
                      reloadDocument
                      className="font-bold rounded text-white hover:opacity-70"
                  >
                    Vehicles
                  </Link>
                </div>
            )}
            <Link
                to="/trip"
                reloadDocument
                className="font-bold rounded text-white hover:opacity-70"
            >
              Trips
            </Link>
          </div>
          {isLoggedIn() ?  (
              <div className="flex flex-col space-y-2 items-center text-back">
                <a
                    onClick={logout}
                    className="select-none px-8 py-3 font-bold rounded text-white bg-purple-700 hover:opacity-70"
                >
                  Logout
                </a>
                <div className="text-purple-300">Logged in as: {user?.username}</div>
              </div>
          ) : (
              <div className="flex items-center space-x-6 text-back">
                <Link to="/login" className="hover:text-darkBlue">Login</Link>
                <Link
                    to="/register"
                    reloadDocument
                    className="px-8 py-3 font-bold rounded text-white bg-purple-800 hover:opacity-70"
                >
                  Signup
                </Link>
              </div>
          )}

        </div>
      </nav>
  );
};

export default Navbar;