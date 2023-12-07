'use client'
import config from "@/app/config";
import UserCourse from "@/components/user_course";
import {useEffect, useState} from "react";
import UserInfo from "@/components/userinfo";
import './profile.css'

export default function Profile() {
    const [courses, setCourses] = useState(null)
    useEffect(() => {
        fetch(config.usercourses_private_data, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            next: {relative: 0},
            cache: 'no-store'
        }).then(async r => {
                const data = await r.json();
                setCourses(data)
            }
        )
    }, [])
    console.log(courses)
    return (
        <div className={"profile__container"}>
            <div className={"profile__courses__container"}>
                {courses != null &&
                    <div className={"profile__course__container"}>
                        {
                            courses.map((course) => (
                                <div key={course.id} className={"profile__course__container"}>
                                    <UserCourse course={course} />
                                    <br/>
                                </div>
                            ))
                        }
                    </div>
                }
            </div>
        </div>
    )
}