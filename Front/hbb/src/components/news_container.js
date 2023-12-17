import '../css/components/news_container.css'
import '../css/standart/default.css'
import icon from '../../public/pushkin.jpg'
import Image from "next/image";
import config from "@/app/config";

export default function NewsContainer({data}) {
    const date = new Date(data.date);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear().toString();

    const formattedDate = `${day}.${month}.${year}`;
    return (
        <div className={"news__container__main shadow-lg rounded-lg"}>
            <div className={"news__icon__container"}>
                <Image
                    sizes={"300px"}
                    width={300}
                    height={300}
                    className={"news__icon"}
                    src={config.news_icon + data.preview}
                    alt={""}
                />
            </div>
            <div className={"text__container relative"}>
                <div className={"title__container"}>
                    <a href={"/"} className={"text-4xl text__title"}>
                        {data.title}
                    </a>
                    <p className={"text-1xl text__info text-white"}>
                        {data.text}
                    </p>
                    <a href={"/"} className={"text-white button"}>
                        Узнать больше
                    </a>
                    <h1 className={"absolute w-full text-right bottom-0 data__time"}>{formattedDate}</h1>
                </div>
            </div>
        </div>
    )

}