using System;

namespace Cards
{
    public class WeakPlayer : Player
    {
        public WeakPlayer(string name) : base(name) { }

        public override void DealCard(Card c)
        {
            if (_cards.Count < 3)
                base.DealCard(c);
            else
            {
                var rnd = new Random();
                _cards.RemoveAt(rnd.Next(_cards.Count));
                base.DealCard(c);
            }

        }
    }
}