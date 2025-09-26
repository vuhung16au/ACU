"use client"
import { useEffect, useState } from 'react'
import { useParams } from 'next/navigation'

type Comment = { id: number, content: string, userName?: string, createdAt?: string }
type Post = { id: number, title: string, content: string, userName?: string, comments?: Comment[] }

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080'

export default function PostDetailPage() {
  const params = useParams()
  const id = params?.id as string
  const [post, setPost] = useState<Post | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [comment, setComment] = useState('')
  const [submitting, setSubmitting] = useState(false)

  const load = async () => {
    try {
      const res = await fetch(`${API_BASE}/api/posts/${id}`)
      if (!res.ok) throw new Error(`Failed to fetch: ${res.status}`)
      const data = await res.json()
      setPost(data)
    } catch (e: any) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { if (id) { load() } }, [id])

  const submitComment = async () => {
    if (!comment.trim() || !post) return
    setSubmitting(true)
    try {
      const res = await fetch(`${API_BASE}/api/comments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content: comment, userId: 1, postId: post.id })
      })
      if (!res.ok) throw new Error('Failed to add comment')
      setComment('')
      await load()
    } catch (e: any) {
      alert(e.message)
    } finally {
      setSubmitting(false)
    }
  }

  if (loading) return <div className="text-gray-900 dark:text-gray-100">Loading...</div>
  if (error) return <div className="text-red-700 dark:text-red-400">Error: {error}</div>
  if (!post) return <div>Not found</div>

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">{post.title}</h1>
        <p className="mt-3 whitespace-pre-wrap text-gray-800 dark:text-gray-200">{post.content}</p>
      </div>

      <section>
        <h2 className="text-xl font-semibold mb-2 text-gray-900 dark:text-gray-100">Comments</h2>
        <div className="space-y-3">
          {(post.comments ?? []).map(c => (
            <div key={c.id} className="p-3 rounded border border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-800">
              <div className="text-sm text-gray-700 dark:text-gray-300">{c.userName ?? 'Anonymous'}</div>
              <div className="text-gray-900 dark:text-gray-100">{c.content}</div>
            </div>
          ))}
        </div>

        <div className="mt-4 space-y-2">
          <textarea value={comment} onChange={e => setComment(e.target.value)}
                    className="w-full border rounded p-2 border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 placeholder-gray-500 dark:placeholder-gray-400" rows={3} placeholder="Add a comment" />
          <button onClick={submitComment} disabled={submitting}
                  className="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded disabled:opacity-50">
            {submitting ? 'Submitting...' : 'Submit'}
          </button>
        </div>
      </section>
    </div>
  )
}


