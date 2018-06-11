<template>
  <ul class="mini-search-result">
    <p>User</p>
    <br />
    <li v-for="user in users" v-bind:key="user.id" class="user">
      <Avatar v-bind:src=user.avatar />
      {{user.name}}
    </li>

  </ul>
</template>

<style>
  .mini-search-result {
    display: block;
    width: 50%;
    margin:auto;
    margin-top: 5px;
    border: 0.1ex solid gray;
  }

  .user {
    margin: 4px;
  }
</style>


<script>
  export default {
    data: function () {
      return {
        users: [

        ],
        imgs: [

        ]
      }
    }
  }
</script>
