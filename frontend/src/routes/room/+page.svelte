<script>
    import { goto } from '$app/navigation';
	import { backendURL } from '$lib/config';
    import { isLoggedIn, getJson, postAndGetJson, get, post } from '$lib/netutil';
	import { Client } from '@stomp/stompjs';
	import { json } from '@sveltejs/kit';

    // let roomName = $state(''), username = $state(''), otherUsername = $state('')

    // let started = $state(false)
    // let me = $state({}), you = $state({})
    // let stompClient = $state(null)
    // $effect(async () => {
    //     if (!await isLoggedIn()) {
    //         goto('/login')
    //         return
    //     }
    //     stompClient = new Client({
    //         brokerURL: backendURL + '/ws',
    //         onConnect: () => {
    //             console.log('connected')
    //             stompClient.subscribe('/user/queue/start', msg => {
    //                 console.log(msg.body)
    //                 const data = JSON.parse(msg.body)
    //                 console.log('hi')
    //                 me = data.me
    //                 me.statuses = []
    //                 you = data.you
    //                 you.statuses = []
    //                 otherUsername = data.yourName
    //                 started = true;
    //             })
    //             stompClient.subscribe('/user/queue/turn-end', msg => {
    //                 const data = JSON.parse(msg.body)
    //                 me.hp = data.me.hp
    //                 me.defense = data.me.defense
    //                 me.attack = data.me.attack
    //                 me.statuses = data.me.statuses

    //                 you.hp = data.you.hp
    //                 you.defense = data.you.defense
    //                 you.attack = data.you.attack
    //                 you.statuses = data.you.statuses
    //                 console.log(me)
    //             })
    //             post('/rooms/start')
    //         },
    //         onStompError: frame => {
    //             console.error('broker reported error ' + frame.headers['message'])
    //             console.error('additional details: ' + frame.body)
    //         },
    //         webSocketFactory: () => new WebSocket(backendURL + '/ws')
    //     })
    //     stompClient.activate()
    //     try {
    //         const data = await getJson('/rooms/current')
    //         roomName = data.roomName
    //         username = data.username
    //     } catch (e) {
    //         console.error(e)
    //     }
    // })

    // const doTurn = () => {
    //     if (stompClient && stompClient.connected) {
    //         console.log("sending turn")
    //         stompClient.publish({
    //             destination: '/app/turn',
    //             body: JSON.stringify({
    //                 moveId: 2,
    //                 speed: 3,
    //                 multiplier: 1
    //             })
    //         })
    //     }
    // }
</script>

<!-- {#if !started}
    <p>{roomName}</p>
    <p>Waiting for another user to join...</p>

{:else} -->
<div class="container">
    <div class="twoBoxLeft">Player HP</div>
    <div class="squareBoxRight">Enemy sprite</div>
    <div class="squareBoxLeft">Player sprite</div>
    <div class="twoBoxRight">Enemy HP</div>
    <div class="twoBoxLeft">Question box</div>
    <div class="twoBoxRight">
        <div><button class="mathbutton" onclick={() => count++}>Answer A</button></div>
        <div><button class="mathbutton" onclick={() => count--}>Answer B</button></div>
        <div><button class="mathbutton" onclick={() => count++}>Answer C</button></div>
        <div><button class="mathbutton" onclick={() => count--}>Answer D</button></div>
    </div>
        <div class="twoBoxLeft">
        <div><button class="mathbutton" onclick={() => count++}>+</button></div>
        <div><button class="mathbutton" onclick={() => count--}>-</button></div>
        <div><button class="mathbutton" onclick={() => count++}>&#x2715;</button></div>
        <div><button class="mathbutton" onclick={() => count--}>&#247;</button></div>
    </div>
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
<!-- {/if} -->