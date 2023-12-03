import config from "@/app/properties";

export async function signIn(state) {
    const res = fetch(config.signin, {
            method: "POST",
            body: JSON.stringify(state),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
    )
    if (res.ok) {
        const json = await res.text()
        localStorage.setItem("token", json)
    }
}