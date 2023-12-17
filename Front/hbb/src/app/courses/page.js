import config from "@/app/config";
import '../../css/standart/default.css'
import './courese.css'
import Course from "@/components/course";

export const coursesData = async () => {
    const res = await fetch(config.courses_for_user, {
        next: {relative: 0},  cache: 'no-store'
    });
    if (!res.ok) {
        throw new Error('Something went wrong!');
    }
    return  await res.json();
}

const Courses = async () => {
    const data = await coursesData();
    return (
        <div className={"w-1/2"}>
            {
                data.map((course) => (
                    <div key={course.id}>
                        <Course course={course} />
                    </div>
                ))
            }
        </div>
    )
}

export default Courses
