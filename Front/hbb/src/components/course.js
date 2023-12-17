"use client"
import Image from "next/image";
import config from "@/app/config";
import {useRouter} from "next/navigation";

export default function Course({course}) {

    const router = useRouter()

    function SignUpToCourse(courseId) {
        fetch(config.course_signup, {
            method: "POST",
            body: JSON.stringify({
                "courseID": courseId
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }).then(response => {
            console.log(response)
            if (response.ok) {
                router.push("/profile")
            }
        })
    }
    return (
        <div>
            <div className="col-span-12 sm:col-span-12 md:col-span-4 lg:col-span-3 xl:col-span-3 2xl:col-span-2 shadow-lg border-2 rounded-lg">
                <div className="px-4 py-2">
                    <h1 className="text-gray-900 font-bold text-2xl uppercase line-clamp-1">{course.name}</h1>
                    <p className="text-gray-600 text-sm mt-1 line-clamp-3">{course.description}</p>
                </div>
                <Image className="w-full h-56 object-contain mix-blend-color-burn p-5" src={config.course_icon + course.id} width={500} height={300} alt="product image" priority />
                <div className="flex items-center justify-between p-4 bg-gray-400 rounded-lg">
                    <div className="text-gray-200 font-bold text-xl flex-row">
                        <h1>₽{course.final_cost}</h1>
                        {course.discount > 0 &&
                            <h2 className={"line-through"}>{Math.round((1 + (course.discount / 100)) * course.final_cost)}</h2>
                        }
                    </div>
                    <button onClick={() => SignUpToCourse(course.id)} className="prime__button text-white button__show__info">Записаться</button>
                </div>
            </div>
            <br/>
        </div>
    )
}