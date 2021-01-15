using System;
using System.Collections.Generic;

namespace Cards
{
    public class Deck
    {
        private List<Card> _cards;

        public Deck(int numberOfCards){
            _cards = new List<Card>();
            var rnd = new Random();
            var possibleCards = Enum.GetValues(typeof(CardType));
            for(int i = 0; i < numberOfCards; i++){
                var card = new Card((CardType)possibleCards.GetValue(rnd.Next(possibleCards.Length)), (uint)rnd.Next(1,9));
                _cards.Add(card);
            }
        }

        public void DealCardsToPlayer(Player player, int numberOfCards){
            var rnd = new Random();
            for(int i = 0; i < numberOfCards; i++){
                var index = rnd.Next(_cards.Count);
                var card = _cards[index];
                _cards.RemoveAt(index);
                player.DealCard(card);
            }
        }
    }
}