'use client'

import './singin.css'
import '../../css/standart/default.css'
import google from "public/icons/google.svg";
import vk from "public/icons/vk.svg";
import Image from "next/image";
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import {signIn} from "./signin_module";
import {Checkbox} from "@icon-park/react";

export default function SignIn() {

    const [state, setState] = useState({
        username: "",
        password: ""
    })
    const [save, setSave] = useState(false)

    const router = useRouter();

    function handleChange(e) {
        const copy = {...state}
        copy[e.target.name] = e.target.value
        setState(copy)
    }

    function handle() {
        useEffect(() => {
            signIn(state).then(r => {
                router.push("/dashboard/profile")
            })
        }, []);
    }

    return (
        <div>
            <form onSubmit={handle}>
                <h3>Войдите</h3>

                <label htmlFor="username">Логин</label>
                <input type="text" placeholder="SoLEk" id="username" onChange={event => handleChange(event)}/>

                <label htmlFor="password">Пароль</label>
                <input type="password" placeholder="123" id="password" onChange={event => handleChange(event)}/>

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