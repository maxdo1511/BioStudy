import '../css/mainpage.css'
import NewsContainer from "@/components/news_container";

export default function Home() {
  return (
    <div className={"main__page__news__container"}>
        <NewsContainer />
        <NewsContainer />
        <NewsContainer />
        <NewsContainer />
        <NewsContainer />
    </div>
  )
}
