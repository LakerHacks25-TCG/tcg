<script>
    import { goto } from '$app/navigation';
	import { backendURL } from '$lib/config';
    import { isLoggedIn, getJson, postAndGetJson, get, post } from '$lib/netutil';
	import { Client } from '@stomp/stompjs';
	import { json } from '@sveltejs/kit';
    import hpBar from "$lib/assets/hpBarFinal.png";
    import { addition, subtraction, multiplication, division } from "$lib/assets/mathProblems"
    import moveSet from "$lib/assets/moves.json"

    let roomName = $state(''), username = $state(''), otherUsername = $state('')
    let question = $state(null)

    let started = $state(false)
    let me = $state({}), you = $state({})
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
                    console.log(msg.body)
                    const data = JSON.parse(msg.body)
                    console.log('hi')
                    me = data.me
                   me.hp = me.maxHp
                    me.statuses = []
                    you = data.you
                   you.hp = you.maxHp
                    you.statuses = []
                    otherUsername = data.yourName
                    started = true;
                })
                stompClient.subscribe('/user/queue/turn-end', msg => {
                    const data = JSON.parse(msg.body)
                    me.hp = data.me.hp
                    me.defense = data.me.defense
                    me.attack = data.me.attack
                    me.statuses = data.me.statuses

                    you.hp = data.you.hp
                    you.defense = data.you.defense
                    you.attack = data.you.attack
                    you.statuses = data.you.statuses
                    console.log(me)
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

    const doTurn = (multiplier, moveIndex) => {
        if (stompClient && stompClient.connected) {
            console.log("sending turn")
            console.log(me)
            stompClient.publish({
                destination: '/app/turn',
                body: JSON.stringify({
                    moveId: me.moveIds[moveIndex],
                    speed: 3,
                    multiplier
                })
            })
        }
    }

    const chooseQuestion = problemSet => {
        const choice = Math.floor(Math.random() * problemSet.length)
        question = problemSet[choice];
    }
    const chooseAnswer = (choice, moveIndex) => {
        if (question[choice] == question.answer)
            doTurn(2, moveIndex)
        else
            doTurn(.5, moveIndex)
        question = null
    }
</script>

{#if !started}
    <p>{roomName}</p>
    <p>Waiting for another user to join...</p>

{:else}
<div class="container">
    <div class="hpBoxLeft"><img src={hpBar} alt="playerHPBar" style="width 125%; height 125%"/></div>
    <div class="squareBoxRight">Enemy Health {you.hp}/{you.maxHp}</div>
    <div class="squareBoxLeft">Player Health {me.hp}/{me.maxHp}</div>
    <div class="hpBoxRight"></div>
    <div class="twoBoxLeft">{question ? question['equation'] : ''}</div>
    <div class="twoBoxRight">
        <div><button class="mathbutton" onclick={() => chooseAnswer('a', 0)}>{question ? question['a'] : ''}</button></div>
        <div><button class="mathbutton" onclick={() => chooseAnswer('b', 1)}>{question ? question['b'] : ''}</button></div>
        <div><button class="mathbutton" onclick={() => chooseAnswer('c', 2)}>{question ? question['c'] : ''}</button></div>
        <div><button class="mathbutton" onclick={() => chooseAnswer('d', 3)}>{question ? question['d'] : ''}</button></div>
    </div>
        <div class="twoBoxLeft">
        <div><button class="mathbutton" onclick={() => chooseQuestion(addition)}>{me ? moveSet.moves.filter(m => m.id == me.moveIds[0])[0].name : ''}</button></div>
        <div><button class="mathbutton" onclick={() => chooseQuestion(subtraction)}>{me ? moveSet.moves.filter(m => m.id == me.moveIds[1])[0].name : ''}</button></div>
        <div><button class="mathbutton" onclick={() => chooseQuestion(multiplication)}>{me ? moveSet.moves.filter(m => m.id == me.moveIds[2])[0].name : ''}</button></div>
        <div><button class="mathbutton" onclick={() => chooseQuestion(division)}>{me ? moveSet.moves.filter(m => m.id == me.moveIds[3])[0].name : ''}</button></div>
    </div>
    <div>c5</div>
    <div>d5</div>
</div>
{/if}