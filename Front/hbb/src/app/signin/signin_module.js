import config from "@/app/properties";

export async function signIn(username, password) {
    const request = {
        username,
        password
    }
    await fetch(config.signin, {
            method: "POST",
            body: JSON.stringify(request),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
    ).then(async (res) => {
        const json = res.text()
        localStorage.setItem("token", await json)
    }).then(() => {
            console.log("SUCCESS")
        }
    )
}