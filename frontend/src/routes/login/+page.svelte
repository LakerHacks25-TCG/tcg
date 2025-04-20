<script>
    import { goto } from '$app/navigation';

    let username = $state(''), password = $state('')

    async function handleLogin() {
        const res = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({username, password}),
            credentials: 'include'
        })
        if (res.ok) {
            goto('/')
        }
    }
</script>

<form onsubmit={handleLogin}>
    <input type="text" bind:value={username}>
    <input type="password" bind:value={password}>
    <button type="submit">Login</button>
</form>