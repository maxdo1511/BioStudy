import '../css/not-found/not-found.css'
import '../css/standart/default.css'

export default function NotFound() {
    return (
        <div className={"not__found__main"}>
            <div className={"not__found__board"}>
                <p className={"not__found__title"}>
                    Страничка не найдена
                </p>
                <a href={'/'} className={"not__found__button prime__button"}>
                    Вернуться на главную
                </a>
            </div>
        </div>
    )
}