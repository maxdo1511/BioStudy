import config from "@/app/properties";
import Image from "next/image";
import icon from "../../../public/next.svg";

export const coursesData = async () => {
    const res = await fetch(config.courses_for_user);
    if (!res.ok) {
        throw new Error('Something went wrong!');
    }
    const data = await res.json();

    return new Promise((resolve) => {
        setTimeout(() => {
            resolve(data);
        }, 2000);
    });
}

const Courses = async () => {
    const data = await coursesData();
    return (
        <div>
            {
                data.map((product) => (
                    <div className="col-span-12 sm:col-span-12 md:col-span-4 lg:col-span-3 xl:col-span-3 2xl:col-span-2 shadow-lg border-2 rounded-lg">
                        <div className="px-4 py-2">
                            <h1 className="text-gray-900 font-bold text-2xl uppercase line-clamp-1">{product.name}</h1>
                            <p className="text-gray-600 text-sm mt-1 line-clamp-3">{product.description}</p>
                        </div>
                        <Image className="w-full h-56 object-contain mix-blend-color-burn p-5" src={icon} width={500} height={300} alt="product image" priority />
                        <div className="flex items-center justify-between p-4 bg-gray-900 rounded-lg">
                            <h1 className="text-gray-200 font-bold text-xl">${product.cost}</h1>
                            <a href={"/"} className="px-3 py-1 bg-gray-200 text-sm text-gray-900 font-semibold rounded hover:scale-105 transition-transform">Посмотреть</a>
                        </div>
                    </div>
                ))
            }
        </div>
    )
}

export default Courses
