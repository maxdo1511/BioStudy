'use client'

import './singup.css'
import '../../css/standart/default.css'
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import config from "@/app/properties";

export default function SignUp() {

    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [repeat_password, setRepeatPassword] = useState('')

    const router = useRouter();

    useEffect(() => {
        if (localStorage.getItem("register") != null) {
            //if (localStorage.getItem("isRegister") === )
            router.push('/')
            localStorage.removeItem("register")
        }
    })

    async function handle() {
        if (password === repeat_password) {
            const user = {
                "username": name,
                "email": email,
                "password": password
            }
            const res = await fetch(config.signup, {
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
                await localStorage.setItem("register", json)
            }
        }
    }

    return (
        <div>
            <div className="background">
                <div className="shape"></div>
                <div className="shape"></div>
            </div>
            <form onSubmit={handle}>
                <h3>Регистрация</h3>

                <label htmlFor="username">Логин {name} {password}</label>
                <input type="text" placeholder="SoLEk" id="username" onChange={event => setName(event.target.value)}/>

                <label htmlFor="username">Почта {name} {password}</label>
                <input type="email" placeholder="SoLEk@mail.ru" id="email" onChange={event => setEmail(event.target.value)}/>

                <label htmlFor="password">Пароль</label>
                <input type="password" placeholder="123" id="password" onChange={event => setPassword(event.target.value)}/>

                <label htmlFor="password">Повторите пароль</label>
                <input type="password" id="repeat_password" onChange={event => setRepeatPassword(event.target.value)}/>

                <button className="animated__up__button" type="submit">Зарегистрироваться</button>
            </form>
        </div>
    );
}