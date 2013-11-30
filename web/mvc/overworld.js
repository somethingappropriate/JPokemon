game.control('overworld', {
  refs: [
  ],
  subcontrols:[
  ],
  api: {
    constructor: function() {
      this.players = {};
      me.audio.init("mp3,ogg");
    },

    login: function(json) {
      me.levelDirector.loadLevel(json.map);
      this.players[json.name] = new me.entityPool.newInstanceOf('player', json);
      this.players[json.name].setName(json.name);
      game.playerName = json.name;
      me.game.add(this.players[json.name]);

      new me.menu.FriendsLauncher().show();
      new me.menu.BattleLobbyLauncher().show();
      new me.menu.PartyLauncher().show();
      new me.menu.MessagesArea().show();
      game.subscribe('battle', new me.menu.BattleWindow());
      game.subscribe('selectmove', new me.menu.SelectMoveWindow());
    },

    onResetEvent: function() {
      $.each(this.players, function(name, player) {
        me.game.add(player);

        if (player.name === game.playerName) {
          me.game.viewport.follow(player, me.game.viewport.AXIS.BOTH);
        }
      });

      me.game.sort();
    },

    join: function(json) {
      this.players[json.name] = me.entityPool.newInstanceOf('trainer', json);
      this.players[json.name].setName(json.name);
      this.onResetEvent();
    },

    leave: function(json) {
      me.game.remove(this.players[json.name]);
      me.entityPool.freeInstance(this.players[json.name]);
    },

    move: function(json) {
      this.players[json.name]['walk' + json.direction](json.x, json.y);
    }
  }
});