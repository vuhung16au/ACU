import './globals.css'
import type { Metadata } from 'next'

export const metadata: Metadata = {
  title: 'Java Programming Blog',
  description: 'A simple blog built with Spring Boot and Next.js',
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body>
        <header className="border-b border-gray-200 dark:border-gray-700 bg-white/70 dark:bg-gray-900/70 backdrop-blur supports-[backdrop-filter]:bg-white/60 supports-[backdrop-filter]:dark:bg-gray-900/60">
          <div className="max-w-4xl mx-auto p-4 font-semibold text-gray-900 dark:text-gray-100">Java Programming Blog</div>
        </header>
        <main className="max-w-4xl mx-auto p-4 text-gray-900 dark:text-gray-100">{children}</main>
      </body>
    </html>
  )
}


