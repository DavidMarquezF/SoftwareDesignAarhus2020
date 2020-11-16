using System.Text;
using System;
using System.Collections.Generic;

namespace RomanConverter
{
    public class Arabic2
    {
        private readonly int _num;
        SortedList<int, string> romanDict = new SortedList<int, string>(Comparer<int>.Create((x, y) => y.CompareTo(x)))
        {
            {1000, "M"},
            {900, "CM"},
            {500, "D"},
            {400, "CD"},
            {100, "C"},
            {90, "XC"},
            {50, "L"},
            {40, "XL"},
            {10, "X"},
            {9, "IX"},
            {5, "V"},
            {4, "IV"},
            {1, "I"}
        };
        public Arabic2(int number)
        {
            _num = number;
        }

        public string ToRoman()
        {
            StringBuilder result = new StringBuilder();
            int number = _num;

            Func<KeyValuePair<int, string>, int, StringBuilder, int> calcNum = null;
            calcNum = (KeyValuePair<int, string> it, int num, StringBuilder s) =>
            {
                if (num >= it.Key)
                {
                    result.Append(it.Value);
                    return calcNum(it, num - it.Key, s);
                }
                else
                    return num;
                
            };

            foreach (var item in romanDict)
            {
                number = calcNum(item, number, result); 
                if(number == 0)
                    break;

            }
            return result.ToString();
        }
    }
}