import '../../css/globals.css'

export default function DashboardLayout({ children }) {
    return (
        <div className={"content__padding"}>
            Dashboard
            {children}
        </div>
    )
}