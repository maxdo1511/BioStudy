'use client'
import '../css/components/user_course.css'
import Image from "next/image";
import config from "@/app/config";
import {useState} from "react";
import useWindowSize from "@/hooks/windowSizeHooke";

export default function UserCourse({course}) {
    const [isInfoOpen, setInfoOpen] = useState(false)
    const windowSize = useWindowSize()

    return (
        <div className={"course__container"}>
            <div className={"course__image__container"} onMouseOver={() => setInfoOpen(true)} onMouseOut={() => setInfoOpen(false)}>
                {windowSize.width > config.desktop_nav_max ?
                    <div className={"course__title"}>
                        {course.name}
                    </div>
                    :
                    !isInfoOpen &&
                        <div className={"course__title"}>
                            {course.name}
                        </div>
                }
                <Image
                    className="course__image"
                    src={config.course_icon + course.id}
                    alt="profil icon"
                    sizes="100%"
                    fill
                    style={{
                        objectFit: 'cover',
                    }}
                />
                {isInfoOpen &&
                    <div className={"course__info"}>
                        <div className={"course__info__description"}>
                            {course.description}
                        </div>
                        <br/>
                        <div className={"course__info__external"}>
                            <div className={"course__info__external_title"}>
                                Всего недель:
                                <br/>
                                Дата начала:
                                <br/>
                                Дата конца:
                            </div>
                            <div className={"course__info__external__values"}>
                                {course.duration}
                                <br/>
                                {course.start_date}
                                <br/>
                                {course.end_date}
                            </div>
                        </div>
                    </div>
                }
            </div>
        </div>
    )

}