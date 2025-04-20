<script>
    import { goto } from '$app/navigation';
	import { backendURL } from '$lib/config';
    import { isLoggedIn, getJson, postAndGetJson, get, post } from '$lib/netutil';
	import { Client } from '@stomp/stompjs';

    let roomName = $state(''), username = $state('')

    let started = $state(false)
    let stompClient = $state(null)
    $effect(async () => {
        if (!await isLoggedIn()) {
            goto('/login')
            return
        }
        stompClient = new Client({
            brokerURL: backendURL + '/ws',
            onConnect: () => {
                console.log('connected')
                stompClient.subscribe('/user/queue/start', msg => {
                    started = true;
                    console.log(msg.body)
                })
                stompClient.subscribe('/user/queue/turn-end', msg => {
                    console.log(msg.body)
                })
                post('/rooms/start')
            },
            onStompError: frame => {
                console.error('broker reported error ' + frame.headers['message'])
                console.error('additional details: ' + frame.body)
            },
            webSocketFactory: () => new WebSocket(backendURL + '/ws')
        })
        stompClient.activate()
        try {
            const data = await getJson('/rooms/current')
            roomName = data.roomName
            username = data.username
        } catch (e) {
            console.error(e)
        }
    })

    const doTurn = () => {
        if (stompClient && stompClient.connected) {
            console.log("sending turn")
            stompClient.publish({
                destination: '/app/turn',
                body: JSON.stringify({
                    moveId: 1,
                    speed: 3,
                    multiplier: 1
                })
            })
        }
    }
</script>

{#if !started}
    <p>{roomName}</p>
    <p>Waiting for another user to join...</p>

{:else}
    <div class="container">
        <div class="gamebox" onclick={doTurn}>Game Box</div>
        <div class="twoBoxLeft">Question box</div>
        <div class="twoBoxRight">Answer Box</div>
        <div class="twoBoxLeft">Tis is where the operator buttons will go and we can totally cheat by putting these in a table</div>
        <div>c5</div>
        <div>d5</div>






    <!-- <div class="bg-[src={bg}]"> -->
    <!-- <div class="content">
    <h1>Welcome to SvelteKit</h1>


    {count}

    <button class="mathbutton" onclick={() => count++}>+</button>
    <button class="mathbutton" onclick={() => count--}>-</button>
    <button class="mathbutton" onclick={() => count++}>&#x2715;</button>
    <button class="mathbutton" onclick={() => count--}>&#247;</button>
    <br>
    <br>
    <label for="answer">?</label>
    <input type="text" id="answer" name="answer">

    <img alt="LogiBeast Logo" src={logo} /> -->
    <!-- </div> --> 
    </div>
{/if}