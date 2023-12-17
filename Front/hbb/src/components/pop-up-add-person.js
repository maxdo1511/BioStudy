"use client"
import "../css/components/pop-up-add-person.css"
import "../css/standart/default.css"
import {useEffect, useState} from "react";
import config from "@/app/config";

export default function PopUpAddPerson({field, localized_field, fieldVal, state, obj, error}) {
    const [value, SetValue] = useState(null)

    async function send() {
        for (const f of field.split(" ")) {
            await fetch(config.user_data_change_field, {
                method: "POST",
                body: JSON.stringify({
                    "field": f,
                    "value": value[f]
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }).then(async r => {
                const response = await r.json();
                state(false)
                if (response.new_value === value[f]) {
                    obj[f] = response.new_value
                }else {
                    error(response)
                }
            })
        }
    }

    function renderPhone() {
        const pl = value == null ? "" : value["phone"]
        return (
            <input type="tel" value={"+7" + pl} maxLength={12} placeholder="(XXX)-XXX-XXXX" id="phone" name="phone" className={"border-2 border-slate-200 min-w-min email-pop-up rounded p-0.5 mb-6"} onChange={event => SetValue({"phone": event.target.value.replace("+7", "")})}/>
        )
    }

    function renderNames(fieldVal) {
        return (
            <div className={"w-full"}>
                <input type="text" maxLength={20} placeholder={fieldVal[1]} className={"border-2 border-slate-200 min-w-min email-pop-up rounded p-0.5 mb-6"} onChange={event => {
                    const copy = {...value}
                    copy[field.split(" ")[0]] = event.target.value
                    console.log(copy)
                    SetValue(copy)
                }
                }/>
                <input type="text" maxLength={20} placeholder={fieldVal[2]} className={"border-2 border-slate-200 min-w-min email-pop-up rounded p-0.5 mb-6"} onChange={event => {
                    const copy = {...value}
                    copy[field.split(" ")[1]] = event.target.value
                    console.log(copy)
                    SetValue(copy)
                }
                }/>
            </div>
        )
    }

    function renderDescription(fieldVal) {
        return (
            <textarea maxLength={400} placeholder={fieldVal[1]} className={"border-2 description border-slate-200 email-pop-up rounded p-0.5 mb-6"} onChange={event => SetValue({"description": event.target.value})}/>
        )
    }

    function renderInput(field, value) {
        if (field === "phone") {
            return renderPhone();
        }
        if (field === "firstname secondname") {
            return renderNames(value);
        }
        if (field === "description") {
            return renderDescription(value);
        }
    }


    return (
        <fieldset className={"absolute border-2 border-sky-700 bg-white p-7 aaa"}>
            <h1 className={"text-4xl mb-10"}>Изменить {localized_field}</h1>
            {
                renderInput(field, fieldVal)
            }
            <button onClick={send} className={"prime__button submit_button"}>
                Подтвердить
            </button>
        </fieldset>
    )
}