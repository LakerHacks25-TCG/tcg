<script>
    import { goto } from '$app/navigation';
    import { isLoggedIn, getJson, postAndGetJson } from '$lib/netutil';

    let roomName = $state(''), username = $state('')

    let started = $state(false)
    let stompClient = $state(null)
    $effect(async () => {
        if (!await isLoggedIn()) {
            goto('/login')
            return
        }
        try {
            const data = await getJson('/rooms/current')
            roomName = data.roomName
            username = data.username
        } catch (e) {
            console.error(e)
        }
    })
</script>

{#if !started}
    <p>{roomName}</p>
    <p>Waiting for another user to join...</p>
{/if}