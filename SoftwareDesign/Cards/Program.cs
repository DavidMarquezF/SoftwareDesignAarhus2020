using System;

namespace Cards
{
    class Program
    {
        static void Main(string[] args)
        {
            var game = new LowestGame();
            var player1 = new Player("David");
            var player2 = new Player("Biel");
            var player3 = new Player("Test");
            var player4 = new WeakPlayer("Eloi");
            game.AddPlayer(player1);
            game.AddPlayer(player2);
            game.AddPlayer(player3);
            game.AddPlayer(player4);
            game.DealCardsToPlayers(5);
            player1.ShowHand();
            player2.ShowHand();
            player3.ShowHand();
            player4.ShowHand();
            game.AnnounceWinner();
        }
    }
}
