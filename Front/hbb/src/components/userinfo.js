'use client'
import '../css/components/userinfo.css'
import Image from "next/image";
import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import config from "@/app/config";
import {handleImageUpload} from "@/utils/image_module";
import icon from '../../public/pushkin.jpg'
import {OutsideAlerter} from "@/hooks/click-outsite-hook";
import PopUpAddPerson from "@/components/pop-up-add-person";
import CustomError from "@/components/error";

export default function UserInfo() {
    const router = useRouter()
    const [info, setInfo] = useState(null)
    const [open, SetOpen] = useState(false);
    const [field, SetField] = useState("");
    const [fieldRu, SetFieldRu] = useState("");
    const [fieldVal, SetFieldVal] = useState(null);
    const [error, SetError] = useState(null);

    useEffect(() => {
        fetch(config.user_data_from_token, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            next: {relative: 0},
            cache: 'no-store'
        }).then(async r => {
                const data = await r.json();
                console.log(data)
                setInfo(data)
            }
        ).catch(e => {
            if (e.toString().includes("UNAUTHORIZED")) {
                if (localStorage.getItem("token") != null) {
                    if (localStorage.getItem("token").length > 0) {
                        localStorage.removeItem("token")
                    }
                }
                router.push("/signin")
            }
        })
    }, [])
    return (
        <div className={"static w-full"}>
            <div className={"profile__userinfo__background shadow-lg rounded-lg"}>
                {
                    info != null &&
                    <div className={"profile__userinfo__container"}>
                        <div className={"profile__icon__container"}>
                            {info.name !== "" ?
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
                                :
                                <Image
                                    className="profile__icon"
                                    src={icon}
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
                                {info.username == null}
                            </h1>
                            <h2 className={"profile__external__text text-1xl"} onClick={() => {
                                SetOpen(true);
                                SetField("firstname secondname");
                                SetFieldRu("имя");
                                SetFieldVal({
                                    "1": info.firstName,
                                    "2": info.secondName
                                })
                            }
                            }>
                                {info.firstName == null ? "У самурая нет имени" : info.firstName}
                                {" "}
                                {info.secondName != null && info.secondName}
                            </h2>
                            <br/>
                            <h2 className={"profile__external__text text-1xl"} onClick={() => {
                                SetOpen(true);
                                SetField("description");
                                SetFieldRu("информацию о себе");
                                SetFieldVal({
                                    "1": info.description
                                })
                            }
                            }>
                                {info.description == null ? info.username + " пока не хочет о себе рассказывать" : info.description}
                            </h2>
                            <br/>
                            <h2 className={"profile__external__text text-1xl"}>
                                email: {info.email}
                            </h2>
                        </div>
                        <div className={"line"} />
                        <div className={"profile__info__2"}>
                            <h2 className={"profile__external__text__2 text-1xl"} onClick={() => {
                                SetOpen(true);
                                SetField("phone");
                                SetFieldRu("номер телефона");
                                SetFieldVal({
                                    "1": info.phone == null ? "9777777777" : info.phone
                                })
                            }
                            }>
                                Тел: {info.phone == null ? "походу нет" : "+7" + info.phone}
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
            {
                info != null &&
                <OutsideAlerter onOutside={() => {
                    if (open) {
                        SetOpen(false);
                        SetField("");
                        SetFieldRu("");
                        SetFieldVal(null);
                    }
                }}>
                    {
                        open && <PopUpAddPerson field={field} localized_field={fieldRu} fieldVal={fieldVal} state={SetOpen} obj={info} error={SetError}/>
                    }
                </OutsideAlerter>
            }
            {
                error != null && <CustomError error={error} state={SetError} />
            }
        </div>
    )
}