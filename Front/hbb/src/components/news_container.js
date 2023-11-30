import '../css/components/news_container.css'
import icon from '../../public/pushkin.jpg'
import Image from "next/image";

export default function NewsContainer() {

    return (
        <div className={"news__container__main"}>
            <Image className={"news__icon"} src={icon} alt={""} />
        </div>


    )

}