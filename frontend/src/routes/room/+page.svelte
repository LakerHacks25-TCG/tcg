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

{:else}
    <div class="container">
        <div class="gamebox">Game Box</div>
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