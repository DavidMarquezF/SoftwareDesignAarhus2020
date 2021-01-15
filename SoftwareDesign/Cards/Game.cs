using System;
using System.Collections.Generic;
using System.Linq;

namespace Cards
{
    public class Game
    {
        protected List<Player> _players;
        private Deck _deck;

        public Game(int numberOfCards = 48)
        {
            _deck = new Deck(numberOfCards);
            _players = new List<Player>();
        }

        public void AddPlayer(Player player)
        {
            _players.Add(player);
        }

        public void DealCardsToPlayers(int numOfCards)
        {
            _players.ForEach(player => _deck.DealCardsToPlayer(player, numOfCards));
        }

        public virtual void AnnounceWinner()
        {
            Console.WriteLine("And the winner is... *drum roll*");
            Console.WriteLine(_players.OrderByDescending(p => p.Value).FirstOrDefault().Name + "!!!");
        }
    }
}