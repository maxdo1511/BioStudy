'use client'

import './a.css'
import config from "@/app/properties";

function getByteArrayFromImage(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.onload = () => {
            const arrayBuffer = reader.result;
            const uintArray = new Uint8Array(arrayBuffer);
            resolve(uintArray);
        };

        reader.onerror = (error) => {
            reject(error);
        };

        reader.readAsArrayBuffer(file);
    });
}

export default function ImageUpload() {

    function handleImageUpload(event){
        const file = event.target.files[0];

        fetch(config["add-icon"], {
            method: "POST",
            body: getByteArrayFromImage(file).toString(),
        }).then((response) => response)
            .then((data) => {
                console.log(data)
            })
            .catch((error) => {
                console.log("not ok")
            });
    }

    return (
        <div className={"contents"}>
            <input type="file" accept="image/*" onChange={handleImageUpload} />
        </div>
    );

}