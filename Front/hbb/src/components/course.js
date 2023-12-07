import Image from "next/image";
import icon from "../../public/next.svg";
import config from "@/app/config";

export default function Course({course}) {
    return (
        <div>
            <div className="col-span-12 sm:col-span-12 md:col-span-4 lg:col-span-3 xl:col-span-3 2xl:col-span-2 shadow-lg border-2 rounded-lg">
                <div className="px-4 py-2">
                    <h1 className="text-gray-900 font-bold text-2xl uppercase line-clamp-1">{course.name}</h1>
                    <p className="text-gray-600 text-sm mt-1 line-clamp-3">{course.description}</p>
                </div>
                <Image className="w-full h-56 object-contain mix-blend-color-burn p-5" src={config.course_icon + course.id} width={500} height={300} alt="product image" priority />
                <div className="flex items-center justify-between p-4 bg-gray-900 rounded-lg">
                    <h1 className="text-gray-200 font-bold text-xl">₽{course.final_cost} {course.discount}</h1>
                    <a href={"/"} className="prime__button text-white button__show__info">Посмотреть</a>
                </div>
            </div>
            <br/>
        </div>
    )
}