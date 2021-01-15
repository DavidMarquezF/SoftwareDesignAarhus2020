using System;
using System.Linq;

namespace Cards
{
    public class LowestGame : Game
    {
        public override void AnnounceWinner()
        {
            Console.WriteLine("And the winner is... *drum roll*");
            Console.WriteLine(_players.OrderByDescending(p => p.Value).LastOrDefault().Name + "!!!");
        }
    }
}