using System;

namespace Cards
{

    public enum CardType
    {
        Red = 1,
        Blue = 2,
        Green = 3,
        Yellow = 4,
        Gold = 5
    }
    public class Card
    {
        private readonly CardType _cardType;
        private readonly uint _number;

        public int Value => (int)_cardType * Convert.ToInt32(_number);
        public Card(CardType cardtype, uint number)
        {
            _cardType = cardtype;
            _number = number;
        }


        public override string ToString()
        {
            return _cardType + " " + _number.ToString();
        }
    }
}