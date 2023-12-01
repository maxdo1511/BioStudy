import config from "@/app/properties";

export async function signIn(name, password) {
    const user = {
        "username": name,
        "password": password
    }
    const res = await fetch(config.signin, {
            method: "POST",
            body: JSON.stringify(user),
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