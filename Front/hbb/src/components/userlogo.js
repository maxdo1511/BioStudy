'use client'
import Image from "next/image";
import "../css/components/userlogo.css"
import "../css/standart/default.css"
import {useEffect, useState} from "react";
import config from "@/app/properties";

const UserLogo = () => {
    const [image, setImage] = useState("")
    useEffect(() => {
        fetch(config.user_icon, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
        }).then(r => {
                const bytes = r.arrayBuffer()
                console.log(bytes)
                const base64Image = btoa(
                    new Uint8Array(bytes)
                        .reduce((data, byte) => data + String.fromCharCode(byte), '')
                );
                const img = `data:image/jpeg;base64,${base64Image}`
                console.log(img)
                setImage(img);
            }
        )
    }, [])
    return (
        <div>
            <a className="user_logo" href={"/"}>
                <Image
                    className="user_image"
                    src={image}
                    alt="profil icon"
                    width={40}
                    height={40}
                />
                Profile
            </a>
        </div>
    )
}

export default UserLogo;