import '../css/globals.css'
import '../css/mainpage.css'
import '../css/standart/default.css'
import '../components/userlogo'
import Navbar from "@/components/navbar";
import { Inter } from 'next/font/google'

export const metadata = {
  title: 'Create Next App',
  description: 'Generated by create next app',
}

const inter = Inter({ subsets: ['latin'] })

export default function RootLayout({ children }) {
  return (
      <html lang="ru">
          <body className="main">
            <header>
              <Navbar />
            </header>

            <main className={inter.className}>
                {children}
            </main>

            <footer>
            </footer>
          </body>
      </html>
  );
};
