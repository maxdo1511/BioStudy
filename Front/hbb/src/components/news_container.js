import '../css/components/news_container.css'
import '../css/standart/default.css'
import icon from '../../public/pushkin.jpg'
import Image from "next/image";

export default function NewsContainer() {

    return (
        <div className={"news__container__main shadow-lg rounded-lg"}>
            <div className={"news__icon__container"}>
                <Image
                    sizes={"300px"}
                    width="40%"
                    height={300}
                    className={"news__icon"}
                    src={icon}
                    alt={""}
                />
            </div>
            <div className={"text__container"}>
                <div className={"title__container"}>
                    <a href={"/"} className={"text-4xl text__title"}>
                        Тайтл
                    </a>
                    <p className={"text-1xl text__info"}>
                        Какой-то текст
                    </p>
                    <a href={"/"} className={"button"}>
                        Узнать больше
                    </a>
                </div>
            </div>
        </div>
    )

}