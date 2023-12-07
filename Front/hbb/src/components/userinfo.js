'use client'
import '../css/components/userinfo.css'
import Image from "next/image";
import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import config from "@/app/config";
import {handleImageUpload} from "@/utils/image_module";

export default function UserInfo() {
    const router = useRouter()
    const [info, setInfo] = useState(null)
    useEffect(() => {
        fetch(config.user_data_from_token, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
        }).then(async r => {
                const data = await r.json();
                setInfo(data)
            }
        ).catch(e => {
            if (e.toString().includes("UNAUTHORIZED")) {
                if (localStorage.getItem("token").length > 0) {
                    localStorage.removeItem("token")
                    router.push("/signin")
                }
            }
        })
    }, [])
    return (
        <div className={"profile__userinfo__background shadow-lg rounded-lg"}>
            {
                info != null &&
                <div className={"profile__userinfo__container"}>
                    <div className={"profile__icon__container"}>
                        {info.name !== "" &&
                            <Image
                                className="profile__icon"
                                src={config.user_icon + info.username}
                                alt="profil icon"
                                sizes="100%"
                                fill
                                style={{
                                    objectFit: 'cover',
                                }}
                            />
                        }
                        <input className={"profile__icon__loader"} type="file" accept="image/*" onChange={handleImageUpload} />
                    </div>
                    <div className={"profile__info"}>
                        <h1 className={"text-2xl text-base"}>
                            {info.username}
                        </h1>
                        <h2 className={"profile__external__text text-1xl"}>
                            {info.firstName + "   "}
                            {info.secondName}
                        </h2>
                        <br/>
                        <h2 className={"profile__external__text text-1xl"}>
                            {info.description}
                        </h2>
                        <br/>
                        <h2 className={"profile__external__text text-1xl"}>
                            email: {info.email}
                        </h2>
                    </div>
                    <div className={"vertical__line"} />
                    <div className={"profile__info__2"}>
                        <h2 className={"profile__external__text__2 text-1xl"}>
                            {"Тел: " + info.phone}
                        </h2>
                        <br/>
                        <h2 className={"profile__external__text__2 text-1xl"}>
                            Последний раз заходил: {info.lastAuth}
                        </h2>
                        <br/>
                        <h2 className={"profile__external__text__2 text-1xl"}>
                            Дата регистрации: {info.registerDate}
                        </h2>
                    </div>
                </div>
            }
        </div>
    )
}