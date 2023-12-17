import "../css/components/pop-up-add-person.css"
import "../css/standart/default.css"

export default function CustomError({error, state}) {
    return (
        <fieldset className={"absolute border-2 border-sky-700 bg-white p-7 aaa"}>
            <h1 className={"text-4xl mb-10"}>Произошла ошибка: </h1>
            <h2>{error}</h2>
            <br/>
            <button onClick={() => state(null)} className={"prime__button submit_button"}>
                Закрыть
            </button>
        </fieldset>
    )
}