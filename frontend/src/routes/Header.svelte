<script lang="ts">
    
</script>

<main> 

    <p> 
        Step: {formState.step}
    </p>

    {#if formState.step === 0}
        <div>
            <label for="name">Your name</label>

            <input type="text" id="name" bind:value={"bind:value{formState}".name} />
        </div>

        <button 
        onclick={() => }
</main>