"use client"
import Link from 'next/link'
import { useEffect, useState } from 'react'

type Post = {
  id: number
  title: string
  content: string
  userId?: number
  userName?: string
  createdAt?: string
}

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080'

export default function HomePage() {
  const [posts, setPosts] = useState<Post[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const res = await fetch(`${API_BASE}/api/posts?page=0&size=10&sort=createdAt,DESC`)
        if (!res.ok) throw new Error(`Failed to fetch: ${res.status}`)
        const data = await res.json()
        setPosts(data.content || [])
      } catch (e: any) {
        setError(e.message)
      } finally {
        setLoading(false)
      }
    }
    fetchPosts()
  }, [])

  if (loading) return <div>Loading...</div>
  if (error) return <div className="text-red-600">Error: {error}</div>

  return (
    <div className="space-y-4">
      {posts.map((p) => (
        <Link
          key={p.id}
          href={`/posts/${p.id}`}
          className="block p-4 rounded border border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-800 hover:bg-gray-50 hover:dark:bg-gray-700 transition-colors"
        >
          <h2 className="text-xl font-semibold text-gray-900 dark:text-gray-100">{p.title}</h2>
          <p className="text-sm text-gray-700 dark:text-gray-300">by {p.userName ?? 'Unknown'}</p>
          <p className="mt-2 text-gray-800 dark:text-gray-200 line-clamp-2">{p.content}</p>
        </Link>
      ))}
    </div>
  )
}


