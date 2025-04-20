<script>
    import { goto } from '$app/navigation';
	import { backendURL } from '$lib/config';
    import { isLoggedIn, getJson, postAndGetJson, get, post } from '$lib/netutil';
	import { Client } from '@stomp/stompjs';
	import { json } from '@sveltejs/kit';
    import hpBar from "$lib/assets/hpBarFinal.png";
    import answerButton from "$lib/assets/Button_2.png";
    import mathButton from "$lib/assets/Button_4.png";


    let roomName = $state('')
    let rooms = $state([])
    let room = $state(null)

    $effect(async () => {
        if (!await isLoggedIn()) {
            goto('/login')
            return
        }
        try {
            rooms = await getJson('/rooms')
        } catch (e) {
            console.error(e)
        }
    })

    async function create() {
        const res = await post(`/rooms?name=${roomName}&creature-id=1`)
        if (res.ok)
            goto('/room')
        else
            console.error('failed to create room')
    }

    async function join() {
        const res = await post(`/rooms/${roomName}/join?creature-id=1`)
        if (res.ok)
            goto('/room')
        else if (res.status == 404)
            console.error(`no room found with the name ${roomName}`)
        else if (res.status == 409)
            console.error('room full')
        else
            console.error('failed to join room')
    }
</script>

<style>
    .mathbutton{
    background-image: url("$lib/assets/Button_2.png");
    min-width: auto;
    background-repeat: no-repeat;
    min-height: 100%;
}

.mathbutton:active{
    size: 85%;
}
</style>

<input class="textbox-input" type="text" bind:value={roomName}>
<button onclick={create}>Create Room</button>
<button onclick={join}>Join Room</button>
