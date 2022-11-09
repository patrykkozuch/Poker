package pl.pkozuch.poker.actions;

public class CheckActionTests {
//
//    @Test
//    public void testCheckAction() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p = (Player) objects[1];
//
////        gc.addPlayer(p);
//
//        gc.startGame();
//
//        Assertions.assertFalse(p.doesCheck());
//
//        CheckAction checkAction = new CheckAction(gc, p);
//        checkAction.make();
//
//        Assertions.assertTrue(p.doesCheck());
//    }
//
//    @Test
//    public void testIfPlayerIsInactiveAfterCheck() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p = (Player) objects[1];
//
//        ArrayList<Player> activePlayers = gc.getActivePlayers();
//
//        Assertions.assertTrue(activePlayers.contains(p));
//
//        CheckAction checkAction = new CheckAction(gc, p);
//        checkAction.make();
//
//        activePlayers = gc.getActivePlayers();
//
//        Assertions.assertFalse(activePlayers.contains(p));
//    }
//
//    @Test
//    public void testPlayerActiveAfterSbRaise() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p1 = (Player) objects[1];
//        Player p2 = (Player) objects[2];
//
//        ArrayList<Player> activePlayers = gc.getActivePlayers();
//
//        Assertions.assertTrue(activePlayers.contains(p1));
//
//        CheckAction checkAction = new CheckAction(gc, p1);
//        checkAction.make();
//
//        activePlayers = gc.getActivePlayers();
//        Assertions.assertFalse(activePlayers.contains(p1));
//
//        RaiseAction raiseAction = new RaiseAction(gc, p2, new String[]{"10"});
//        raiseAction.make();
//
//        activePlayers = gc.getActivePlayers();
//        Assertions.assertTrue(activePlayers.contains(p1));
//    }
//
//    @Test
//    public void testCheckAfterRaise() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p1 = (Player) objects[1];
//        Player p2 = (Player) objects[2];
//
//        RaiseAction raiseAction = new RaiseAction(gc, p2, new String[]{"10"});
//        raiseAction.make();
//
//        CheckAction checkAction = new CheckAction(gc, p1);
//        Assertions.assertThrows(RuntimeException.class, checkAction::make);
//    }
//
//    @Test
//    public void testCheckDuringChanging() {
//        String actions = "RAISE 10\nCALL\nCALL\n";
//
//        Object[] objects = TestPreparer.createGameControllerAndPlayers(actions);
//
//        GameController gc = (GameController) objects[0];
//        Player p1 = (Player) objects[1];
//        Player p2 = (Player) objects[1];
//
//        ArrayList<Player> activePlayers = gc.getActivePlayers();
//        Assertions.assertTrue(activePlayers.contains(p1));
//
//        /*
//            Start betting round - use actions declared above:
//            1. First player raise bet
//            2. Second player calls
//
//         */
//        gc.startNextRound();
//
//        //Now round state == CHANGING
//
//        CheckAction checkAction = new CheckAction(gc, p1);
//        Assertions.assertThrows(RuntimeException.class, checkAction::make);
//    }
}
