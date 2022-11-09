package pl.pkozuch.poker.actions;

public class ChangeActionTests {
//    @Test
//    public void testWrongCardNumber() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p = (Player) objects[1];
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            new ChangeAction(gc, p, new String[]{"-1"});
//        });
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            new ChangeAction(gc, p, new String[]{"6"});
//        });
//    }
//
//    @Test
//    public void testChangeCardDuringOtherStateThanChanging() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p = (Player) objects[1];
//
//        ChangeAction changeAction = new ChangeAction(gc, p, new String[]{"1"});
//        Assertions.assertThrows(RuntimeException.class, changeAction::make);
//    }
//
//    @Test
//    public void testChangeSuccessfully() {
//        String actions = "RAISE 10\nCALL\nCALL\n";
//
//        Object[] objects = TestPreparer.createGameControllerAndPlayers(actions);
//
//        GameController gc = (GameController) objects[0];
//        Player p1 = (Player) objects[1];
//        Player p2 = (Player) objects[2];
//
//        gc.startNextRound();
//
//        Card[] p1CardsBeforeChange = p1.getCards();
//        Card[] p2CardsBeforeChange = p2.getCards();
//
//        //Providing 0 should not change any card
//        ChangeAction changeAction1 = new ChangeAction(gc, p1, new String[]{"0"});
//        changeAction1.make();
//
//        ChangeAction changeAction2 = new ChangeAction(gc, p2, new String[]{"1", "2", "3", "4", "5"});
//        changeAction2.make();
//
//        Card[] p1CardsAfterChange = p1.getCards();
//        Card[] p2CardsAfterChange = p2.getCards();
//
//        Assertions.assertTrue(p1.hasChangedCards());
//        Assertions.assertTrue(p2.hasChangedCards());
//
//        for (int i = 0; i < 5; i++) {
//            Assertions.assertEquals(p1CardsBeforeChange[i], p1CardsAfterChange[i]);
//            Assertions.assertNotEquals(p2CardsBeforeChange[i], p2CardsAfterChange[i]);
//        }
//    }
//
//    @Test
//    public void testChangeWithInvalidArgumentType() {
//        Object[] objects = TestPreparer.createGameControllerAndPlayers();
//
//        GameController gc = (GameController) objects[0];
//        Player p = (Player) objects[1];
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            new ChangeAction(gc, p, new String[]{"b"});
//        });
//    }
}
