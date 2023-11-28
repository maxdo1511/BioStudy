import config from "@/app/properties";

export const coursesData = async () => {
    const res = await fetch(config.courses_for_user);
    if (!res.ok) {
        throw new Error('Something went wrong!');
    }
    const data = await res.json();

    return new Promise((resolve) => {
        setTimeout(() => {
            resolve(data);
        }, 500);
    });
}

const Courses = async () => {
    const data = await coursesData();
    return (
        <div>
            {
                data.map((course) => (
                    <div>
                        <h1>
                            {course.name}
                        </h1>
                    </div>
                ))
            }
        </div>
    )
}

export default Courses
