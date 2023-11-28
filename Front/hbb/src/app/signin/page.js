'use client'

import './singin.css'
import '../../css/standart/default.css'
import google from "public/icons/google.svg";
import vk from "public/icons/vk.svg";
import Image from "next/image";
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import config from "@/app/properties";

export default function SignIn() {

    const [name, setName] = useState('')
    const [password, setPassword] = useState('')

    const router = useRouter();

    useEffect(() => {
        if (localStorage.getItem("token") != null) {
            router.push('/')
        }
    })

    async function handle() {
        const user = {
            "username": name,
            "password": password
        }
        const res = await fetch(config.signin, {
                method: "POST",
                body: JSON.stringify(user),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }
        )
        if (res.ok) {
            const json = await res.text()
            await localStorage.setItem("token", json)
        }
    }

    return (
        <div>
            <div className="background">
                <div className="shape"></div>
                <div className="shape"></div>
            </div>
            <form onSubmit={async () => { await handle()}}>
                <h3>Войдите</h3>

                <label htmlFor="username">Логин {name} {password}</label>
                <input type="text" placeholder="SoLEk" id="username" onChange={event => setName(event.target.value)}/>

                <label htmlFor="password">Пароль</label>
                <input type="password" placeholder="123" id="password" onChange={event => setPassword(event.target.value)}/>

                <button className="animated__up__button" type="submit">Войти</button>

                <div className="social">
                    <div className="buttons">
                        <a href={"/"} className="fab google">
                            <Image
                                src={google}
                                alt="google icon"
                                width={20}
                                height={20}
                            />
                            <div className="google__title">
                                Google
                            </div>
                        </a>
                        <a href={"/"} className="fab vk">
                            <Image
                                src={vk}
                                alt="google icon"
                                width={20}
                                height={20}
                            />
                            <div className="vk__title">
                                Vk
                            </div>
                        </a>
                    </div>
                </div>
            </form>
        </div>
    );
}