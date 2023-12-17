import '../css/mainpage.css'
import NewsContainer from "@/components/news_container";
import config from "@/app/config";

export const newsData = async () => {
    const res = await fetch(config.news_all, {
        next: {relative: 0},  cache: 'no-store'
    });
    if (!res.ok) {
        throw new Error('Something went wrong!');
    }
    return  await res.json();
}

export default async function Home() {
    const news = await newsData();
    return (
        <div className={"main__page__news__container"}>
            {
                news.map((data) => (
                    <div key={data.id}>
                        <NewsContainer data={data}/>
                    </div>
                ))
            }
        </div>
    )
}
