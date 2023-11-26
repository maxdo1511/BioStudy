import '../../css/globals.css'

export default function DashboardLayout({ children }) {
    return (
        <div>
            <h1>
                Dashboard
                {children}
            </h1>
        </div>
    )
}