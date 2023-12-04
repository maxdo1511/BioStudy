'use client'

import './a.css'
import config from "@/app/properties";
import {handleImageUpload} from "@/utils/image_module";

export default function ImageUpload() {

    return (
        <div className={"contents"}>
            <input type="file" accept="image/*" onChange={handleImageUpload} />
        </div>
    );

}