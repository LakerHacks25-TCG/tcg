import { goto } from '$app/navigation'
import { backendURL } from './config'

export const isLoggedIn = async () => {
    return (await fetch(backendURL + '/me', { credentials: 'include' })).ok
}

export const post = async (endpoint, config) => {
    const headers = config && config.headers ? config.headers : {}
    const body = config && config.body ? JSON.stringify(config.body) : undefined
    headers['Content-Type'] = 'application/json'
    return await fetch(backendURL + endpoint, {
        method: 'POST',
        credentials: 'include',
        headers,
        body,
    })
}

export const postAndGetJson = async (endpoint, config) => {
    const res = await post(endpoint, config)
    if (!res.ok)
        throw new Error(`could not post to ${endpoint}, status: ${res.status}`)
    return await res.json()
}

export const get = async (endpoint, headers) => await fetch(backendURL + endpoint, { headers, credentials: 'include' })

export const getJson = async (endpoint, headers) => {
    const res = await get(endpoint, headers)
    if (!res.ok)
        throw new Error(`could not get ${endpoint}, status: ${res.status}`)
    return await res.json()
}