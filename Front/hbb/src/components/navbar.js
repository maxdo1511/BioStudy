'use client'

import Userlogo from "@/components/userlogo";
import '../css/globals.css'
import '../css/mainpage.css'
import '../css/standart/default.css'
import '../css/components/navbar.css'
import '../components/userlogo'
import {useEffect, useState} from "react";
import useWindowSize from "@/hooks/windowSizeHooke";
import config from "@/app/config";
import Image from "next/image";
import menu_ico from '../../public/icons/menu.svg'

export default function Navbar() {
    const [item, setItem] = useState('false');
    const [open, setOpen] = useState(true);
    const windowSize = useWindowSize()

    useEffect(() => {
        setItem(localStorage.getItem("token"))
    }, [])
    return (
        <div className={"nav__container"}>
        {
            open ?
                <nav className="navbar">
                    {windowSize.width > config.desktop_nav_max ?
                        <ul className="navbar__menu">
                            <li className="navbar__item">
                                <a href="/" className="navbar__link prime__button animated__up__button"><i
                                    data-feather="home"></i><span>Главная</span></a>
                            </li>
                            <li className="navbar__item">
                                <a href="/about-us" className="navbar__link prime__button animated__up__button"><i
                                    data-feather="users"></i><span>О нас</span></a>
                            </li>
                            <li className="navbar__item">
                                <a href="/courses" className="navbar__link prime__button animated__up__button"><i
                                    data-feather="folder"></i><span>Курсы</span></a>
                            </li>
                            <li className="navbar__item">
                                <a href="/news" className="navbar__link prime__button animated__up__button"><i
                                    data-feather="archive"></i><span>Новости</span></a>
                            </li>
                            <li className="navbar__item">
                                <a href="/teachers" className="navbar__link prime__button animated__up__button"><i
                                    data-feather="help-circle"></i><span>Преподаватели</span></a>
                            </li>
                            <li className="navbar__item">
                                <a href="/contact" className="navbar__link prime__button animated__up__button"><i
                                    data-feather="settings"></i><span>Обратная связь</span></a>
                            </li>
                        </ul>
                        :
                        <div>
                            <Image src={menu_ico} alt={"menu icon"} width={40} height={40} className={"menu__icon"}
                                   onClick={() => setOpen(false)}/>
                        </div>
                    }
                    {item === 'false' &&
                        <div></div>
                    }
                    {item == null &&
                        <div className="auth__buttons">
                            <a href="/signin" className="prime__button auth__button">
                                Вход
                            </a>
                            <a href="/signup" className="prime__button auth__button">
                                Регистрация
                            </a>
                        </div>
                    }
                    {item === '' &&
                        <div className="auth__buttons">
                            <a href="/signin" className="prime__button auth__button">
                                Вход
                            </a>
                            <a href="/signup" className="prime__button auth__button">
                                Регистрация
                            </a>
                        </div>
                    }
                    {item != null && item.length > 5 &&
                        <Userlogo/>
                    }
                </nav>
                :
                <div className={"full__screen__menu__container"}>
                    <div className={"fixed"}>
                        X
                    </div>
                    <a href="/" className="navbar__link prime__button animated__up__button"><i
                        data-feather="home"></i><span>Главная</span></a>
                </div>
        }
        </div>
    )


}