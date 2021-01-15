using System;
using System.Collections.Generic;
using System.Linq;

namespace Cards
{
    public class Player
    {
        private string _name;
        protected List<Card> _cards;
        public string Name {
            get => _name;
            private set => _name = value; 
        }

        public int Value => _cards.Select(a => a.Value).Sum();

        public Player(string name){
            Name = name;
            _cards = new List<Card>();
        }

        public void ShowHand(){
            Console.Write(Name + ": ");
            foreach(var card in _cards){
                Console.Write(card.ToString() + ", ");
            }
            Console.WriteLine();
        }

        public virtual void DealCard(Card c){
            this._cards.Add(c);
        }
    }
}