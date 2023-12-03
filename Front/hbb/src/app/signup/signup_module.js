import config from "@/app/properties";

export async function signUp(json) {
    const res = await fetch(config.signup, {
            method: "POST",
            body: JSON.stringify(json),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
    )
    if (res.ok) {
        const json = await res.text()
        await localStorage.setItem("register", json)
    }
}