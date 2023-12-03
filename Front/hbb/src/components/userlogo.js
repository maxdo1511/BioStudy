'use client'
import Image from "next/image";
import "../css/components/userlogo.css"
import "../css/standart/default.css"
import {useEffect, useState} from "react";
import config from "@/app/properties";

const UserLogo = () => {
    const [userName, name] = useState("")
    useEffect(() => {
        fetch(config.user_data, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
        }).then(r => {

            }
        )
    }, [])
    return (
        <div>
            <a className="user_logo" href={"/dashboard/profile"}>
                <div className="user_image__container">
                    <Image
                        className="user_image"
                        src={config.user_icon + userName}
                        alt="profil icon"
                        width={40}
                        height={40}
                    />
                </div>
                Profile
            </a>
        </div>
    )
}

export default UserLogo;