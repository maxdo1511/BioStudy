import '../../css/globals.css'
import './dashboard.css'
import UserInfo from "@/components/userinfo";

export default function DashboardLayout({ children }) {
    return (
        <div className={"dashboard__container"}>
            <UserInfo />
            {children}
        </div>
    )
}