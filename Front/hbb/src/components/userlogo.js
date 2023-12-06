'use client'
import Image from "next/image";
import "../css/components/userlogo.css"
import "../css/standart/default.css"
import {useEffect, useState} from "react";
import config from "@/app/config";
import {useRouter} from "next/navigation";


const UserLogo = () => {
    const router = useRouter()
    const [userName, setName] = useState("")
    useEffect(() => {
        fetch(config.user_data_from_token, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
        }).then(async r => {
                const data = await r.json();
                setName(data.username)
            }
        ).catch(e => {
            if (e.toString().includes("UNAUTHORIZED")) {
                localStorage.removeItem("token")
                router.push("/signin")
            }
        })
    }, [])
    return (
        <div>
            {
                userName != null &&
                <a className="user_logo" href={"/profile"}>
                    <div className="user_image__container">
                        {userName !== "" &&
                            <Image
                                className="user_image"
                                src={config.user_icon + userName}
                                alt="profil icon"
                                sizes="40px"
                                fill
                                style={{
                                    objectFit: 'cover',
                                }}
                            />
                        }
                    </div>
                    {userName}
                </a>
            }
        </div>
    )
}

export default UserLogo;