import Image from "next/image";
import icon from "public/next.svg";
import "../css/components/userlogo.css"
import "../css/standart/default.css"

const UserLogo = () => {
    return (
        <div>
            <a className="user_logo" href={"/"}>
                <Image
                    className="user_image"
                    src={icon}
                    alt="profil icon"
                    width={40}
                    height={40}
                />
                Profile
            </a>
        </div>
    )
}

export default UserLogo;