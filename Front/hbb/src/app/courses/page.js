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
