<script>
    import { goto } from '$app/navigation';
	import { backendURL } from '$lib/config';

    let username = $state(''), password = $state('')

    async function handleLogin() {
        const res = await fetch(backendURL + '/login', {
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
    <input class="textbox-input" type="text" bind:value={username}>
    <input class="textbox-input" type="password" bind:value={password}>
    <button type="submit">Login</button>
</form>