'use client'

import './singup.css'
import '../../css/standart/default.css'
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import {signUp} from "./signup_module";

export default function SignUp() {

    const [state, setState] = useState({
        username: "",
        email: "",
        password: ""
    })

    const router = useRouter();

    function handleChange(e) {
        const copy = {...state}
        copy[e.target.name] = e.target.value
        setState(copy)
    }

    function handle() {
        useEffect(() => {
            signUp(state).then(r => {
                router.push("/signup")
            })
        }, []);
    }

    return (
        <div>
            <form onSubmit={handle}>
                <h3>Регистрация</h3>

                <label htmlFor="username">Логин</label>
                <input type="text" placeholder="SoLEk" id="username" onChange={event => handleChange(event)}/>

                <label htmlFor="username">Почта</label>
                <input type="email" placeholder="SoLEk@mail.ru" id="email" onChange={event => handleChange(event)}/>

                <label htmlFor="password">Пароль</label>
                <input type="password" placeholder="123" id="password" onChange={event => handleChange(event)}/>

                <button className="animated__up__button" type="submit">Зарегистрироваться</button>
            </form>
        </div>
    );
}